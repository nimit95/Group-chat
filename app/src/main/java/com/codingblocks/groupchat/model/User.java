package com.codingblocks.groupchat.model;

import java.util.ArrayList;

/**
 * Created by nimit on 5/8/17.
 */


/*data class User(val userId:String,
                val Name: String,
                val usersGroup:ArrayList<String>)*/
public class User {
    String userId, name;
    ArrayList<String> usersGroup;

    public User(){

    }
    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;

    }

    public User(String userId, String name, ArrayList<String> usersGroup) {
        this.userId = userId;
        this.name = name;
        this.usersGroup = usersGroup;
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
}
