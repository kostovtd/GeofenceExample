package com.geofencingexample;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public class GeofencingManager implements ResultCallback<Status>{

    private static final String TAG = GeofencingManager.class.getSimpleName();


    private GoogleLocationApiManager mGoogleLocationApiManager;
    private GeofenceCallback mGeofenceCallback;
    private List<Geofence> mGeofenceList = new ArrayList<>();
    private PendingIntent mGeofencePendingIntent;
    private Context mContext;

    public GeofencingManager(GoogleLocationApiManager googleLocationApiManager, Context context) {
        if (googleLocationApiManager == null)
            throw new NullPointerException("googleLocationApiManager can not be NULL");
        if (context == null) throw new NullPointerException("context can not be NULL");

        this.mGoogleLocationApiManager = googleLocationApiManager;
        this.mContext = context;
    }


    public void addGeofences(List<Geofence> geofenceList) {
        if (geofenceList != null) {
            mGeofenceList.addAll(geofenceList);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleLocationApiManager.getmGoogleApiClient(),
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        }
    }


    public void removeGeofences() {
        LocationServices.GeofencingApi.removeGeofences(
                mGoogleLocationApiManager.getmGoogleApiClient(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }


    private PendingIntent getGeofencePendingIntent() {
        if(mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }

        Intent intent = new Intent(mContext, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public void setmGeofenceCallback(GeofenceCallback mGeofenceCallback) {
        this.mGeofenceCallback = mGeofenceCallback;
    }



    @Override
    public void onResult(@NonNull Status status) {
        if(status.isSuccess())
            if(mGeofenceCallback != null)
                mGeofenceCallback.onGeofenceResultAvailable();
    }
}
