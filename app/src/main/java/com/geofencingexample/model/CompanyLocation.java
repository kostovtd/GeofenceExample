package com.geofencingexample.model;

/**
 * Created by todor.kostov on 27.10.2016 г..
 */

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

/**
 * Just a dummy container class in order to
 * collect {@link com.google.android.gms.location.Geofence}
 * and {@link com.google.android.gms.maps.model.LatLng} in one entity
 */
public class CompanyLocation {

    private LatLng coordinates;
    private Geofence geofence;

    public CompanyLocation(LatLng coordinates, Geofence geofence) {
        this.coordinates = coordinates;
        this.geofence = geofence;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public Geofence getGeofence() {
        return geofence;
    }

    public void setGeofence(Geofence geofence) {
        this.geofence = geofence;
    }
}
