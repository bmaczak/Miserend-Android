package com.frama.miserend.hu.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.frama.miserend.hu.database.MiserendDatabase;
import com.frama.miserend.hu.database.manager.DatabaseManager;
import com.frama.miserend.hu.di.qualifiers.ApplicationContext;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
public class DatabaseModule {


    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(@ApplicationContext Context context) {
        return new DatabaseManager(context);
    }

    @Provides
    @Singleton
    public MiserendDatabase provideMiserendDatabase(@ApplicationContext Context context, @Named("database name") String databaseName) {
        return Room.databaseBuilder(context, MiserendDatabase.class, databaseName).build();
    }

    @Provides
    @Singleton
    @Named("database name")
    String provideDatabaseName() {
        return "miserend.sqlite3";
    }
}
