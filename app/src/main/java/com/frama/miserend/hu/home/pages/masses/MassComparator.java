package com.frama.miserend.hu.home.pages.masses;

import android.location.Location;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;

import java.util.Comparator;

/**
 * Created by maczak on 2018. 03. 13..
 */

public class MassComparator implements Comparator<MassWithChuch> {

    private final Location currentLocation;

    public MassComparator(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public int compare(MassWithChuch massWithChuch, MassWithChuch t1) {
        return (int) (getDistance(massWithChuch.getChurch()) - getDistance(t1.getChurch()));
    }

    private float getDistance(Church church) {
        Location locationA = new Location("point A");
        locationA.setLatitude(church.getLat());
        locationA.setLongitude(church.getLon());
        return currentLocation.distanceTo(locationA);
    }
}
