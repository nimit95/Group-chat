package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.viewHolders.ChatViewHolder;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.codingblocks.groupchat.utils.FirebaseUserID;

import io.realm.OrderedRealmCollection;
import io.realm.RealmChangeListener;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatFeedRecyclerAdapter extends RealmRecyclerViewAdapter<RMessage, RecyclerView.ViewHolder> implements CONSTANTS{

    private int lastMsgCount;
    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public ChatFeedRecyclerAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<RMessage> data, boolean autoUpdate) {
        super(context, data, autoUpdate);

        lastMsgCount = getItemCount();
        RealmChangeListener<RealmResults> listener = new RealmChangeListener<RealmResults>() {
            @Override
            public void onChange(RealmResults elements) {
                //only scroll if new is added.
                if (lastMsgCount < getItemCount()) {
                    scrollToBottom();
                }
                lastMsgCount = getItemCount();
            }
        };
        if (data instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) data;
            realmResults.addChangeListener(listener);
        }
    }

    private void scrollToBottom() {
        if (getData() != null && !getData().isEmpty() && recyclerView != null) {
            recyclerView.smoothScrollToPosition(getItemCount() - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String userID = FirebaseUserID.getFirebaseUserId(context);

        RMessage rMessage = getData().get(position);

        if(rMessage.getFirebaseUserID().compareTo( userID)==0)
            return OUR_MESSAGE;
        return OTHERS_MESSAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;

        switch (viewType){
            case OUR_MESSAGE:
                view = inflater.inflate(R.layout.item_user_message,viewGroup,false);
                return new ChatViewHolder(view,OUR_MESSAGE);
            case OTHERS_MESSAGE:
                view = inflater.inflate(R.layout.item_others_message,viewGroup,false);
                return new ChatViewHolder(view,OTHERS_MESSAGE);
        }
        return new ChatViewHolder(null,0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatViewHolder chatViewHolder = null;
        if (holder instanceof ChatViewHolder)
            chatViewHolder = (ChatViewHolder) holder;

        RMessage message = getData().get(position);

        Log.e("onBindViewHolder: ", message.getTimeStamp());
        chatViewHolder.timeStamp.setText(message.getTimeStamp().split(" ")[3].substring(0,5));
        if (message.getMessageType()==CONSTANTS.MESSAGE_TYPE_TEXT)
            chatViewHolder.chatTextView.setText(message.getMessage());
        else if (message.getMessageType()==CONSTANTS.MESSAGE_TYPE_GIF) {
            chatViewHolder.chatTextView.setVisibility(View.GONE);
            chatViewHolder.imageGIfMessage.setVisibility(View.VISIBLE);
            Glide.with(context).load(message.getMessage()).into(chatViewHolder.imageGIfMessage);
        }

    }}