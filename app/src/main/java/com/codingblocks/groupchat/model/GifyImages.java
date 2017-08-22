package com.codingblocks.groupchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GifyImages {
    @SerializedName("fixed_height")
    @Expose
    private FixedWidthGif fixedWidthGif;

    public FixedWidthGif getFixedWidthGif() {
        return fixedWidthGif;
    }
}
