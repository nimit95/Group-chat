package com.codingblocks.groupchat.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.codingblocks.groupchat.LoginActivity;
import com.codingblocks.groupchat.activities.MainActivity;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by piyush on 20/8/17.
 */

public class CurrentLocation {

    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public CurrentLocation(Context context){
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }
    public void setCurrentLocationAndMoveToNextActivity(){

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    Log.e("onSuccess: Lattitude", String.valueOf(location.getLatitude()));
                    Log.e("onSuccess: Longitude", String.valueOf(location.getLongitude()));
                    startMainActivity();
                }
            }
        });
    }
    private void startMainActivity(){

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
