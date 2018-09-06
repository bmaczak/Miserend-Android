package com.frama.miserend.hu.location;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Balazs on 2018. 02. 07..
 */

public class LocationPermissionHelper {

    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 10;

    public static final int LOCATION_SETTINGS_REQUEST_CODE = 11;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 12;

    private final Activity activity;

    public LocationPermissionHelper(Activity activity) {
        this.activity = activity;
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

    public void showLocationSettings() {
        activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), LocationPermissionHelper.LOCATION_SETTINGS_REQUEST_CODE);
    }

    public boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
