package com.frama.miserend.hu.database.miserend.relations

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

import com.frama.miserend.hu.database.miserend.entities.Church
import com.frama.miserend.hu.database.miserend.entities.Image
import com.frama.miserend.hu.database.miserend.entities.Mass

/**
 * Created by Balazs on 2018. 02. 11..
 */

class ChurchWithMasses {
    @Embedded
    var church: Church? = null
    @Relation(parentColumn = "tid", entityColumn = "tid")
    var masses: List<Mass> = emptyList()
    @Relation(parentColumn = "tid", entityColumn = "tid")
    var images: List<Image> = emptyList()
}
