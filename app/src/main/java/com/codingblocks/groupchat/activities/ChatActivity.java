package com.codingblocks.groupchat.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.recyclerAdapters.ChatFeedRecyclerAdapter;
import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.realm.RealmController;
import com.codingblocks.groupchat.realm.RealmModels.RGroup;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;
import com.codingblocks.groupchat.utils.FirebaseUserID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChatFeed;
    private EditText userMessage;
    private ChatFeedRecyclerAdapter chatFeedRecyclerAdapter;
    private FloatingActionButton buttonSend;
    private ArrayList<Message> messageList;

    List<Message> listOfMessages;
    private String groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        groupId = intent.getStringExtra("group_id");
        messageList = new ArrayList<>();

        init();
        setupAdapter();
        setUpListener();
    }

    private void setUpListener() {

        FirebaseReference.groupsReference.child(getGroupID()).child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot messageShot:dataSnapshot.getChildren()) {
                    messageList.add(messageShot.getValue(Message.class));
                    Log.e("onDataChange: ", messageList.get(messageList.size()-1).getMessage());
                }

                listOfMessages = messageList;
                RealmController.clearAll();
                RealmController.addToRealm(listOfMessages,ChatActivity.this, getGroupID());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        rvChatFeed = (RecyclerView) findViewById(R.id.rv_chat_feed);
        userMessage = (EditText) findViewById(R.id.user_message);
        buttonSend = (FloatingActionButton) findViewById(R.id.btn_send);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = userMessage.getText().toString();
                if (message.length() > 0 ){
                    sendMessageToFirebase(message);
                    userMessage.setText("");
                }
            }
        });
    }

    private void sendMessageToFirebase(String message) {
        Message obj = new Message(message, FirebaseUserID.getFirebaseUserId(this),
                DateFormat.getDateTimeInstance().format(new Date())
                        ,getGroupID());
        messageList.add(obj);
        FirebaseReference.groupsReference.child(getGroupID()).child("message")
                .setValue(messageList);
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rvChatFeed.setLayoutManager(llm);
        rvChatFeed.setHasFixedSize(false);


        listOfMessages = getData();
        RealmController.addToRealm(listOfMessages,this, getGroupID());

        RealmResults<RMessage> results = RealmController.fetchChats(getGroupID());
        chatFeedRecyclerAdapter = new ChatFeedRecyclerAdapter(this, results,true);
        rvChatFeed.setAdapter(chatFeedRecyclerAdapter);
        rvChatFeed.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    rvChatFeed.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int scrollTo = rvChatFeed.getAdapter().getItemCount() - 1;
                            scrollTo = scrollTo >= 0 ? scrollTo : 0;
                            rvChatFeed.scrollToPosition(scrollTo);
                        }
                    }, 10);
                }
            }
        });
    }

    private String getGroupID(){
        return groupId;
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
