package com.starter.dinerssecrets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.starter.dinerssecrets.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_detail);
        initToolbar();
        loadDetail();
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
                }
                else {
                    STCookbookDetailResolver resolver = new STCookbookDetailResolver(this);
                    resolver.resolveHtml(book.url, new STCookbookDetailResolver.OnCookbookDetailCompletedListener() {
                        @Override
                        public void onCompleted(STCookbookDetail detail) {
                            mToolBarTitle.setText(book.name);
                        }
                    });
                }

            }
        }
    }
}
