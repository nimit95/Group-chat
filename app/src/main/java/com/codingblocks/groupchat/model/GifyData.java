package com.codingblocks.groupchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GifyData {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("images")
    @Expose
    private GifyImages gifyImages;

    public String getType() {
        return type;
    }

    public GifyImages getGifyImages() {
        return gifyImages;
    }
}
