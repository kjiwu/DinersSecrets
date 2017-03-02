package com.starter.dinerssecrets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.STMaterialsAdapter;
import com.starter.dinerssecrets.customs.SlideView;
import com.starter.dinerssecrets.databases.STMaterialsDBHelper;

/**
 * Created by wulei on 2017/2/27.
 */

public class STSearchActivity extends STBaseActivity {

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;

    private STMaterialsDBHelper mMaterialsDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_search);

        mMaterialsDBHelper= new STMaterialsDBHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_materials_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        STMaterialsAdapter adapter = new STMaterialsAdapter(this);
        mRecyclerView.setAdapter(adapter);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}