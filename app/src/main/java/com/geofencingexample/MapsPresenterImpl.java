package com.geofencingexample;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public class MapsPresenterImpl implements MapsPresenter, LocationCallback, GeofenceCallback{

    private static final String TAG = MapsPresenterImpl.class.getSimpleName();
    private MapsView view;
    private GoogleLocationApiManager googleLocationApiManager;
    private GeofencingManager geofencingManager;

    private List<Geofence> geofenceList = new ArrayList<>();
    private List<LatLng> latLngList = new ArrayList<>();

    public MapsPresenterImpl(MapsView view, FragmentActivity fragmentActivity, Context context) {
        if(view == null) throw new NullPointerException("view can not be NULL");
        if(fragmentActivity == null) throw new NullPointerException("fragmentActivity can not be NULL");
        if(context == null) throw new NullPointerException("context can not be NULL");

        this.view = view;
        this.googleLocationApiManager = new GoogleLocationApiManager(fragmentActivity, context);
        this.googleLocationApiManager.setLocationCallback(this);
        this.googleLocationApiManager.connect();
        this.geofencingManager = new GeofencingManager(this.googleLocationApiManager, context);
        this.geofencingManager.setmGeofenceCallback(this);
    }


    @Override
    public void onLocationApiManagerConnected() {
        fetchGeofences();
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

    @Override
    public void fetchGeofences() {
        LatLng interExpo = new LatLng(42.64923011, 23.39556813);
        LatLng metro = new LatLng(42.64685481, 23.39321852);
        LatLng sirma = new LatLng(42.65430002, 23.39087963);

        latLngList.add(interExpo);
        latLngList.add(metro);
        latLngList.add(sirma);

        geofenceList.add(new Geofence.Builder()
                .setRequestId("Inter Expo Center")
                .setCircularRegion(interExpo.latitude, interExpo.longitude, 100.0f)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(60 * 60 * 1000)
                .build());

        geofenceList.add(new Geofence.Builder()
                .setRequestId("Metro")
                .setCircularRegion(metro.latitude, metro.longitude, 100.0f)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(60 * 60 * 1000)
                .build());

        geofenceList.add(new Geofence.Builder()
                .setRequestId("Sirma")
                .setCircularRegion(sirma.latitude, sirma.longitude, 100.0f)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(60 * 60 * 1000)
                .build());

        geofencingManager.addGeofences(geofenceList);
    }


    @Override
    public void onGeofenceResultAvailable() {
        view.showGeofences(latLngList);
    }
}
