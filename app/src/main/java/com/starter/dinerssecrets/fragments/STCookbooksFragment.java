package com.starter.dinerssecrets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.STCookbookItemAdapter;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbooksFragment extends STBaseFragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_cookbooks, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cb_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        STCookbookItemAdapter adapter = new STCookbookItemAdapter(inflater.getContext());
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
