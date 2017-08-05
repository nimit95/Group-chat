package com.codingblocks.groupchat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.recyclerAdapters.ChatFeedRecyclerAdapter;
import com.codingblocks.groupchat.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChatFeed;
    private EditText userMessage;
    private ChatFeedRecyclerAdapter chatFeedRecyclerAdapter;
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

        Message msg =new Message(" Piyush is user","piyush6348","12:00");
        Message msg2 =new Message(" Nimit is user","nimitagg95","12:50");
        List<Message> listOfMessages;
        listOfMessages = new ArrayList<>();
        listOfMessages.add(msg);
        listOfMessages.add(msg2);
        listOfMessages.add(msg2);
        listOfMessages.add(msg2);
        listOfMessages.add(msg2);
        listOfMessages.add(msg);
        listOfMessages.add(msg);

        chatFeedRecyclerAdapter = new ChatFeedRecyclerAdapter(listOfMessages, this);
        rvChatFeed.setAdapter(chatFeedRecyclerAdapter);
    }
}
