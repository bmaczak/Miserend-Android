package com.frama.miserend.hu.di.modules

import android.arch.persistence.room.Room
import android.content.Context

import com.frama.miserend.hu.api.MiserendApi
import com.frama.miserend.hu.database.local.LocalDatabase
import com.frama.miserend.hu.database.miserend.MiserendDatabase
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager
import com.frama.miserend.hu.di.qualifiers.ApplicationContext
import com.frama.miserend.hu.preferences.Preferences

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by Balazs on 2018. 02. 11..
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabaseManager(@ApplicationContext context: Context, api: MiserendApi, preferences: Preferences, miserendDatabase: MiserendDatabase): DatabaseManager {
        return DatabaseManager(context, api, preferences, miserendDatabase.churchDao())
    }

    @Provides
    @Singleton
    fun provideMiserendDatabase(@ApplicationContext context: Context, @Named("database name") databaseName: String): MiserendDatabase {
        return Room.databaseBuilder(context, MiserendDatabase::class.java, databaseName).build()
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context, @Named("local database name") databaseName: String): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, databaseName).build()
    }

    @Provides
    @Singleton
    @Named("database name")
    internal fun provideDatabaseName(): String {
        return DatabaseManager.DATABASE_FILE_NAME
    }

    @Provides
    @Singleton
    @Named("local database name")
    internal fun provideLocalDatabaseName(): String {
        return "localdatabase.sqlite3"
    }
}
