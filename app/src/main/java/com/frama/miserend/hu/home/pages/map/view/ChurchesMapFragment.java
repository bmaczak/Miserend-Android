package com.frama.miserend.hu.home.pages.map.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.home.pages.map.viewmodel.ChurchesMapViewModel;
import com.frama.miserend.hu.router.Router;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import net.sharewire.googlemapsclustering.Cluster;
import net.sharewire.googlemapsclustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class ChurchesMapFragment extends SupportMapFragment implements OnMapReadyCallback, ClusterManager.Callbacks<ChurchClusterItem>, GoogleMap.OnInfoWindowClickListener {

    @Inject
    ChurchesMapViewModel churchesMapViewModel;
    @Inject
    Router router;

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
        clusterManager = new ClusterManager<>(getActivity(), map);
        clusterManager.setCallbacks(this);
        map.setOnCameraIdleListener(clusterManager);
        map.setOnInfoWindowClickListener(this);
        if (churchesMapViewModel.getChurcesLiveData().getValue() != null) {
            addPins(churchesMapViewModel.getChurcesLiveData().getValue());
        }
    }

    private void addPins(List<Church> churches) {
        List<ChurchClusterItem> clusterItems = new ArrayList<>();
        for (Church church : churches) {
            clusterItems.add(new ChurchClusterItem(church));
        }
        clusterManager.setItems(clusterItems);
    }

    @Override
    public boolean onClusterClick(@NonNull Cluster<ChurchClusterItem> cluster) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ChurchClusterItem item : cluster.getItems()) {
            builder.include(new LatLng(item.getLatitude(), item.getLongitude()));
        }
        final LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        return true;
    }

    @Override
    public boolean onClusterItemClick(@NonNull ChurchClusterItem clusterItem) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Cluster<ChurchClusterItem> cluster = (Cluster) marker.getTag();
        router.showChurchDetails(cluster.getItems().get(0).getChurch());
    }
}
