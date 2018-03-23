package com.frama.miserend.hu.home.pages.map.view;

import com.frama.miserend.hu.database.miserend.entities.Church;

import net.sharewire.googlemapsclustering.ClusterItem;

/**
 * Created by Balazs on 2018. 03. 02..
 */

public class ChurchClusterItem implements ClusterItem {

    private Church church;

    public ChurchClusterItem(Church church) {
        this.church = church;
    }

    @Override
    public double getLatitude() {
        return church.getLat();
    }

    @Override
    public double getLongitude() {
        return church.getLon();
    }

    @Override
    public String getTitle() {
        return church.getName();
    }

    @Override
    public String getSnippet() {
        return church.getAddress();
    }

    public Church getChurch() {
        return church;
    }
}
