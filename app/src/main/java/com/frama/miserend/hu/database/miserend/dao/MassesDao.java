package com.frama.miserend.hu.database.miserend.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
public interface MassesDao {

    @Query("SELECT church.*, mass.*,((church.lng-(:longitude))*(church.lng-(:longitude)) + (church.lat-(:latitude))*(church.lat-(:latitude))) AS len " +
            "FROM templomok AS church JOIN misek AS mass ON mass.tid = church.tid WHERE lng != 0 AND lat != 0 AND mass.nap = :dayOfWeek ORDER BY len ASC LIMIT 100")
    Flowable<List<MassWithChurch>> getMassesInRadius(double latitude, double longitude, int dayOfWeek);
}
