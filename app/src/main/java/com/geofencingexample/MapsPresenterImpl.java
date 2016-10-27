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
    private List<CompanyLocation> companyLocationList = new ArrayList<>();

    public MapsPresenterImpl(MapsView view, FragmentActivity fragmentActivity, Context context) {
        if(view == null) throw new NullPointerException("view can not be NULL");
        if(fragmentActivity == null) throw new NullPointerException("fragmentActivity can not be NULL");
        if(context == null) throw new NullPointerException("context can not be NULL");

        this.view = view;
        this.googleLocationApiManager = new GoogleLocationApiManager(fragmentActivity, context);
        this.googleLocationApiManager.setLocationCallback(this);

        this.geofencingManager = new GeofencingManager(this.googleLocationApiManager, context);
        this.geofencingManager.setmGeofenceCallback(this);

        this.view.generateMap();
    }


    @Override
    public void onLocationApiManagerConnected() {
        fetchCompanyLocations();

        List<Geofence> geofenceList = new ArrayList<>();
        for(CompanyLocation companyLocation : companyLocationList) {
            geofenceList.add(companyLocation.getGeofence());
        }

        geofencingManager.addGeofences(geofenceList);
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
        geofencingManager.removeGeofences();
    }

    @Override
    public void fetchCompanyLocations() {
        LatLng interExpoLatLng = new LatLng(42.64923011, 23.39556813);
        LatLng metroLatLng = new LatLng(42.64685481, 23.39321852);
        LatLng sirmaLatLng = new LatLng(42.65430002, 23.39087963);

        Geofence interExpoGeofence = new Geofence.Builder()
                .setRequestId("Inter Expo Center")
                .setCircularRegion(interExpoLatLng.latitude, interExpoLatLng.longitude, 100.0f)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(60 * 60 * 1000)
                .build();

        Geofence metroGeofence = new Geofence.Builder()
                .setRequestId("Metro")
                .setCircularRegion(metroLatLng.latitude, metroLatLng.longitude, 100.0f)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(60 * 60 * 1000)
                .build();

        Geofence sirmaGeofence = new Geofence.Builder()
                .setRequestId("Sirma")
                .setCircularRegion(sirmaLatLng.latitude, sirmaLatLng.longitude, 100.0f)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(60 * 60 * 1000)
                .build();

        CompanyLocation interExpoLocation = new CompanyLocation(interExpoLatLng, interExpoGeofence);
        CompanyLocation metroLocation = new CompanyLocation(metroLatLng, metroGeofence);
        CompanyLocation sirmaLocation = new CompanyLocation(sirmaLatLng, sirmaGeofence);

        companyLocationList.add(interExpoLocation);
        companyLocationList.add(metroLocation);
        companyLocationList.add(sirmaLocation);
    }


    @Override
    public void onGeofenceResultAvailable() {
        view.showGeofences(companyLocationList);
    }


    @Override
    public void onMapReady() {
        googleLocationApiManager.connect();
    }
}
