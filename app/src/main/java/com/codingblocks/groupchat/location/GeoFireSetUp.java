package com.codingblocks.groupchat.location;

import android.content.Context;
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

public class GeoFireSetUp implements CONSTANTS{
    private Context context;
    private SuperPrefs superPrefs;
    private GeoFire geoFire;
    private DatabaseReference locationReference;

    public GeoFireSetUp(Context context){
        this.context = context;
    }
    public void setUpGeoFire(){

        String userId = FirebaseUserID.getFirebaseUserId(context);
        Log.e("setUpGeoFire: ","user id is " + userId );
        locationReference = FirebaseReference.userReference.child(userId).child(LOCATION_KEY_FIREBASE);
        geoFire = new GeoFire(locationReference);

        superPrefs = new SuperPrefs(context);
        locationReference.addListenerForSingleValueEvent(new ValueEventListener() {
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

                    }

                    @Override
                    public void onKeyExited(String key) {
                        Location location = new Location(superPrefs.getString(LONGITUDE_KEY_FIREBASE),
                                superPrefs.getString(LATTITUDE_KEY_FIREBASE));
                        locationReference.setValue(location);
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {
                        superPrefs.setString(LONGITUDE_KEY_FIREBASE,String.valueOf(location.longitude));
                        superPrefs.setString(LATTITUDE_KEY_FIREBASE,String.valueOf(location.latitude));
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
}