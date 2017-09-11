package com.codingblocks.groupchat.realm;

import android.content.Context;
import android.util.Log;

import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.realm.RealmModels.RGroup;
import com.codingblocks.groupchat.realm.RealmModels.RGroupList;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by piyush on 5/8/17.
 */

public class RealmController {

    public static void addToRealm(final List<Message> msgList, Context context, String groupID){

        Realm realm=null;

        try {
            realm = Realm.getDefaultInstance();

            //RealmResults realmResults = realm.where(RGroupList.class)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    //RealmResults realmResults = realm.where(RGroupList.class).
                    for(int i=0;i<msgList.size();i++)
                    {

                        Message msg = msgList.get(i);

                        RMessage elem = realm.createObject(RMessage.class);
                        elem.setMessage(msg.getMessage());
                        elem.setFirebaseUserID(msg.getFirebaseUserID());
                        elem.setTimeStamp(msg.getTimestamp());
                        elem.setGroupID(msg.getGroupID());
                        elem.setUserName(msg.getUserName());
                        elem.setMessageType(msg.getMessageType());
                        realm.insertOrUpdate(elem);

                    }
                }
            });
        }finally {
            if(realm!=null)
                realm.close();
        }
    }

    public static RealmResults<RMessage> fetchChats(String groupID){
        Realm realm=null;
        realm = Realm.getDefaultInstance();
        RealmResults<RMessage> changes = realm.where(RMessage.class).equalTo("groupID",groupID).findAll();
        //realm.close();
        return changes;
    }
    public static void clearAll() {
        Realm realm=null;
        try {
            realm = Realm.getDefaultInstance();

            //RealmResults realmResults = realm.where(RGroupList.class)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(RMessage.class);
                }
            });
        }finally {
            if(realm!=null)
                realm.close();
        }
    }
}
