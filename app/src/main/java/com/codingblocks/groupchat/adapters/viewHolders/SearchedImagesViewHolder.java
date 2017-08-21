package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.codingblocks.groupchat.R;

/**
 * Created by nimit on 20/8/17.
 */

public class SearchedImagesViewHolder extends RecyclerView.ViewHolder {

    public ImageView gifImageView;

    public SearchedImagesViewHolder(View itemView) {
        super(itemView);
        gifImageView = (ImageView) itemView.findViewById(R.id.gif_image);
    }
}
