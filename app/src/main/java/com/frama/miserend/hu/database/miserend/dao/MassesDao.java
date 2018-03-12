package com.frama.miserend.hu.database.miserend.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface MassesDao {

    @Query("SELECT church.*, mass.*,((lng-(:longitude))*(lng-(:longitude)) + (lat-(:latitude))*(lat-(:latitude))) AS len " +
            "FROM templomok AS church JOIN misek AS mass ON mass.tid = church.tid WHERE mass.nap = :dayOfWeek AND lng != 0 AND lat != 0 ORDER BY len")
    DataSource.Factory<Integer, MassWithChuch> getRecommendedMasses(double latitude, double longitude, int dayOfWeek);
}
