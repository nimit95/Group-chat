package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.activities.ChatActivity;
import com.codingblocks.groupchat.adapters.viewHolders.GroupViewHolder;
import com.codingblocks.groupchat.model.Group;

import java.util.List;

/**
 * Created by piyush and nimit on 5/8/17.
 */

public class GroupFeedRecyclerAdapter extends RecyclerView.Adapter<GroupViewHolder>{
    private List<Group> groupList;
    public Context context;
    public GroupFeedRecyclerAdapter(Context context, List<Group> groupList) {
        this.groupList = groupList;
        this.context = context;
        Log.e("list size", groupList.size() + "");
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // which is the better way for recycler view click listner, here or there
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_layout,parent,false);
        final GroupViewHolder holder = new GroupViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatActivity.class).
                        putExtra("group_id", groupList.get(holder.getAdapterPosition()).getGroupID()));

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.groupName.setText(groupList.get(position).getGroupName());

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
