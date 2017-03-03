package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.models.STCookbookMaterial;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by wulei on 2017/3/3.
 */

public class STDetailMaterialsAdapter extends RecyclerView.Adapter {

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

            nameView = (TextView) itemView.findViewById(R.id.detail_material_name);
            countView = (TextView) itemView.findViewById(R.id.detail_material_count);
        }

        public TextView nameView;
        public TextView countView;
    }

    private Context mContext;
    private List<STCookbookMaterial> mMaterials;

    public STDetailMaterialsAdapter(Context context, List<STCookbookMaterial> data) {
        mContext = context;
        mMaterials = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_material_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder) holder;
        STCookbookMaterial material = mMaterials.get(position);
        vHolder.nameView.setText(material.name);
        vHolder.countView.setText(material.count);
    }

    @Override
    public int getItemCount() {
        return (null != mMaterials) ? mMaterials.size() : 0;
    }
}
