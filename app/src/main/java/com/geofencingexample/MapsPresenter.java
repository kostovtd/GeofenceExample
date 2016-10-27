package com.geofencingexample;

import com.google.android.gms.location.Geofence;

import java.util.List;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public interface MapsPresenter {

    void connectToLocationService();
    void disconnectFromLocationService();
    void fetchCompanyLocations();
    void onMapReady();
}
