package com.frama.miserend.hu.utils;

import android.location.Location;

import com.frama.miserend.hu.database.miserend.entities.Church;

/**
 * Created by maczak on 2018. 03. 13..
 */

public class ChurchUtils {

    public static float distanceTo(Location location, Church church) {
        Location locationA = new Location("point A");
        locationA.setLatitude(church.getLat());
        locationA.setLongitude(church.getLon());
        return location.distanceTo(locationA);
    }
}
