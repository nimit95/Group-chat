package com.codingblocks.groupchat.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codingblocks.groupchat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by piyush on 27/8/17.
 */

public class PlacesViewHolder extends RecyclerView.ViewHolder {
    public TextView placeName,placeVicinity;
    public CircleImageView placeImage;
    public PlacesViewHolder(View itemView) {
        super(itemView);
        placeImage = (CircleImageView) itemView.findViewById(R.id.place_image);
        placeName = (TextView) itemView.findViewById(R.id.place_name);
        placeVicinity = (TextView) itemView.findViewById(R.id.place_vicinity);
    }
}
