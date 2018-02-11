package com.frama.miserend.hu.database.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.relations.ChurchWithMasses;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface ChurchWithMassesDao {

    @Query("SELECT * FROM templomok")
    DataSource.Factory<Integer, ChurchWithMasses> getNearChurches();

}
