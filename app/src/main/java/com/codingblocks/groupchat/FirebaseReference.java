package com.codingblocks.groupchat;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by piyush on 4/8/17.
 */

public class FirebaseReference extends Application {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference userReference = database.getReference("users");
    public static DatabaseReference groupsReference = database.getReference("groups");
}
