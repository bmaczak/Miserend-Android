package com.frama.miserend.hu.database.miserend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface ChurchWithMassesDao {

    @Transaction
    @Query("SELECT *,((lng-(:longitude))*(lng-(:longitude)) + (lat-(:latitude))*(lat-(:latitude))) AS len FROM templomok WHERE lng != 0 AND lat != 0 ORDER BY len ASC")
    DataSource.Factory<Integer, ChurchWithMasses> getNearChurches(double latitude, double longitude);

    @Transaction
    @Query("SELECT * FROM templomok WHERE tid IN (:churchIds)")
    LiveData<List<ChurchWithMasses>> getChurchesById(List<Integer> churchIds);

    @Transaction
    @Query("SELECT * FROM templomok WHERE tid = :churchId LIMIT 1")
    LiveData<ChurchWithMasses> getChurchById(int churchId);

    @Transaction
    @Query("SELECT * FROM templomok WHERE (nev LIKE '%' || :churchName || '%' OR ismertnev LIKE '%' || :churchName || '%') LIMIT 999")
    LiveData<List<ChurchWithMasses>> getByName(String churchName);

    @Transaction
    @Query("SELECT * FROM templomok WHERE (nev LIKE '%' || :churchName || '%' OR ismertnev LIKE '%' || :churchName || '%') " +
            "AND varos LIKE '%' || :city || '%' LIMIT 999")
    LiveData<List<ChurchWithMasses>> getBySearch(String churchName, String city);

}
