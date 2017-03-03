package com.starter.dinerssecrets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.activities.STDetailActivity;
import com.starter.dinerssecrets.adapters.STCookbookCollectionAdapter;
import com.starter.dinerssecrets.customs.EmptyRecyclerView;
import com.starter.dinerssecrets.databases.STCollectionsDBHelper;
import com.starter.dinerssecrets.models.STCookbookItem;

import static com.starter.dinerssecrets.fragments.STCookbooksFragment.COOKBBOKS_REQUEST_CODE;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCollectionsFragment extends STBaseFragment {

    private EmptyRecyclerView mRecyclerView;
    private STCookbookItem mSelectedItem;
    private int mSelectedPosition;
    private STCookbookCollectionAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_collections, null);
        View emptyView = view.findViewById(R.id.emptyView);

        mRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.cb_recyclerView);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mAdapter = new STCookbookCollectionAdapter(inflater.getContext());
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STCookbookCollectionAdapter.TagHolder tagHolder = (STCookbookCollectionAdapter.TagHolder) v.getTag();
                mSelectedItem = tagHolder.item;
                mSelectedPosition = tagHolder.position;
                if(null != mSelectedItem) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("book", mSelectedItem);
                    Intent intent = new Intent(getActivity(), STDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, COOKBBOKS_REQUEST_CODE);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COOKBBOKS_REQUEST_CODE) {
            if(null != data) {
                boolean isSaveCollection = data.getBooleanExtra("IsSaveCollection", false);
                if (!isSaveCollection) {
                    mAdapter.removeItem(mSelectedPosition);
                    STCollectionsDBHelper dbHelper = new STCollectionsDBHelper(getActivity());
                    dbHelper.deleteCollection(mSelectedItem.cooking_id);
                }
            }
            mSelectedItem = null;
            mSelectedPosition = -1;
        }
    }
}
