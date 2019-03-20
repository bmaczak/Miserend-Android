package com.frama.miserend.hu.database.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import com.frama.miserend.hu.database.local.entities.RecentSearch

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
interface RecentSearchesDao {

    @Query("SELECT searchterm FROM recent_searches")
    fun getAll(): LiveData<List<String>>

    @Query("SELECT searchterm FROM recent_searches WHERE searchterm LIKE '%' || :searchTerm || '%'  ORDER BY timestamp DESC LIMIT 5")
    fun getBySearchTerm(searchTerm: String): LiveData<List<String>>

    @Insert
    fun insert(recentSearch: RecentSearch)

    @Delete
    fun delete(recentSearch: RecentSearch)

}
