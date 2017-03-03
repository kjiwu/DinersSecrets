package com.starter.dinerssecrets.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.STDetailAdapter;
import com.starter.dinerssecrets.customs.EmptyRecyclerView;
import com.starter.dinerssecrets.databases.STCollectionsDBHelper;
import com.starter.dinerssecrets.databases.STCookbookDetailDBHelper;
import com.starter.dinerssecrets.fragments.STCookbooksFragment;
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

    private String cooking_id;
    private boolean mIsSaveCollection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_detail);

        initViews();
        showProgressDialog();
        initToolbar();
        loadDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        if(mIsSaveCollection) {
            menu.getItem(0).setTitle(R.string.detail_toolbar_uncollection_title);
            menu.getItem(0).setIcon(R.mipmap.remove_fav);
        } else {
            menu.getItem(0).setTitle(R.string.detail_toolbar_collection_title);
            menu.getItem(0).setIcon(R.mipmap.add_fav);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_toolbar_collection: {
                STCollectionsDBHelper dbHelper = new STCollectionsDBHelper(STDetailActivity.this);
                mIsSaveCollection = !mIsSaveCollection;
                if(mIsSaveCollection) {
                    dbHelper.insertCollection(cooking_id);
                    item.setTitle(R.string.detail_toolbar_uncollection_title);
                    item.setIcon(R.mipmap.remove_fav);
                } else {
                    dbHelper.deleteCollection(cooking_id);
                    item.setTitle(R.string.detail_toolbar_collection_title);
                    item.setIcon(R.mipmap.add_fav);
                }
                Intent intent = new Intent();
                intent.putExtra("IsSaveCollection", mIsSaveCollection);
                setResult(STCookbooksFragment.COOKBBOKS_REQUEST_CODE, intent);
            }
                break;
        }
        return super.onOptionsItemSelected(item);
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
                cooking_id = book.cooking_id;
                mIsSaveCollection = book.isCollection;
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
