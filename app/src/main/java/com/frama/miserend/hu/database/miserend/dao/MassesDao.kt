package com.frama.miserend.hu.database.miserend.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

import com.frama.miserend.hu.database.miserend.relations.MassWithChurch

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
interface MassesDao {

    @Query("SELECT church.*, mass.*,((church.lng-(:longitude))*(church.lng-(:longitude)) + (church.lat-(:latitude))*(church.lat-(:latitude))) AS len " + "FROM templomok AS church JOIN misek AS mass ON mass.tid = church.tid WHERE lng != 0 AND lat != 0 AND mass.nap = :dayOfWeek ORDER BY len ASC LIMIT 500")
    fun getMassesInRadius(latitude: Double, longitude: Double, dayOfWeek: Int): LiveData<List<MassWithChurch>>

    @Query("SELECT church.*, mass.* FROM templomok AS church JOIN misek AS mass ON mass.tid = church.tid " +
            "WHERE (church.nev LIKE '%' || :churchName || '%' OR church.ismertnev LIKE '%' || :churchName || '%') " +
            "AND church.varos LIKE '%' || :city || '%' " +
            "AND mass.nap = :dayOfWeek LIMIT 999")
    fun getMassesBySearch(churchName: String, city: String, dayOfWeek: Int): LiveData<List<MassWithChurch>>
}
