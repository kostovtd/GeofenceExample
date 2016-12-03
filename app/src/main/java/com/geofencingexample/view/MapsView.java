package com.geofencingexample.view;

import android.location.Location;

import com.geofencingexample.model.CompanyLocation;

import java.util.List;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public interface MapsView {

    void generateMap();
    void updateLocationOnMap(Location location);
    void showGeofences(List<CompanyLocation> companyLocationList);
}
