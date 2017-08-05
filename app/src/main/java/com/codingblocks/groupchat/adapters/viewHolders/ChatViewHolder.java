package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codingblocks.groupchat.R;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView chatMsg;
    public ChatViewHolder(View itemView,int viewType) {
        super(itemView);
        chatMsg = (TextView) itemView.findViewById(R.id.chat_msg);
    }
}
