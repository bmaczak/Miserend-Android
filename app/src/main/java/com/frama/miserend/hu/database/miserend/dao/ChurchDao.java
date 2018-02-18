package com.frama.miserend.hu.database.miserend.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.miserend.entities.Church;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface ChurchDao {

    @Query("SELECT * FROM templomok")
    Flowable<List<Church>> getAll();

    @Query("SELECT * FROM templomok WHERE nev LIKE '%' || :searchTerm || '%' OR ismertnev LIKE '%' || :searchTerm || '%'")
    Flowable<List<Church>> getByName(String searchTerm);

    @Query("SELECT DISTINCT varos FROM templomok WHERE varos LIKE '%' || :searchTerm || '%'")
    Flowable<List<String>> getCities(String searchTerm);
}
