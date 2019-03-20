package com.frama.miserend.hu.database.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import com.frama.miserend.hu.database.local.entities.Favorite

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
interface FavoritesDao {

    @Query("SELECT tid FROM favorites")
    fun getAll(): LiveData<List<Int>>

    @Query("SELECT COUNT(*) FROM favorites WHERE tid = :churchId")
    fun getCountById(churchId: Int): Int

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

}
