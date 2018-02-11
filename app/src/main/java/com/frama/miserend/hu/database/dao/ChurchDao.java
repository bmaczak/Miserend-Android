package com.frama.miserend.hu.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.entities.Church;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface ChurchDao {

    @Query("SELECT * FROM templomok")
    Flowable<List<Church>> getAll();

}
