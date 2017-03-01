package com.starter.dinerssecrets.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TabHost;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.fragments.STCollectionsFragment;
import com.starter.dinerssecrets.fragments.STCookbooksFragment;
import com.starter.dinerssecrets.fragments.STMeFragment;
import com.starter.dinerssecrets.managers.AppManager;

/**
 * Created by wulei on 2017/2/27.
 */

public class STMainActivity extends STBaseActivity {

    private FrameLayout mRealContainer;
    private Toolbar mToolbar;
    private FragmentTabHost mTabHost;

    private Class[] fragments = {
            STCookbooksFragment.class,
            STCollectionsFragment.class,
            STMeFragment.class
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_main);

        initializeTasbHost();
        mRealContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_toolbar_search:
                Log.d(AppManager.APP_TAG, "我要搜索了");
                Intent intent = new Intent(STMainActivity.this, STSearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化TabHost组件
     */
    private void initializeTasbHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fragment_container);

        String[] tabNames = getResources().getStringArray(R.array.tabs);
        for (int i = 0; i < tabNames.length; i++) {
            String name = tabNames[i];
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(name).setIndicator(name);
            mTabHost.addTab(tabSpec, fragments[i], null);
        }
    }
}
