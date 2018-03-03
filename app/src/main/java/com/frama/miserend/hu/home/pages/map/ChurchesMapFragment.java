package com.frama.miserend.hu.home.pages.map;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class ChurchesMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Inject
    ChurchesMapViewModel churchesMapViewModel;

    private GoogleMap map;
    private ClusterManager<ChurchClusterItem> clusterManager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        churchesMapViewModel.getChurcesLiveData().observe(this, this::onChurchesLoaded);
    }

    private void onChurchesLoaded(List<Church> churches) {
        if (map != null) {
            addPins(churches);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (churchesMapViewModel.getChurcesLiveData().getValue() != null) {
            addPins(churchesMapViewModel.getChurcesLiveData().getValue());
        }
    }

    private void addPins(List<Church> churches) {
        clusterManager = new ClusterManager<>(getActivity(), map);
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
        map.setOnInfoWindowClickListener(clusterManager);

        for (Church church : churches) {
            clusterManager.addItem(new ChurchClusterItem(church));
        }
        clusterManager.cluster();
    }
}
