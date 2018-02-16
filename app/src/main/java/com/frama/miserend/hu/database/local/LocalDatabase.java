package com.frama.miserend.hu.database.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.frama.miserend.hu.database.local.dao.FavoritesDao;
import com.frama.miserend.hu.database.local.entities.Favorite;
import com.frama.miserend.hu.database.miserend.dao.ChurchDao;
import com.frama.miserend.hu.database.miserend.dao.ChurchWithMassesDao;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.entities.Mass;

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Database(entities = {Favorite.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract FavoritesDao favoritesDao();

}
