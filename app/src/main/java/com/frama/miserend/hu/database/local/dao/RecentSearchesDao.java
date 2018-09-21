package com.frama.miserend.hu.database.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.local.entities.RecentSearch;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface RecentSearchesDao {

    @Query("SELECT searchterm FROM recent_searches")
    LiveData<List<String>> getAll();

    @Query("SELECT searchterm FROM recent_searches WHERE searchterm LIKE '%' || :searchTerm || '%'  ORDER BY timestamp DESC LIMIT 5")
    LiveData<List<String>> getBySearchTerm(String searchTerm);

    @Insert
    void insert(RecentSearch recentSearch);

    @Delete
    void delete(RecentSearch recentSearch);

}
