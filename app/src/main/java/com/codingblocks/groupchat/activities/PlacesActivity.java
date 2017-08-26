package com.codingblocks.groupchat.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.network.PlacesApiInterface;
import com.codingblocks.groupchat.places.PlacesApiResult;
import com.codingblocks.groupchat.places.Result;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Places;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlacesActivity extends FragmentActivity implements OnConnectionFailedListener, CONSTANTS {
    private GoogleApiClient mGoogleApiClient;
    private SuperPrefs prefs;
    private String PROXIMITY_RADIUS = "10000";
    //Subscription subscription;

    private Spinner availableTypeOfPlacesSpinner;
    private RecyclerView placesApiResultRecyclerView;
    private List<String> listOfTypesOfPlaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        initialise();
        setUpSpinner();
        prefs = new SuperPrefs(this);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();


        //getPlaces("restaurant");
    }

    private void initialise() {
        availableTypeOfPlacesSpinner = (Spinner) findViewById(R.id.available_types_of_places);
        placesApiResultRecyclerView = (RecyclerView) findViewById(R.id.places_api_result_rv);
        listOfTypesOfPlaces = new ArrayList<>();

        listOfTypesOfPlaces.add("restaurant");
        listOfTypesOfPlaces.add("bar");
        listOfTypesOfPlaces.add("lodging");
        listOfTypesOfPlaces.add("food");
        listOfTypesOfPlaces.add("store");
        listOfTypesOfPlaces.add("point_of_interest");
        listOfTypesOfPlaces.add("meal_delivery");
        listOfTypesOfPlaces.add("meal_takeaway");
    }

    private void setUpSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listOfTypesOfPlaces);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availableTypeOfPlacesSpinner.setAdapter(arrayAdapter);
        availableTypeOfPlacesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String choosen = adapterView.getItemAtPosition(i).toString();
                Log.e("onItemSelected: ", choosen);
                getPlaces(choosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("onNothingSelected: ", "Nothing");
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("onConnectionFailed: ", "Google client connection failed");
    }

    private String getURL(String nearbyPlace) {
        String longitude = prefs.getString(LONGITUDE_KEY_FIREBASE);
        String lattitude = prefs.getString(LATTITUDE_KEY_FIREBASE);

        String url ="json?"+
                "location=" + lattitude + "," + longitude +
                "&radius=" + PROXIMITY_RADIUS +
                "&type=" + nearbyPlace +
                "&sensor=true" +
                "&key=" + PLACES_KEY;
        Log.e("getURL: ",PLACES_API_BASE_URL+url );
        return url;
    }

    public void getPlaces(String nearbyPlace) {

        // Places API Async API call.
        PlacesApiInterface placesApiInterface = PlacesApiInterface.retrofit.create(PlacesApiInterface.class);
        Call<PlacesApiResult> call = placesApiInterface.placesApiResult(getURL(nearbyPlace));

        call.enqueue(new Callback<PlacesApiResult>() {
            @Override
            public void onResponse(Call<PlacesApiResult> call, Response<PlacesApiResult> response) {
                    //PlacesApiResult obj = call.execute().body();

                List<Result> resultList;
                if(response.body() == null)
                    Log.e("onResponse: null ", response.errorBody().toString());
                else{
                    Log.e("onResponse: ", "obtained"+response.body());
                    resultList = response.body().getResults();

                    for (int i = 0; i < resultList.size(); i++) {
                        Log.e("Name " + resultList.get(i).getName(),
                                "vicinity " + resultList.get(i).getVicinity());
                    }
                }
            }
            @Override
            public void onFailure(Call<PlacesApiResult> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
        /*
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
                });*/
    }
}

