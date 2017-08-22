package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.activities.ChatActivity;
import com.codingblocks.groupchat.adapters.viewHolders.SearchedImagesViewHolder;


import java.util.ArrayList;

/**
 * Created by nimit on 20/8/17.
 */

public class SearchedImagesRecyclerAdapter extends RecyclerView.Adapter<SearchedImagesViewHolder> {

    private ArrayList<String> imageOrGifUrlList;
    private Context context;
    private int type;

    public SearchedImagesRecyclerAdapter(ArrayList<String> imageOrGifUrlList, Context context, int type) {
        this.imageOrGifUrlList = imageOrGifUrlList;
        this.context = context;
        this.type = type;
    }

    @Override
    public SearchedImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_gif_item, parent, false);
        final SearchedImagesViewHolder sivh = new SearchedImagesViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ChatActivity)context).sendMessageToFirebase(imageOrGifUrlList.get(sivh.getAdapterPosition()),type);
            }
        });
        return sivh;
    }

    @Override
    public void onBindViewHolder(SearchedImagesViewHolder holder, int position) {
        String url = imageOrGifUrlList.get(position);
       // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.gifImageView);
        Glide.with(context).load(url).into(holder.gifImageView);
    }

    @Override
    public int getItemCount() {
        return imageOrGifUrlList.size();
    }
}
