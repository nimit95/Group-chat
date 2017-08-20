package com.codingblocks.groupchat.model;

import java.util.ArrayList;

/**
 * Created by nimit on 5/8/17.
 */


/*data class User(val userId:String,
                val Name: String,
                val usersGroup:ArrayList<String>)*/
public class User {
    private String userId, name;
    private ArrayList<String> usersGroup;

    private Location location;
    public User(){

    }

    public User(String userId, String name, ArrayList<String> usersGroup, Location location) {
        this.userId = userId;
        this.name = name;
        this.usersGroup = usersGroup;
        this.location = location;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUsersGroup() {
        return usersGroup;
    }

    public void setUsersGroup(ArrayList<String> usersGroup) {
        this.usersGroup = usersGroup;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
