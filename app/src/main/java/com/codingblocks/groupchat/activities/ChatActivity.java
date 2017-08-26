package com.codingblocks.groupchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.recyclerAdapters.ChatFeedRecyclerAdapter;
import com.codingblocks.groupchat.adapters.recyclerAdapters.SearchedImagesRecyclerAdapter;
import com.codingblocks.groupchat.model.GifyNetworkData;
import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.network.GifyTrendingGifInterface;
import com.codingblocks.groupchat.realm.RealmController;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;
import com.codingblocks.groupchat.utils.FirebaseUserID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codingblocks.groupchat.utils.CONSTANTS.MESSAGE_TYPE_GIF;
import static com.codingblocks.groupchat.utils.CONSTANTS.MESSAGE_TYPE_TEXT;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChatFeed, imageGifSearch;
    private EditText userMessage;
    private ChatFeedRecyclerAdapter chatFeedRecyclerAdapter;
    private FloatingActionButton buttonSend;
    private ArrayList<Message> messageList;
    public SlidingUpPanelLayout slidingPanelLayout;
    private LinearLayout chatBarLinearLayout;

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

        userMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e( "onextChanged: ", charSequence.toString());
                if(charSequence.toString().compareToIgnoreCase("@gify")==0) {
                    slidingPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    getandDisplayGifs();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        slidingPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
               // 9gag, xkcd, gify


                if(newState==SlidingUpPanelLayout.PanelState.COLLAPSED && previousState!=SlidingUpPanelLayout.PanelState.EXPANDED) {
                    Log.e( "onPanelStateChanged: ", "working on initial opening");
                }
            }
        });
    }

    private void init() {
        rvChatFeed = (RecyclerView) findViewById(R.id.rv_chat_feed);
        imageGifSearch = (RecyclerView) findViewById(R.id.searched_images);
        userMessage = (EditText) findViewById(R.id.user_message);
        buttonSend = (FloatingActionButton) findViewById(R.id.btn_send);


        slidingPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        chatBarLinearLayout = (LinearLayout) findViewById(R.id.send_message_layout);
        chatBarLinearLayout.bringToFront();
        //chatBarLinearLayout.setZ((float) 5.0);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = userMessage.getText().toString();
                if (message.length() > 0 ){
                    sendMessageToFirebase(message, MESSAGE_TYPE_TEXT);
                    userMessage.setText("");
                }
            }
        });
    }


    public void sendMessageToFirebase(String message, int type) {
        /*
        Message obj = new Message(message, FirebaseUserID.getFirebaseUserId(this),
                DateFormat.getDateTimeInstance().format(new Date())
                        ,getGroupID(), FirebaseUserID.getUserName(this),type);*/
        Message obj = new Message(
                message,
                FirebaseUserID.getFirebaseUserId(this),
                DateFormat.getDateTimeInstance().format(new Date()),
                getGroupID(),
                FirebaseUserID.getUserName(this),
                type
        );
        messageList.add(obj);
        FirebaseReference.groupsReference.child(getGroupID()).child("message").push().setValue(obj);
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rvChatFeed.setLayoutManager(llm);
        rvChatFeed.setHasFixedSize(false);

        imageGifSearch.setLayoutManager(new StaggeredGridLayoutManager(3,1));
        imageGifSearch.setHasFixedSize(false);

        listOfMessages = getData();
        RealmController.addToRealm(listOfMessages,this, getGroupID());

        RealmResults<RMessage> results = RealmController.fetchChats(getGroupID());
        chatFeedRecyclerAdapter = new ChatFeedRecyclerAdapter(ChatActivity.this, results,true);
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
        Message msg = new Message(" Piyush is user","piyush6348","12:00","Group 1","Piyush",0);
        Message msg2 = new Message(" Nimit is user","nimitagg95","12:50","Group 2", "Nimit",0);

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
/*
    @Override
    public void onBackPressed() {
        Log.e("onBackPressed: ", "back clicked");
        if(slidingPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED
                || slidingPanelLayout.getPanelState()==SlidingUpPanelLayout.PanelState.EXPANDED
                || slidingPanelLayout.getPanelState()==SlidingUpPanelLayout.PanelState.DRAGGING){
            slidingPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
        else{
            super.onBackPressed();
            finish();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share_group:
                //newGame();
                shareGroup();
                return true;
            case R.id.plan_outing:
                return true;
            case R.id.help:
                //showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareGroup() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,  getGroupID() );
        startActivity(sharingIntent);
    }


    private void getandDisplayGifs() {
        GifyTrendingGifInterface gifyTrending = GifyTrendingGifInterface.retrofit.create(GifyTrendingGifInterface.class);
        Call<GifyNetworkData> call= gifyTrending.getGifyTrendingGif();

        call.enqueue(new Callback<GifyNetworkData>() {
            @Override
            public void onResponse(Call<GifyNetworkData> call, Response<GifyNetworkData> response) {

               // Log.e( "onResponse: ",response.body().getGifyDataList().size()+"");
                ArrayList<String> url = new ArrayList<String>();
                for(int i=0;i<response.body().getGifyDataList().size();i++) {
                    url.add(response.body().getGifyDataList().get(i).getGifyImages().getFixedWidthGif().getGifUrl());
                }
                Log.e("onResponse: ", "url list size" + url.size());
                imageGifSearch.setAdapter(new SearchedImagesRecyclerAdapter(url,ChatActivity.this, MESSAGE_TYPE_GIF));

            }

            @Override
            public void onFailure(Call<GifyNetworkData> call, Throwable t) {

            }
        });
    }

    public void clearUserMessage() {
        userMessage.setText("");
    }
}
