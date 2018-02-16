package com.frama.miserend.hu.database.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.local.entities.Favorite;
import com.frama.miserend.hu.database.miserend.entities.Church;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<Favorite>> getAll();

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

}
