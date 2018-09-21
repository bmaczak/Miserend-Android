package com.frama.miserend.hu.location;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationRepository {

    private final Context context;

    private MutableLiveData<Location> location;
    private MutableLiveData<LocationError> locationError;

    public LocationRepository(Context context) {
        this.context = context;
        location = new MutableLiveData<>();
        locationError = new MutableLiveData<>();
    }

    public LiveData<Location> getLocation() {
        if (location.getValue() == null) {
            refreshLocation();
        }
        return location;
    }

    public MutableLiveData<LocationError> getLocationError() {
        return locationError;
    }

    public void refreshLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            this.location.setValue(location);
                            locationError.setValue(null);
                        } else {
                            locationError.setValue(LocationError.COULD_NOT_RETRIEVE);
                        }
                    })
                    .addOnFailureListener(e -> locationError.setValue(LocationError.COULD_NOT_RETRIEVE));
        } else {
            locationError.setValue(LocationError.PERMISSION);
        }
    }
}
