package com.codingblocks.groupchat.realm;

import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.realm.RealmModels.RGroup;

import java.util.List;

import io.realm.Realm;

/**
 * Created by piyush on 5/8/17.
 */

public class RealmController {

    public static void addToRealm(final List<Message> msgList){
        Realm realm=null;

        try {
            realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    for(int i=0;i<msgList.size();i++)
                    {
                        Message msg = msgList.get(i);

                        RGroup elem = realm.createObject(RGroup.class);
                        elem.setMessage(msg.getMessage());
                        elem.setFirebaseUserID(msg.getFirebaseUserID());
                        elem.setTimeStamp(msg.getTimestamp());

                        realm.insertOrUpdate(elem);
                    }
                }
            });
        }finally {
            if(realm!=null)
                realm.close();
        }
    }
}
