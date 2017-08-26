package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingblocks.groupchat.R;

import me.himanshusoni.chatmessageview.ChatMessageView;

/**
 * Created by piyush on 5/8/17.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView chatTextView;
    public TextView timeStamp;
    public TextView senderName;
    public ChatMessageView chatMessage;
    public ImageView imageGIfMessage;

    public ChatViewHolder(View itemView,int viewType) {
        super(itemView);

        chatTextView = (TextView) itemView.findViewById(R.id.text);
        timeStamp = (TextView) itemView.findViewById(R.id.timestamp);
        chatMessage = (ChatMessageView) itemView.findViewById(R.id.chatMessageView);
        senderName = (TextView) itemView.findViewById(R.id.sender_name);
        imageGIfMessage = (ImageView) itemView.findViewById(R.id.image_gif);
    }
}
