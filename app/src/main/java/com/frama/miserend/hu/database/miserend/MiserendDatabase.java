package com.frama.miserend.hu.database.miserend;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.frama.miserend.hu.database.miserend.dao.ChurchDao;
import com.frama.miserend.hu.database.miserend.dao.ChurchWithMassesDao;
import com.frama.miserend.hu.database.miserend.dao.MassesDao;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Image;
import com.frama.miserend.hu.database.miserend.entities.Mass;

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Database(entities = {Church.class, Mass.class, Image.class}, version = 4)
public abstract class MiserendDatabase extends RoomDatabase {
    public abstract ChurchDao churchDao();

    public abstract ChurchWithMassesDao churchWithMassesDao();

    public abstract MassesDao massesDao();

}
