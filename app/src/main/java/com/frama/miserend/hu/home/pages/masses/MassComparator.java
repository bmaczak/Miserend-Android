package com.frama.miserend.hu.home.pages.masses;

import android.location.Location;

import com.frama.miserend.hu.database.miserend.relations.MassWithChurch;
import com.frama.miserend.hu.utils.ChurchUtils;

import java.util.Comparator;

/**
 * Created by maczak on 2018. 03. 13..
 */

public class MassComparator implements Comparator<MassWithChurch> {

    private final Location currentLocation;

    public MassComparator(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public int compare(MassWithChurch massWithChurch, MassWithChurch t1) {
        return (int) (ChurchUtils.distanceTo(currentLocation, massWithChurch.getChurch())
                - ChurchUtils.distanceTo(currentLocation, t1.getChurch()));
    }
}
