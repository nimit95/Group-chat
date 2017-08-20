package com.codingblocks.groupchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FixedWidthGif {
    @SerializedName("url")
    @Expose
    private String gifUrl;

    public String getGifUrl() {
        return gifUrl;
    }
}
