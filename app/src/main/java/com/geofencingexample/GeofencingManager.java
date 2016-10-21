package com.geofencingexample;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.List;

/**
 * Created by todor.kostov on 20.10.2016 г..
 */

public class GeofencingManager {

    private static final String TAG = GeofencingManager.class.getSimpleName();


    private GoogleLocationApiManager mGoogleLocationApiManager;
    private List<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private Context mContext;

    public GeofencingManager(GoogleLocationApiManager googleLocationApiManager, List<Geofence> geofenceList, Context context) {
        if(googleLocationApiManager == null) throw new NullPointerException("googleLocationApiManager can not be NULL");
        if(geofenceList == null) throw new NullPointerException("geofenceList can not be NULL");
        if(context == null) throw new NullPointerException("context can not be NULL");

        this.mGoogleLocationApiManager = googleLocationApiManager;
        this.mGeofenceList = geofenceList;
        this.mContext = context;
    }


    public void addGeofences() {

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
}
