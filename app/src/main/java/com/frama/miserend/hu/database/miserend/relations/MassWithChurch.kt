package com.frama.miserend.hu.database.miserend.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

import com.frama.miserend.hu.database.miserend.entities.Church
import com.frama.miserend.hu.database.miserend.entities.Mass

/**
 * Created by maczak on 2018. 03. 12..
 */

class MassWithChurch {
    @Embedded
    var mass: Mass? = null

    @Relation(parentColumn = "tid", entityColumn = "tid")
    var churches: List<Church> = emptyList()

    val church: Church?
        get() = if (!churches.isEmpty()) churches[0] else null
}
