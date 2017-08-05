package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codingblocks.groupchat.R;

/**
 * Created by piyush on 5/8/17.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {
    public TextView groupName;
    public GroupViewHolder(View itemView) {
        super(itemView);
        groupName = (TextView) itemView.findViewById(R.id.group_name);
    }
}
