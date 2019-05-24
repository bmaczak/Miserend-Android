package com.frama.miserend.hu.utils

import android.location.Location

import com.frama.miserend.hu.database.miserend.entities.Church

/**
 * Created by maczak on 2018. 03. 13..
 */

object ChurchUtils {

    fun distanceTo(location: Location, church: Church): Float {
        val locationA = Location("point A")
        locationA.latitude = church.lat
        locationA.longitude = church.lon
        return location.distanceTo(locationA)
    }
}
