package com.frama.miserend.hu.database.miserend.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.io.Serializable

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Entity(tableName = "templomok")
data class Church(
        @PrimaryKey @ColumnInfo(name = "tid") var id: Int,
        @ColumnInfo(name = "nev") var name: String?,
        @ColumnInfo(name = "ismertnev") var commonName: String?,
        @ColumnInfo(name = "gorog") var isGreek: Boolean,
        @ColumnInfo(name = "lat") var lat: Double,
        @ColumnInfo(name = "lng") var lon: Double,
        @ColumnInfo(name = "geocim") var address: String?,
        @ColumnInfo(name = "varos") var city: String?,
        @ColumnInfo(name = "orszag") var country: String?,
        @ColumnInfo(name = "megye") var county: String?,
        @ColumnInfo(name = "cim") var street: String?,
        @ColumnInfo(name = "megkozelites") var gettingThere: String?,
        @ColumnInfo(name = "kep") var imageUrl: String?
) : Serializable
