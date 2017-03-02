package com.starter.dinerssecrets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by kjiwu on 2017/3/2.
 */

public class STRecyclerViewAdapter extends RecyclerView.Adapter {

    private boolean mIsScrolling = false;

    public void setScrolling(boolean value) {
        if(value != mIsScrolling) {
            mIsScrolling = value;
            notifyDataSetChanged();
        }
    }

    public boolean getIsScrolling() {
        return mIsScrolling;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
