package com.starter.dinerssecrets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.activities.STDetailActivity;
import com.starter.dinerssecrets.adapters.STCookbookItemAdapter;
import com.starter.dinerssecrets.customs.EmptyRecyclerView;
import com.starter.dinerssecrets.databases.STCookbookDBHelper;
import com.starter.dinerssecrets.models.STCookbookItem;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbooksFragment extends STBaseFragment {

    private EmptyRecyclerView mRecyclerView;
    private STCookbookDBHelper mDBHelper;
    private STCookbookItemAdapter mAdapter;

    private STCookbookItem mSelectedItem;

    public final static int COOKBBOKS_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_cookbooks, null);
        View emptyView = view.findViewById(R.id.emptyView);

        mDBHelper = new STCookbookDBHelper(getContext());

        mRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.cb_recyclerView);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter = new STCookbookItemAdapter(getActivity());
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedItem = (STCookbookItem) v.getTag();
                if(null != mSelectedItem) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("book", mSelectedItem);
                    Intent intent = new Intent(getActivity(), STDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, COOKBBOKS_REQUEST_CODE);
                }
            }
        });
        mAdapter.setData(mDBHelper.getCookBookLists());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COOKBBOKS_REQUEST_CODE) {
            if(null != data) {
                boolean isSaveCollection = data.getBooleanExtra("IsSaveCollection", false);
                if (isSaveCollection != mSelectedItem.isCollection) {
                    mSelectedItem.isCollection = isSaveCollection;
                    mAdapter.notifyDataSetChanged();
                }
            }
            mSelectedItem = null;
        }
    }
}
