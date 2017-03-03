package com.starter.dinerssecrets.adapters.viewholders;

import android.preference.TwoStatePreference;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.customs.TwoColumnsView;

/**
 * Created by wulei on 2017/3/3.
 */

public class STDetailMaterialViewHolder extends RecyclerView.ViewHolder {

    public TwoColumnsView materialsView;

    public STDetailMaterialViewHolder(View itemView) {
        super(itemView);
        materialsView = (TwoColumnsView) itemView.findViewById(R.id.detail_material_container);
    }

}
