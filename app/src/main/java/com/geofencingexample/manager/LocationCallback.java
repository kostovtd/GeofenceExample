package com.geofencingexample.manager;

import android.location.Location;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public interface LocationCallback {

    void onLocationChanged(Location location);
    void onLocationApiManagerConnected();

}
