package com.codingblocks.groupchat.network;

import com.codingblocks.groupchat.model.GifyNetworkData;
import com.codingblocks.groupchat.utils.CONSTANTS;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by nimit on 20/8/17.
 */

public interface GifyTrendingGifInterface {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CONSTANTS.BASE_URL_GIFY)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/v1/gifs/trending?api_key=6653ca75049245fdb19afbe697f0c0b5&limit=15")
    Call<GifyNetworkData> getGifyTrendingGif();


}
