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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
                Gson gson = new Gson();
                String obj = gson.toJson(groupList.get(holder.getAdapterPosition()));
                context.startActivity(new Intent(context, ChatActivity.class)
                        .putExtra("group_id", groupList.get(holder.getAdapterPosition()).getGroupID())
                        .putExtra("group", obj)
                );

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        String groupName = groupList.get(position).getGroupName();
        groupName = Character.toUpperCase(groupName.charAt(0)) + groupName.substring(1);
        holder.groupName.setText(groupName);
        //holder.groupImg
        Picasso.with(context).load("http://via.placeholder.com/45x45").into(holder.groupImg);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
