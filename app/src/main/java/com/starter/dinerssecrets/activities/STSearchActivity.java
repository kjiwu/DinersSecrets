package com.starter.dinerssecrets.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.adapters.STCookbookItemAdapter;
import com.starter.dinerssecrets.adapters.STMaterialsAdapter;
import com.starter.dinerssecrets.customs.EmptyRecyclerView;
import com.starter.dinerssecrets.databases.STCookbookDBHelper;
import com.starter.dinerssecrets.databases.STMaterialsDBHelper;
import com.starter.dinerssecrets.models.STCookbookItem;
import com.starter.dinerssecrets.utilities.InputHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wulei on 2017/2/27.
 */

public class STSearchActivity extends STBaseActivity implements STMaterialsAdapter.OnMaterialSelectedLintener{

    private Toolbar mToolbar;

    private EmptyRecyclerView mMaterialListView;
    private EmptyRecyclerView mSearchResultView;
    private EditText mSearchEditText;

    private View mSearchEmptyView;

    private STCookbookItemAdapter mSearchResultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_search);

        mSearchEmptyView = findViewById(R.id.search_empty_tip);

        mSearchEditText = (EditText) findViewById(R.id.search_edittext);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    InputHelper.closeSoftInput(STSearchActivity.this, mSearchEditText.getWindowToken());
                    hiddenSearchResultList();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSearchEditText.clearFocus();

        mMaterialListView = (EmptyRecyclerView) findViewById(R.id.search_materials_list);
        mMaterialListView.setLayoutManager(new LinearLayoutManager(this));
        STMaterialsAdapter adapter = new STMaterialsAdapter(this);
        adapter.setOnMaterialSelectedLintener(this);
        mMaterialListView.setAdapter(adapter);

        mSearchResultView = (EmptyRecyclerView) findViewById(R.id.search_result_list);
        mSearchResultView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultAdapter = new STCookbookItemAdapter(this);
        mSearchResultAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STCookbookItem item = (STCookbookItem) v.getTag();
                if(null != item) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("book", item);
                    Intent intent = new Intent(STSearchActivity.this, STDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        mSearchResultView.setAdapter(mSearchResultAdapter);

        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_toolbar_search:
                InputHelper.closeSoftInput(this, mSearchEditText.getWindowToken());

                CharSequence sMaterials = mSearchEditText.getText();
                if(0 != sMaterials.length()) {
                    hiddenMaterialList();

                    String[] materials = sMaterials.toString().split(",");
                    searchCookbook(Arrays.asList(materials));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void searchCookbook(List<String> materials) {
        STCookbookDBHelper dbHelper = new STCookbookDBHelper(this);
        mSearchResultAdapter.setData(dbHelper.searchCookbooks(materials));
    }

    private void hiddenMaterialList() {
        mSearchResultView.setVisibility(View.VISIBLE);
        mMaterialListView.setVisibility(View.GONE);
        mSearchResultView.setEmptyView(mSearchEmptyView);
    }

    private void hiddenSearchResultList() {
        mSearchResultView.setVisibility(View.GONE);
        mMaterialListView.setVisibility(View.VISIBLE);
        mSearchEmptyView.setVisibility(View.GONE);
        mSearchResultView.setEmptyView(null);
        mSearchResultAdapter.setData(null);
    }

    @Override
    public void OnSelected(CharSequence name) {
        StringBuilder builder = new StringBuilder();
        CharSequence text = mSearchEditText.getText();
        if(0 != text.length()) {
            builder.append(mSearchEditText.getText());
            builder.append(",");
        }
        builder.append(name);
        String content = builder.toString();
        mSearchEditText.setText(content);
        mSearchEditText.setSelection(content.length());
    }
}