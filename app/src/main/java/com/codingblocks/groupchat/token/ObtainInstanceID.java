//package com.codingblocks.groupchat.token;
//
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
///**
// * Created by piyush on 4/8/17.
// */
//
//public class ObtainInstanceID extends FirebaseInstanceIdService {
//    public static String TAG="";
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//
//        TAG = this.getClass().getSimpleName();
//
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e(TAG, "onTokenRefresh: " + refreshedToken );
//
//    }
//}
