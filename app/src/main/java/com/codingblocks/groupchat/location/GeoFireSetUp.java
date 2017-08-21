package com.codingblocks.groupchat.location;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.model.Location;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.codingblocks.groupchat.utils.FirebaseUserID;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by piyush on 20/8/17.
 */

public class GeoFireSetUp extends Service implements CONSTANTS{
    private Context context;
    private SuperPrefs superPrefs;
    private GeoFire geoFire;
    private DatabaseReference locationReference;

    public void setUpGeoFire(){

        String userId = FirebaseUserID.getFirebaseUserId(context);
        Log.e("setUpGeoFire: ","user id is " + userId );
        locationReference = FirebaseReference.userReference.child(userId).child(LOCATION_KEY_FIREBASE);
        geoFire = new GeoFire(locationReference);

        superPrefs = new SuperPrefs(context);

        locationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Location newLocation = dataSnapshot.getValue(Location.class);

                GeoQuery geoQuery = geoFire.queryAtLocation(
                        new GeoLocation(Double.parseDouble(newLocation.getLat())
                                ,Double.parseDouble(newLocation.getLon())),1);

                Log.e( "onDataChange: ","LOCATION QUERY LISTENER SET" );
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        print("Entered the method");
                    }

                    @Override
                    public void onKeyExited(String key) {
                        Location location = new Location(superPrefs.getString(LONGITUDE_KEY_FIREBASE),
                                superPrefs.getString(LATTITUDE_KEY_FIREBASE));
                        locationReference.setValue(location);
                        print("onKeyExited");
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {
                        superPrefs.setString(LONGITUDE_KEY_FIREBASE,String.valueOf(location.longitude));
                        superPrefs.setString(LATTITUDE_KEY_FIREBASE,String.valueOf(location.latitude));
                        print("onKeyMoved");
                    }

                    @Override
                    public void onGeoQueryReady() {

                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void print(String method){
        superPrefs = new SuperPrefs(context);
        Log.e(method,"lattitude" + superPrefs.getString(LATTITUDE_KEY_FIREBASE) );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        context = getApplicationContext();
        setUpGeoFire();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        setUpGeoFire();
        return null;
    }
}
