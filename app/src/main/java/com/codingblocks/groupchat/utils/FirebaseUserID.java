package com.codingblocks.groupchat.utils;

import android.content.Context;

import com.codingblocks.groupchat.sharedPref.SuperPrefs;

/**
 * Created by piyush on 5/8/17.
 */

public class FirebaseUserID {

    public static String getFirebaseUserId(Context context) {

        SuperPrefs superPrefs = new SuperPrefs(context);
        return superPrefs.getString("user-id");
    }

    public static String getUserName(Context context) {
        SuperPrefs superPrefs = new SuperPrefs(context);
        return superPrefs.getString("user-name");
    }
}
