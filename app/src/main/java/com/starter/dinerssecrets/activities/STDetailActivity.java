package com.starter.dinerssecrets.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.STDetailAdapter;
import com.starter.dinerssecrets.customs.EmptyRecyclerView;
import com.starter.dinerssecrets.databases.STCookbookDetailDBHelper;
import com.starter.dinerssecrets.models.STCookbookDetail;
import com.starter.dinerssecrets.models.STCookbookItem;
import com.starter.dinerssecrets.utilities.resolvers.STCookbookDetailResolver;

/**
 * Created by wulei on 2017/3/1.
 */

public class STDetailActivity extends STBaseActivity {

    private Toolbar mToolbar;
    private TextView mToolBarTitle;
    private EmptyRecyclerView mContentList;
    private ProgressDialog mLoadingDialog;
    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_detail);

        initViews();
        showProgressDialog();
        initToolbar();
        loadDetail();
    }

    private void initViews() {
        mEmptyView = findViewById(R.id.detail_empty_view);
        mContentList = (EmptyRecyclerView) findViewById(R.id.detail_container);
        mContentList.setLayoutManager(new LinearLayoutManager(this));
        mContentList.setEmptyView(mEmptyView);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        mToolBarTitle = (TextView) findViewById(R.id.detail_tv_title);
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

    private void loadDetail() {
        Bundle bundle = getIntent().getExtras();
        if(null != bundle) {
            final STCookbookItem book = (STCookbookItem) bundle.getSerializable("book");
            if(null != book) {
                STCookbookDetailDBHelper dbHelper = new STCookbookDetailDBHelper(this);
                STCookbookDetail detail = dbHelper.getCookbookDetail(book.cooking_id);
                if(null != detail) {
                    mToolbar.setTitle(detail.title);
                    STDetailAdapter adapter = new STDetailAdapter(this, detail);
                    mContentList.setAdapter(adapter);
                }
                else {
                    if(isHaveNetwork()) {
                        STCookbookDetailResolver resolver = new STCookbookDetailResolver(this);
                        resolver.resolveHtml(book.url, new STCookbookDetailResolver.OnCookbookDetailCompletedListener() {
                            @Override
                            public void onCompleted(STCookbookDetail detail) {
                                detail.intro = book.intro;
                                detail.difficulty = book.difficulty;
                                mToolBarTitle.setText(book.name);
                                mLoadingDialog.dismiss();
                                STDetailAdapter adapter = new STDetailAdapter(STDetailActivity.this, detail);
                                mContentList.setAdapter(adapter);
                            }
                        });
                    } else {
                        Toast.makeText(this, R.string.network_error_tips, Toast.LENGTH_SHORT)
                                .show();
                        mLoadingDialog.dismiss();
                    }
                }

            }
        }
    }

    private void showProgressDialog() {
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setIndeterminate(true);
        mLoadingDialog.setTitle(R.string.app_name);
        mLoadingDialog.setMessage(getResources().getString(R.string.detail_loading_tips));
        mLoadingDialog.show();
    }
}
