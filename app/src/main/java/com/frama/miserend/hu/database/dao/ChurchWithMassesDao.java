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

    @Query("SELECT *,((lng-(:longitude))*(lng-(:longitude)) + (lat-(:latitude))*(lat-(:latitude))) AS len FROM templomok WHERE lng != 0 AND lat != 0 ORDER BY len ASC")
    DataSource.Factory<Integer, ChurchWithMasses> getNearChurches(double latitude, double longitude);

}
