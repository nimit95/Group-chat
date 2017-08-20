package com.codingblocks.groupchat.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.codingblocks.groupchat.LoginActivity;
import com.codingblocks.groupchat.activities.MainActivity;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
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
    private LoginActivity loginActivityReference;
    public CurrentLocation(Context context){
        this.context = context;
        loginActivityReference = (LoginActivity) ((Activity)context);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        //Log.e( "CurrentLocation: ", "Heyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
    }
    public void setCurrentLocationAndMoveToNextActivity(){
        
        //askForPermission();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    Log.e("onSuccess: Lattitude", String.valueOf(location.getLatitude()));
                    Log.e("onSuccess: Longitude", String.valueOf(location.getLongitude()));
                    SuperPrefs prefs = new SuperPrefs(loginActivityReference);
                    prefs.setString("lon",String.valueOf(location.getLongitude()));
                    prefs.setString("lat",String.valueOf(location.getLatitude()));
                    //startMainActivity();
                }
            }
        });
    }
}
