package com.starter.dinerssecrets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.customs.WordWrapView;

/**
 * Created by wulei on 2017/3/2.
 */

public class STMaterialContentViewHolder extends RecyclerView.ViewHolder {

    public STMaterialContentViewHolder(View itemView) {
        super(itemView);
        content = (WordWrapView) itemView.findViewById(R.id.search_list_content);
    }

    public WordWrapView content;
}
