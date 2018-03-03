package com.frama.miserend.hu.home.pages.map;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Balazs on 2018. 03. 02..
 */

public class ChurchClusterItem implements ClusterItem {

    private Church church;

    public ChurchClusterItem(Church church) {
        this.church = church;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(church.getLat(), church.getLon());
    }

    @Override
    public String getTitle() {
        return church.getName();
    }

    @Override
    public String getSnippet() {
        return church.getAddress();
    }
}
