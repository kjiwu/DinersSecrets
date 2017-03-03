package com.starter.dinerssecrets.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starter.dinerssecrets.R;

/**
 * Created by wulei on 2017/3/3.
 */

public class STDetailContentViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout contentList;
    public TextView contentView;

    public STDetailContentViewHolder(View itemView) {
        super(itemView);

        contentView = (TextView) itemView.findViewById(R.id.detail_intro);
        contentList = (LinearLayout) itemView.findViewById(R.id.detail_content_list);
    }

}
