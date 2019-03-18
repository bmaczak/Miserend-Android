package com.frama.miserend.hu.database.miserend.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

import com.frama.miserend.hu.utils.Validation

import java.io.Serializable

/**
 * Created by Balazs on 2018. 02. 10..
 */
@Entity(tableName = "misek", foreignKeys = [ForeignKey(entity = Church::class, parentColumns = arrayOf("tid"), childColumns = arrayOf("tid"))])
data class Mass(
        @PrimaryKey @ColumnInfo(name = "mid") var id: Int,
        @ColumnInfo(name = "tid") var churchId: Int,
        @ColumnInfo(name = "nap") var day: Int,
        @ColumnInfo(name = "idoszak") var season: String?,
        @ColumnInfo(name = "nyelv") var language: String?,
        @ColumnInfo(name = "milyen") var tags: String?,
        @ColumnInfo(name = "periodus") var period: String?,
        @ColumnInfo(name = "suly") var weight: Int,
        @ColumnInfo(name = "datumtol") var fromDate: Int,
        @ColumnInfo(name = "datumig") var toDate: Int,
        @ColumnInfo(name = "megjegyzes") var comment: String?
) : Serializable {
    @ColumnInfo(name = "ido")
    var time: String? = null
        get() = if ("24:00:00".equals(field!!, ignoreCase = true)) {
            "00:00:00"
        } else {
            field
        }

    fun hasInfo(): Boolean = Validation.notEmpty(comment) || Validation.notEmpty(tags) || period != null && period != "0"
}
