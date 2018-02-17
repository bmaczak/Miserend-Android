package com.frama.miserend.hu.database.miserend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;

import java.util.List;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface ChurchWithMassesDao {

    @Query("SELECT *,((lng-(:longitude))*(lng-(:longitude)) + (lat-(:latitude))*(lat-(:latitude))) AS len FROM templomok WHERE lng != 0 AND lat != 0 ORDER BY len ASC")
    DataSource.Factory<Integer, ChurchWithMasses> getNearChurches(double latitude, double longitude);

    @Query("SELECT * FROM templomok WHERE tid IN (:churchIds)")
    LiveData<List<ChurchWithMasses>> getChurchesById(List<Integer> churchIds);

}
