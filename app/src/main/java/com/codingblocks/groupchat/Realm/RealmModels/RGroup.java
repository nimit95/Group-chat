package com.codingblocks.groupchat.Realm.RealmModels;

import io.realm.RealmObject;

/**
 * Created by piyush on 5/8/17.
 */

public class RGroup extends RealmObject{
    private String groupID;


    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
