package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.viewHolders.*;
import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.utils.CONSTANTS;

import java.util.List;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatFeedRecyclerAdapter extends RecyclerView.Adapter<ChatViewHolder> implements CONSTANTS{

    List<Message> messageList;

    public ChatFeedRecyclerAdapter(List<Message> messageList){
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);

        if(messageList.get(position).getFirebaseUserID()=="piyush6348")
            return 1;
        return 0;
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
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row_item,parent,false);

        return new ChatViewHolder(null,0);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        //holder.chatMsg.setText(messageList.get(position).getMessage());
        holder.timeStamp.setText(messageList.get(position).getTimestamp());
        holder.chatTextView.setText(messageList.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
