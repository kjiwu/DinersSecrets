package com.starter.dinerssecrets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STMaterialsDBHelper;
import com.starter.dinerssecrets.models.STMaterialEntity;
import com.starter.dinerssecrets.models.STMaterialsItem;

/**
 * Created by wulei on 2017/3/2.
 */

public class STMaterialsAdapter extends SectionedRecyclerViewAdapter<STMaterialHeaderViewHolder,
        STMaterialContentViewHolder,
        STMaterialFooterViewHolder> {

    private STMaterialEntity entity;
    private Context mContext;

    private STMaterialsDBHelper mDBHelper;


    public STMaterialsAdapter(Context context) {
        mContext = context;
        mDBHelper = new STMaterialsDBHelper(context);
        entity = mDBHelper.getMaterials();
    }

    @Override
    protected int getSectionCount() {
        return (null == entity || null == entity.allMaterials) ? 0 : entity.allMaterials.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        if(null != entity && null != entity.allMaterials) {
            STMaterialEntity.MaterialGroup group = entity.allMaterials.get(section);
            if(null != group.materials) {
                return 1;
            }
        }

        return 0;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected STMaterialHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.search_list_header, parent, false);
        STMaterialHeaderViewHolder holder = new STMaterialHeaderViewHolder(headerView);
        return holder;
    }

    @Override
    protected STMaterialFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected STMaterialContentViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.search_list_content, parent, false);
        STMaterialContentViewHolder holder = new STMaterialContentViewHolder(contentView);
        return holder;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(STMaterialHeaderViewHolder holder, int section) {
        if(null != entity && null != entity.allMaterials) {
            STMaterialEntity.MaterialGroup group = entity.allMaterials.get(section);
            holder.header.setText(group.type);
        }
    }

    @Override
    protected void onBindSectionFooterViewHolder(STMaterialFooterViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(STMaterialContentViewHolder holder, int section, int position) {
        if(null != entity && null != entity.allMaterials) {
            STMaterialEntity.MaterialGroup group = entity.allMaterials.get(section);
            if(null != group.materials) {
                for(STMaterialsItem material : group.materials) {
                    TextView tv = new TextView(mContext);
                    tv.setText(material.name);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.content.addView(tv);
                }
            }
        }
    }
}
