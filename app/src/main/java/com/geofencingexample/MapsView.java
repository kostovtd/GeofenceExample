package com.geofencingexample;

import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public interface MapsView {

    void updateLocationOnMap(Location location);
    void showGeofences(List<LatLng> latLngList);
}
