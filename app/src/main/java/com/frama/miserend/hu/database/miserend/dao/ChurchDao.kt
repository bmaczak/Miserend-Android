package com.frama.miserend.hu.database.miserend.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

import com.frama.miserend.hu.database.miserend.entities.Church

import io.reactivex.Single

/**
 * Created by Balazs on 2018. 02. 10..
 */

@Dao
interface ChurchDao {

    @get:Query("SELECT * FROM templomok WHERE lat != 0 AND lng != 0")
    val all: LiveData<List<Church>>

    @get:Query("SELECT DISTINCT varos FROM templomok")
    val allCities: LiveData<List<String>>

    @get:Query("SELECT COUNT(*) FROM templomok")
    val churchesCount: Single<Int>

    @Query("SELECT * FROM templomok WHERE nev LIKE '%' || :searchTerm || '%' OR ismertnev LIKE '%' || :searchTerm || '%'")
    fun getByName(searchTerm: String): LiveData<List<Church>>

    @Query("SELECT DISTINCT varos FROM templomok WHERE varos LIKE '%' || :searchTerm || '%'")
    fun getCities(searchTerm: String): LiveData<List<String>>
}
