package com.frama.miserend.hu.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;

/**
 * Created by Balazs on 2018. 02. 07..
 */

public class LocationRetriever {

    public enum LocationError {
        PERMISSION, COULD_NOT_RETRIEVE
    }

    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 10;

    public static final int LOCATION_SETTINGS_REQUEST_CODE = 11;

    private final Fragment fragment;
    private final LocationResultListener locationResultListener;

    public LocationRetriever(Fragment fragment, LocationResultListener locationResultListener) {
        this.fragment = fragment;
        this.locationResultListener = locationResultListener;
    }

    public void getLastKnownLocation() {
        if (checkLocationPermission()) {
            getLocation();
        }
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(fragment.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_LOCATION);

            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.getContext());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        locationResultListener.onLocationRetrieved(location);
                    } else {
                        locationResultListener.onLocationError(LocationError.COULD_NOT_RETRIEVE);
                    }
                }).addOnFailureListener(e -> {
                        locationResultListener.onLocationError(LocationError.COULD_NOT_RETRIEVE);
                });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    locationResultListener.onLocationError(LocationError.PERMISSION);
                }
            }
        }
    }

    public interface LocationResultListener {

        void onLocationRetrieved(Location location);

        void onLocationError(LocationError error);
    }
}
