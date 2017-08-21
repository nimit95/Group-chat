package com.codingblocks.groupchat.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.codingblocks.groupchat.utils.DataParser;
import com.codingblocks.groupchat.utils.UrlConnection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Places;


import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PlacesActivity extends FragmentActivity implements OnConnectionFailedListener, CONSTANTS {
    private GoogleApiClient mGoogleApiClient;
    private SuperPrefs prefs;
    private String PROXIMITY_RADIUS = "10000";
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        prefs = new SuperPrefs(this);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();


        getPlaces();
        // Background Thread
//        UrlConnection urlConnection = new UrlConnection();
//        String changedURL = urlConnection.readUrl(getURL("Nearby Place"));


        // UI Thread
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {

        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");

            Log.e("showNearbyPlaces: ", placeName + vicinity);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("onConnectionFailed: ", "Google client connection failed");
    }

    private String getURL(String nearbyPlace) {
        String longitude = prefs.getString(LONGITUDE_KEY_FIREBASE);
        String lattitude = prefs.getString(LATTITUDE_KEY_FIREBASE);

        String url = PLACES_API_BASE_URL +
                "location=" + lattitude + "," + longitude +
                "&radius=" + PROXIMITY_RADIUS +
                "&type=" + nearbyPlace +
                "&sensor=true" +
                "&key=" + PLACES_KEY;
        return url;
    }

    public void getPlaces() {
        Observable<String> observable = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {

                String url = getURL("restaurant");
                Log.e(" URL to be used", url);
                UrlConnection urlConnection = new UrlConnection();
                String changedURL = urlConnection.readUrl(url);

                Log.e("changed url ",changedURL );
                return changedURL;
            }
        });

        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e( "onCompleted: ","running" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError: ", e.getMessage());
                    }

                    @Override
                    public void onNext(String changedURL) {
                        List<HashMap<String, String>> nearbyPlacesList = null;
                        DataParser dataParser = new DataParser();
                        nearbyPlacesList = dataParser.parse(changedURL);

                        showNearbyPlaces(nearbyPlacesList);
                    }
                });
    }
}

