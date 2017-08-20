package com.codingblocks.groupchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nimit on 20/8/17.
 */

public class GifyNetworkData {
    @SerializedName("data")
    @Expose
    private ArrayList<GifyData> gifyDataList;

    public ArrayList<GifyData> getGifyDataList() {
        return gifyDataList;
    }
}

