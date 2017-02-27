package com.starter.dinerssecrets.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;

import com.starter.dinerssecrets.databases.STDBHelper;
import com.starter.dinerssecrets.receivers.NetworkChangedReceiver;

/**
 * Created by wulei on 2017/2/27.
 */

public class STBaseActivity extends AppCompatActivity implements NetworkChangedReceiver.OnNetworkChangedListener {

    private boolean mHaveNetwork;
    private int mNetworkType;

    public boolean isHaveNetwork() {
        return mHaveNetwork;
    }

    public int getNetworkType() {
        return mNetworkType;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onChanged(boolean haveNetwork, int type) {
        mHaveNetwork = haveNetwork;
        mNetworkType = type;
    }

    public void stStartActivity(Intent intent) {
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(STBaseActivity.this).toBundle());
    }
}
