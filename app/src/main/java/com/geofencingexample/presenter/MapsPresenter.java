package com.geofencingexample.presenter;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public interface MapsPresenter {

    void connectToLocationService();
    void disconnectFromLocationService();
    void fetchCompanyLocations();
    void onMapReady();
}
