package com.codingblocks.groupchat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.recyclerAdapters.ChatFeedRecyclerAdapter;
import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.realm.RealmController;
import com.codingblocks.groupchat.realm.RealmModels.RGroup;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChatFeed;
    private EditText userMessage;
    private ChatFeedRecyclerAdapter chatFeedRecyclerAdapter;
    private Realm realm = null;
    private RealmChangeListener realmListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
        setupAdapter();
    }

    private void init() {
        rvChatFeed = (RecyclerView) findViewById(R.id.rv_chat_feed);
        userMessage = (EditText) findViewById(R.id.user_message);
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rvChatFeed.setLayoutManager(llm);
        rvChatFeed.setHasFixedSize(false);

        Message msg = new Message(" Piyush is user","piyush6348","12:00","Group 1");
        Message msg2 = new Message(" Nimit is user","nimitagg95","12:50","Group 2");

        List<Message> listOfMessages;
        listOfMessages = new ArrayList<>();
        listOfMessages.add(msg);
        listOfMessages.add(msg2);
        listOfMessages.add(msg2);
        listOfMessages.add(msg2);
        listOfMessages.add(msg2);
        listOfMessages.add(msg);
        listOfMessages.add(msg);

        realm = Realm.getDefaultInstance();
        RealmController.addToRealm(listOfMessages,this);
        RealmResults<RMessage> results = realm.where(RMessage.class).equalTo("groupID","Group 2").findAll();
        chatFeedRecyclerAdapter = new ChatFeedRecyclerAdapter(this, results,true);
        rvChatFeed.setAdapter(chatFeedRecyclerAdapter);

    }

    /*
    private List<Message> fetchFromRealm() {
        RealmResults<RGroup> result = RealmController.fetchChats("Group 1");

        List<Message> res;
        res = new ArrayList<>();

        return res;


        for( int ct = 1; ct <= result.size(); ct++)
        {
            RGroup grp = result.get(ct - 1);
            Message msg = new Message(grp.getMessage(),grp.getFirebaseUserID()
                                        ,grp.getTimeStamp(),grp.getGroupID());
            res.add(msg);
        }
        return  res;
    }

    private void addToRealm(List<Message> listOfMessages) {
        RealmController.addToRealm(listOfMessages,this);
    }
    private void registerRealDatabaseChangeListener() {

    }*/
}
