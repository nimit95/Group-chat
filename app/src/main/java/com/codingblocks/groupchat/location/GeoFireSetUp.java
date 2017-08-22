package com.codingblocks.groupchat.location;

import android.content.Context;
import android.util.Log;

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.model.UserLocation;
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

        locationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserLocation newUserLocation = dataSnapshot.getValue(UserLocation.class);

                GeoQuery geoQuery = geoFire.queryAtLocation(
                        new GeoLocation(Double.parseDouble(newUserLocation.getLat())
                                ,Double.parseDouble(newUserLocation.getLon())),1);

                Log.e( "onDataChange: ","LOCATION QUERY LISTENER SET" );
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        print("Entered the method");
                    }

                    @Override
                    public void onKeyExited(String key) {
                        UserLocation userLocation = new UserLocation(superPrefs.getString(LONGITUDE_KEY_FIREBASE),
                                superPrefs.getString(LATTITUDE_KEY_FIREBASE));
                        locationReference.setValue(userLocation);
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
}
