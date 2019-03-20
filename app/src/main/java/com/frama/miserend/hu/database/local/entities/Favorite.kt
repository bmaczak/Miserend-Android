package com.frama.miserend.hu.database.local.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by maczak on 2018. 02. 16..
 */

@Entity(tableName = "favorites")
data class Favorite(
        @PrimaryKey @ColumnInfo(name = "tid") var churchId: Int
) {
    override fun equals(other: Any?): Boolean = if (other is Favorite) other.churchId == churchId else super.equals(other)
}
