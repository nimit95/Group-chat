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

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.LoginActivity;
import com.codingblocks.groupchat.activities.MainActivity;
import com.codingblocks.groupchat.activities.PlacesActivity;
import com.codingblocks.groupchat.model.UserLocation;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.codingblocks.groupchat.utils.FirebaseUserID;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by piyush on 20/8/17.
 */

public class CurrentLocation implements CONSTANTS {

    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LoginActivity loginActivityReference;
    private DatabaseReference locationReference;

    public CurrentLocation(Context context) {
        this.context = context;
        loginActivityReference = (LoginActivity) ((Activity) context);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        //Log.e( "CurrentLocation: ", "Heyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
    }

    public void setCurrentLocationAndMoveToNextActivity() {

        //askForPermission();

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.e("onSuccess: Lattitude", String.valueOf(location.getLatitude()));
                    Log.e("onSuccess: Longitude", String.valueOf(location.getLongitude()));
                    SuperPrefs prefs = new SuperPrefs(loginActivityReference);
                    prefs.setString(LONGITUDE_KEY_FIREBASE, String.valueOf(location.getLongitude()));
                    prefs.setString(LATTITUDE_KEY_FIREBASE, String.valueOf(location.getLatitude()));

                    setUpListener();
                    UserLocation userLocation = new UserLocation(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
                    FirebaseReference.userReference.child(prefs.getString("user-id")).child("location").setValue(userLocation);
                    startMainActivity();

                }
            }
        });
    }

    private void setUpListener() {
        Intent intentForService =new Intent(context,GeoFireSetUp.class);
        context.startService(intentForService);
    }

    private void startMainActivity(){

        SuperPrefs prefs = new SuperPrefs(context);
        Log.e("longitude from Prefs ",prefs.getString("lon") );
        Log.e("startMainActivity: ", prefs.getString("userName"));
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
       // context.finish();
    }
}
