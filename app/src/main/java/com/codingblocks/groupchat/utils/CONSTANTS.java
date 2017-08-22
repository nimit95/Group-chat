package com.codingblocks.groupchat.utils;

/**
 * Created by piyush on 5/8/17.
 */

public interface CONSTANTS {
    int OUR_MESSAGE = 1;
    int OTHERS_MESSAGE = 0;
    int MESSAGE_TYPE_TEXT = 0;
    int MESSAGE_TYPE_IMG = 1;
    int MESSAGE_TYPE_GIF = 2;
    int MESSAGE_TYPE_VIDEO =3;

    String BASE_URL_GIFY = "http://api.giphy.com";
    String LOCATION_KEY_FIREBASE="location";
    String LONGITUDE_KEY_FIREBASE="lon";
    String LATTITUDE_KEY_FIREBASE="lat";
    String PLACES_API_BASE_URL ="https://maps.googleapis.com/maps/api/place/nearbysearch/";
    String PLACES_KEY="AIzaSyAgLoEr4UJs1JUYu6GckG0nKUYDpo2BR8k";
}
