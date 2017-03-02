package com.starter.dinerssecrets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.starter.dinerssecrets.R;

import org.w3c.dom.Text;

/**
 * Created by wulei on 2017/3/2.
 */

public class STMaterialHeaderViewHolder extends RecyclerView.ViewHolder {

    public STMaterialHeaderViewHolder(View itemView) {
        super(itemView);
        header = (TextView) itemView.findViewById(R.id.search_list_tv_header);
    }

    public TextView header;

}
