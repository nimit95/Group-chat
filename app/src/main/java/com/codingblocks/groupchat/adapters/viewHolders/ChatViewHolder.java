package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codingblocks.groupchat.R;

import me.himanshusoni.chatmessageview.ChatMessageView;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {
    //public TextView chatMsg;

    //@BindView(R.id.text)
    public TextView chatTextView;
    //@BindView(R.id.timestamp)
    public TextView timeStamp;
    //@BindView(R.id.chatMessageView)
    public ChatMessageView chatMessage;

    public ChatViewHolder(View itemView,int viewType) {
        super(itemView);
        //chatMsg = (TextView) itemView.findViewById(R.id.chat_msg);

        chatTextView = (TextView) itemView.findViewById(R.id.text);
        timeStamp = (TextView) itemView.findViewById(R.id.timestamp);
        chatMessage = (ChatMessageView) itemView.findViewById(R.id.chatMessageView);
    }
}
