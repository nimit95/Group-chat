package com.codingblocks.groupchat.realm.RealmModels;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by piyush on 6/8/17.
 */

public class RGroupList extends RealmObject {
    private RealmList<RGroup> groupRealmList;

    public RealmList<RGroup> getGroupRealmList() {
        return groupRealmList;
    }

    public void setGroupRealmList(RealmList<RGroup> groupRealmList) {
        this.groupRealmList = groupRealmList;
    }
}
