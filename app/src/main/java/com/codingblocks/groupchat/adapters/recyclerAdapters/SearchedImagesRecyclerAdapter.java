package com.codingblocks.groupchat.adapters.recyclerAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.activities.ChatActivity;
import com.codingblocks.groupchat.adapters.viewHolders.SearchedImagesViewHolder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


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
                ((ChatActivity) context).slidingPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                ((ChatActivity) context).sendMessageToFirebase(imageOrGifUrlList.get(sivh.getAdapterPosition()), type);

            }
        });
        return sivh;
    }

    @Override
    public void onBindViewHolder(final SearchedImagesViewHolder holder, int position) {
        String url = imageOrGifUrlList.get(position);
        // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(holder.gifImageView);
        Log.e("onBindViewHolder: ", url);
        // holder.progressBar
        // holder.gifImageView.setHe(holder.linearLayout.getWidth());

        //holder.gifImageView.getLayoutParams().height = holder.linearLayout.getWidth()/3;
        // holder.gifImageView.requestLayout();



        Glide.with(context).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }


                })
                .into(holder.gifImageView);
    }

    @Override
    public int getItemCount() {
        return imageOrGifUrlList.size();
    }
}
