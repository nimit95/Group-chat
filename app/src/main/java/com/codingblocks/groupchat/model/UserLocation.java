package com.codingblocks.groupchat.model;

/**
 * Created by piyush on 20/8/17.
 */

public class Location {
    private String lon,lat;

    public Location(){}
    public Location(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getLon() {

        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
