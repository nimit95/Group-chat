package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.viewHolders.ChatViewHolder;
import com.codingblocks.groupchat.realm.RealmModels.RMessage;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.codingblocks.groupchat.utils.FirebaseUserID;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatFeedRecyclerAdapter extends RealmRecyclerViewAdapter<RMessage, RecyclerView.ViewHolder> implements CONSTANTS{

    public ChatFeedRecyclerAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<RMessage> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
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

        chatViewHolder.timeStamp.setText(message.getTimeStamp());
        chatViewHolder.chatTextView.setText(message.getMessage());

    }}