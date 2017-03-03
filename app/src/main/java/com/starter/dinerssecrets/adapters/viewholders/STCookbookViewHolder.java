package com.starter.dinerssecrets.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starter.dinerssecrets.R;

/**
 * Created by wulei on 2017/3/1.
 */

public class STCookbookViewHolder extends RecyclerView.ViewHolder {

    public STCookbookViewHolder(View itemView) {
        super(itemView);
        header = (ImageView) itemView.findViewById(R.id.ci_header);
        titleView = (TextView) itemView.findViewById(R.id.ci_title);
        introView = (TextView) itemView.findViewById(R.id.ci_intro);
        difficultyView = (TextView) itemView.findViewById(R.id.ci_difficulty);
        container = (RelativeLayout) itemView.findViewById(R.id.item_container);
    }

    public ImageView header;
    public TextView titleView;
    public TextView introView;
    public TextView difficultyView;

    public RelativeLayout container;
}
