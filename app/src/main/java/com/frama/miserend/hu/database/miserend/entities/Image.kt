package com.frama.miserend.hu.database.miserend.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Balazs on 2018. 02. 27..
 */
@Entity(tableName = "kepek", foreignKeys = [(ForeignKey(entity = Church::class, parentColumns = arrayOf("tid"), childColumns = arrayOf("tid")))])
data class Image(
        @PrimaryKey @ColumnInfo(name = "kid") var id: Int,
        @ColumnInfo(name = "tid") var churchId: Int,
        @ColumnInfo(name = "kep") var imageUrl: String?
)