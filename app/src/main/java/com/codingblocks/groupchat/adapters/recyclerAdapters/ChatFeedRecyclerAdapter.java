package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.viewHolders.*;
import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.utils.CONSTANTS;
import com.codingblocks.groupchat.utils.FirebaseUserID;

import java.util.List;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatFeedRecyclerAdapter extends RecyclerView.Adapter<ChatViewHolder> implements CONSTANTS{

    List<Message> messageList;
    Context context;
    public ChatFeedRecyclerAdapter(List<Message> messageList, Context context){
        this.messageList = messageList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        String userID = FirebaseUserID.getFirebaseUserId(context);

        if(messageList.get(position).getFirebaseUserID()== userID)
            return OUR_MESSAGE;
        return OTHERS_MESSAGE;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;

        switch (viewType){
            case OUR_MESSAGE:
                view = inflater.inflate(R.layout.item_user_message,viewGroup,false);
                return new ChatViewHolder(view,OUR_MESSAGE);
            case OTHERS_MESSAGE:
                view = inflater.inflate(R.layout.item_susi_message,viewGroup,false);
                return new ChatViewHolder(view,OTHERS_MESSAGE);
        }
        return new ChatViewHolder(null,0);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.timeStamp.setText(messageList.get(position).getTimestamp());
        holder.chatTextView.setText(messageList.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
