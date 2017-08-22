package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.codingblocks.groupchat.R;

/**
 * Created by nimit on 20/8/17.
 */

public class SearchedImagesViewHolder extends RecyclerView.ViewHolder {

    public ImageView gifImageView;
    public LinearLayout linearLayout;
    public ProgressBar progressBar;

    public SearchedImagesViewHolder(View itemView) {
        super(itemView);
        gifImageView = (ImageView) itemView.findViewById(R.id.gif_image);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        //gifImageView.setLayoutParams(new LinearLayout.LayoutParams(200, linearLayout.getWidth()/3));
    }
}
