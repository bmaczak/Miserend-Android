package com.frama.miserend.hu.location;

import android.location.Location;

public class LocationData {
    private Location location;
    private LocationError locationError;

    public LocationData(Location location) {
        this.location = location;
    }

    public LocationData(LocationError locationError) {
        this.locationError = locationError;
    }

    public Location getLocation() {
        return location;
    }

    public LocationError getLocationError() {
        return locationError;
    }

    public boolean hasError() {
        return location == null;
    }
}
