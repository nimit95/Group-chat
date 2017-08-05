package com.codingblocks.groupchat.realm;

import android.content.Context;
import android.util.Log;

import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.realm.RealmModels.RGroup;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by piyush on 5/8/17.
 */

public class RealmController {

    public static void addToRealm(final List<Message> msgList, Context context){

        Realm realm=null;

        try {
            realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    for(int i=0;i<msgList.size();i++)
                    {
                        Message msg = msgList.get(i);

                        RMessage elem = realm.createObject(RMessage.class);
                        elem.setMessage(msg.getMessage());
                        elem.setFirebaseUserID(msg.getFirebaseUserID());
                        elem.setTimeStamp(msg.getTimestamp());
                        elem.setGroupID(msg.getGroupID());
                        realm.insertOrUpdate(elem);
                        Log.e( "execute: ", msg.getMessage());
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
        return changes;
    }
}
