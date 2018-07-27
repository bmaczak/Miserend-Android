package com.frama.miserend.hu.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Balazs on 2018. 02. 07..
 */

public class LocationManager {

    public enum LocationError {
        PERMISSION, COULD_NOT_RETRIEVE
    }

    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 10;

    public static final int LOCATION_SETTINGS_REQUEST_CODE = 11;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 12;

    private final Activity activity;
    private final List<LocationResultListener> locationResultListeners;

    public LocationManager(Activity activity) {
        this.activity = activity;
        this.locationResultListeners = new ArrayList<>();
    }

    public void registerListener(LocationResultListener locationResultListener) {
        locationResultListeners.add(locationResultListener);
    }

    public void unregisterListener(LocationResultListener locationResultListener) {
        locationResultListeners.remove(locationResultListener);
    }

    public void getLastKnownLocation() {
        if (checkLocationPermission()) {
            getLocation();
        }
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_LOCATION);
            } else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_LOCATION);

            return false;
        } else {
            return true;
        }
    }

    public void showLocationSettings() {
        activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), LocationManager.LOCATION_SETTINGS_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        notify(location);
                    } else {
                        notify(LocationError.COULD_NOT_RETRIEVE);
                    }
                }).addOnFailureListener(e -> {
                        notify(LocationError.COULD_NOT_RETRIEVE);
                });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    notify(LocationError.PERMISSION);
                }
            }
        }
    }

    private void notify(Location location) {
        for (LocationResultListener listener : locationResultListeners) {
            listener.onLocationRetrieved(location);
        }
    }

    private void notify(LocationError locationError) {
        for (LocationResultListener listener : locationResultListeners) {
            listener.onLocationError(locationError);
        }
    }

    public interface LocationResultListener {

        void onLocationRetrieved(Location location);

        void onLocationError(LocationError error);
    }
}
