package com.starter.dinerssecrets.activities;


import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STCookbookDBHelper;
import com.starter.dinerssecrets.databases.STDBHelper;
import com.starter.dinerssecrets.managers.ImageDownloadManager;
import com.starter.dinerssecrets.managers.NetwrokManager;
import com.starter.dinerssecrets.models.CookbookItem;

import java.util.List;

public class STSplashActivity extends STBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_splash);
    }
}