package com.starter.dinerssecrets.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.starter.dinerssecrets.R;

/**
 * Created by wulei on 2017/3/3.
 */

public class STDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView headerTitle;
    public ImageView headerImage;

    public FrameLayout container;

    public STDetailHeaderViewHolder(View itemView) {
        super(itemView);

        headerImage = (ImageView) itemView.findViewById(R.id.detail_common_image);
        headerTitle = (TextView) itemView.findViewById(R.id.detail_common_title);
        container = (FrameLayout) itemView.findViewById(R.id.detail_header_container);
    }

}
