package com.frama.miserend.hu.database.miserend

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.frama.miserend.hu.database.miserend.dao.ChurchDao
import com.frama.miserend.hu.database.miserend.dao.ChurchWithMassesDao
import com.frama.miserend.hu.database.miserend.dao.MassesDao
import com.frama.miserend.hu.database.miserend.entities.Church
import com.frama.miserend.hu.database.miserend.entities.Image
import com.frama.miserend.hu.database.miserend.entities.Mass
import com.frama.miserend.hu.database.miserend.manager.DatabaseManager

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Database(entities = arrayOf(Church::class, Mass::class, Image::class), version = DatabaseManager.DATABASE_VERSION)
abstract class MiserendDatabase : RoomDatabase() {
    abstract fun churchDao(): ChurchDao

    abstract fun churchWithMassesDao(): ChurchWithMassesDao

    abstract fun massesDao(): MassesDao
}
