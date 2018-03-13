package com.frama.miserend.hu.home.pages.masses;

import android.location.Location;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.database.miserend.relations.MassWithChuch;
import com.frama.miserend.hu.utils.ChurchUtils;

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
        return (int) (ChurchUtils.distanceTo(currentLocation, massWithChuch.getChurch())
                - ChurchUtils.distanceTo(currentLocation, t1.getChurch()));
    }
}
