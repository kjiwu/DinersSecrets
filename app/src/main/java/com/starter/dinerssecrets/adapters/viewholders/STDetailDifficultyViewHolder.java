package com.starter.dinerssecrets.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.starter.dinerssecrets.R;

import org.w3c.dom.Text;

/**
 * Created by wulei on 2017/3/3.
 */

public class STDetailDifficultyViewHolder extends RecyclerView.ViewHolder {

    public TextView difficultyView;
    public TextView timeView;

    public STDetailDifficultyViewHolder(View itemView) {
        super(itemView);
        difficultyView = (TextView) itemView.findViewById(R.id.detail_diff_diff);
        timeView = (TextView) itemView.findViewById(R.id.detail_diff_time);
    }

}
