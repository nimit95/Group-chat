package com.codingblocks.groupchat.location;

import android.content.Context;

import com.codingblocks.groupchat.FirebaseReference;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by piyush on 20/8/17.
 */

public class GeoFireSetUp {
    private Context context;
    public GeoFireSetUp(Context context){
        this.context = context;
    }
    public void setUpGeoFire(){


        DatabaseReference locationReference = FirebaseReference.userReference;

    }
}
