package com.frama.miserend.hu.database.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.local.entities.Favorite;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface FavoritesDao {

    @Query("SELECT tid FROM favorites")
    LiveData<List<Integer>> getAll();

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

}
