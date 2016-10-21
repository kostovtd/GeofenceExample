package com.geofencingexample;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public class MapsPresenterImpl implements MapsPresenter, LocationCallback{

    private static final String TAG = MapsPresenterImpl.class.getSimpleName();
    private MapsView view;
    private GoogleLocationApiManager googleLocationApiManager;
    private GeofencingManager geofencingManager;

    public MapsPresenterImpl(MapsView view, FragmentActivity fragmentActivity, Context context) {
        if(view == null) throw new NullPointerException("view can not be NULL");
        if(fragmentActivity == null) throw new NullPointerException("fragmentActivity can not be NULL");
        if(context == null) throw new NullPointerException("context can not be NULL");

        this.view = view;
        this.googleLocationApiManager = new GoogleLocationApiManager(fragmentActivity, context);
        this.googleLocationApiManager.setLocationCallback(this);
        this.googleLocationApiManager.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        view.updateLocationOnMap(location);
    }

    @Override
    public void connectToLocationService() {
        Log.d(TAG, "connectToLocationService: hit");
        googleLocationApiManager.connect();
    }

    @Override
    public void disconnectFromLocationService() {
        Log.d(TAG, "disconnectFromLocationService: hit");
        googleLocationApiManager.disconnect();
    }
}
