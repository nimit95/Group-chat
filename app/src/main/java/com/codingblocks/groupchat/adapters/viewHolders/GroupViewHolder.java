package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingblocks.groupchat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by piyush on 5/8/17.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {
    public TextView groupName;
    public CircleImageView groupImg;

    public GroupViewHolder(View itemView) {
        super(itemView);
        groupName = (TextView) itemView.findViewById(R.id.group_name);
        groupImg = (CircleImageView) itemView.findViewById(R.id.group_image);
    }
}
