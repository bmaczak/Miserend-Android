package com.frama.miserend.hu.database.miserend.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction

import com.frama.miserend.hu.database.miserend.relations.ChurchWithMasses

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
interface ChurchWithMassesDao {

    @Transaction
    @Query("SELECT *,((lng-(:longitude))*(lng-(:longitude)) + (lat-(:latitude))*(lat-(:latitude))) AS len FROM templomok WHERE lng != 0 AND lat != 0 ORDER BY len ASC")
    fun getNearChurches(latitude: Double, longitude: Double): DataSource.Factory<Int, ChurchWithMasses>

    @Transaction
    @Query("SELECT * FROM templomok WHERE tid IN (:churchIds)")
    fun getChurchesById(churchIds: List<Int>): LiveData<List<ChurchWithMasses>>

    @Transaction
    @Query("SELECT * FROM templomok WHERE tid = :churchId LIMIT 1")
    fun getChurchById(churchId: Int): LiveData<ChurchWithMasses>

    @Transaction
    @Query("SELECT * FROM templomok WHERE (nev LIKE '%' || :churchName || '%' OR ismertnev LIKE '%' || :churchName || '%') LIMIT 999")
    fun getByName(churchName: String): LiveData<List<ChurchWithMasses>>

    @Transaction
    @Query("SELECT * FROM templomok WHERE (nev LIKE '%' || :churchName || '%' OR ismertnev LIKE '%' || :churchName || '%') " + "AND varos LIKE '%' || :city || '%' LIMIT 999")
    fun getBySearch(churchName: String, city: String): LiveData<List<ChurchWithMasses>>

}
