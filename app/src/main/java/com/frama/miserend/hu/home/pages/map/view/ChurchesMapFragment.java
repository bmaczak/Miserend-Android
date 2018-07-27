package com.frama.miserend.hu.home.pages.map.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.frama.miserend.hu.R;
import com.frama.miserend.hu.database.miserend.entities.Church;
import com.frama.miserend.hu.home.pages.map.viewmodel.ChurchesMapViewModel;
import com.frama.miserend.hu.location.LocationManager;
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
import net.sharewire.googlemapsclustering.DefaultIconGenerator;
import net.sharewire.googlemapsclustering.IconStyle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Balazs on 2018. 02. 13..
 */

public class ChurchesMapFragment extends SupportMapFragment implements OnMapReadyCallback, ClusterManager.Callbacks<ChurchClusterItem>, GoogleMap.OnInfoWindowClickListener, LocationManager.LocationResultListener {

    private static int CURRENT_LOCATION_ZOOM = 14;

    @Inject
    ChurchesMapViewModel churchesMapViewModel;
    @Inject
    Router router;
    @Inject
    LocationManager locationManager;

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
        locationManager.registerListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locationManager.unregisterListener(this);
    }


    private void onChurchesLoaded(List<Church> churches) {
        if (map != null) {
            addPins(churches);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setupClustering();
        map.setOnInfoWindowClickListener(this);
        if (churchesMapViewModel.getChurcesLiveData().getValue() != null) {
            addPins(churchesMapViewModel.getChurcesLiveData().getValue());
        }
        if (locationManager.hasPermission()) {
            locationManager.getLastKnownLocation(false);
            map.setMyLocationEnabled(true);
        }
    }

    private void setupClustering() {
        clusterManager = new ClusterManager<>(getActivity(), map);
        clusterManager.setCallbacks(this);
        DefaultIconGenerator<ChurchClusterItem> generator = new DefaultIconGenerator<>(getActivity());
        IconStyle iconStyle = new IconStyle.Builder(getActivity())
                .setClusterBackgroundColor(getResources().getColor(R.color.colorPrimary))
                .setClusterIconResId(R.drawable.map_pin).build();
        generator.setIconStyle(iconStyle);
        clusterManager.setIconGenerator(generator);
        map.setOnCameraIdleListener(clusterManager);
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

    @Override
    public void onLocationRetrieved(Location location) {
        LatLng redmond = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(redmond, CURRENT_LOCATION_ZOOM));
    }

    @Override
    public void onLocationError(LocationManager.LocationError error) {

    }
}
