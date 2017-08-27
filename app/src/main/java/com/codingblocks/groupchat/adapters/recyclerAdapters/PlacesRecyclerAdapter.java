package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.viewHolders.PlacesViewHolder;
import com.codingblocks.groupchat.places.Result;

import java.util.List;

/**
 * Created by piyush on 27/8/17.
 */

public class PlacesRecyclerAdapter extends RecyclerView.Adapter<PlacesViewHolder> {
    private List<Result> resultList;
    private Context context;
    public PlacesRecyclerAdapter(List<Result> resultList,Context context){
        this.resultList = resultList;
        this.context = context;
    }

    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_layout,parent,false);
        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlacesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
