package com.frama.miserend.hu.database.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.frama.miserend.hu.database.local.dao.FavoritesDao
import com.frama.miserend.hu.database.local.dao.RecentSearchesDao
import com.frama.miserend.hu.database.local.entities.Favorite
import com.frama.miserend.hu.database.local.entities.RecentSearch

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Database(entities = [(Favorite::class), (RecentSearch::class)], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun recentSearchesDao(): RecentSearchesDao
}
