package com.codingblocks.groupchat.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
    private FloatingActionButton buttonSend;
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
        buttonSend = (FloatingActionButton) findViewById(R.id.btn_send);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = userMessage.getText().toString();
                if (message != null){
                    sendMessageToFirebase(message);
                }
            }
        });
    }

    private void sendMessageToFirebase(String message) {
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rvChatFeed.setLayoutManager(llm);
        rvChatFeed.setHasFixedSize(false);


        List<Message> listOfMessages = getData();
        RealmController.addToRealm(listOfMessages,this);

        RealmResults<RMessage> results = RealmController.fetchChats(getGroupID());
        chatFeedRecyclerAdapter = new ChatFeedRecyclerAdapter(this, results,true);
        rvChatFeed.setAdapter(chatFeedRecyclerAdapter);

    }

    String getGroupID(){
        return "Group 1";
    }
    private List<Message> getData() {
        Message msg = new Message(" Piyush is user","piyush6348","12:00","Group 1");
        Message msg2 = new Message(" Nimit is user","nimitagg95","12:50","Group 2");

        List<Message> listOfMessages;
        listOfMessages = new ArrayList<>();
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg2);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg2);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        listOfMessages.add(msg);
        return listOfMessages;
    }
}
