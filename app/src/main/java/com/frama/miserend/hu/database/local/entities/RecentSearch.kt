package com.frama.miserend.hu.database.local.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.Calendar

/**
 * Created by maczak on 2018. 02. 16..
 */

@Entity(tableName = "recent_searches")
data class RecentSearch(
        @ColumnInfo(name = "searchterm") var searchTerm: String?,
        @PrimaryKey @ColumnInfo(name = "timestamp") var timestamp: Long = Calendar.getInstance().timeInMillis
)
