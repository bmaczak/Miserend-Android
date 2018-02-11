package com.frama.miserend.hu.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.frama.miserend.hu.database.dao.ChurchDao;
import com.frama.miserend.hu.database.dao.ChurchWithMassesDao;
import com.frama.miserend.hu.database.entities.Church;
import com.frama.miserend.hu.database.entities.Mass;

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Database(entities = {Church.class, Mass.class}, version = 4)
public abstract class MiserendDatabase extends RoomDatabase {
    public abstract ChurchDao churchDao();

    public abstract ChurchWithMassesDao churchWithMassesDao();
}
