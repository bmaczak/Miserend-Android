package com.frama.miserend.hu.database.miserend.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.miserend.entities.Church;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface ChurchDao {

    @Query("SELECT * FROM templomok WHERE lat != 0 AND lng != 0")
    LiveData<List<Church>> getAll();

    @Query("SELECT * FROM templomok WHERE nev LIKE '%' || :searchTerm || '%' OR ismertnev LIKE '%' || :searchTerm || '%'")
    LiveData<List<Church>> getByName(String searchTerm);

    @Query("SELECT DISTINCT varos FROM templomok WHERE varos LIKE '%' || :searchTerm || '%'")
    LiveData<List<String>> getCities(String searchTerm);

    @Query("SELECT DISTINCT varos FROM templomok")
    LiveData<List<String>> getAllCities();

    @Query("SELECT COUNT(*) FROM templomok")
    Single<Integer> getChurchesCount();
}
