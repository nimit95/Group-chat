package com.codingblocks.groupchat.network;

import com.codingblocks.groupchat.places.PlacesApiResult;
import com.codingblocks.groupchat.utils.CONSTANTS;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by piyush on 21/8/17.
 */

public interface PlacesApiInterface {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CONSTANTS.PLACES_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @GET("/api/place/nearbysearch/json?location=29.0346817,77.236305&radius=10000&type=restaurant&sensor=true&key=AIzaSyAgLoEr4UJs1JUYu6GckG0nKUYDpo2BR8k")
    Call<PlacesApiResult> placesApiResult;
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=29.0346817,77.236305&radius=10000&type=restaurant&sensor=true&key=AIzaSyAgLoEr4UJs1JUYu6GckG0nKUYDpo2BR8k
}
