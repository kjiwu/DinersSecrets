package com.starter.dinerssecrets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.activities.STDetailActivity;
import com.starter.dinerssecrets.adapters.STCookbookCollectionAdapter;
import com.starter.dinerssecrets.adapters.STCookbookItemAdapter;
import com.starter.dinerssecrets.customs.EmptyRecyclerView;
import com.starter.dinerssecrets.databases.STCollectionsDBHelper;
import com.starter.dinerssecrets.managers.AppManager;
import com.starter.dinerssecrets.models.STCookbookItem;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbooksFragment extends STBaseFragment {

    private EmptyRecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_cookbooks, null);
        View emptyView = view.findViewById(R.id.emptyView);

        mRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.cb_recyclerView);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        STCookbookItemAdapter adapter = new STCookbookItemAdapter(inflater.getContext());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STCookbookItem item = (STCookbookItem) v.getTag();
                if(null != item) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("book", item);
                    Intent intent = new Intent(getActivity(), STDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
