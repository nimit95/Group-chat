package com.codingblocks.groupchat.realm.RealmModels;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by piyush on 5/8/17.
 */

public class RGroup extends RealmObject{
    private String groupID;
    private RealmList<RMessage> realmMessageList;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public RealmList<RMessage> getRealmMessageList() {
        return realmMessageList;
    }

    public void setRealmMessageList(RealmList<RMessage> realmMessageList) {
        this.realmMessageList = realmMessageList;
    }
}
