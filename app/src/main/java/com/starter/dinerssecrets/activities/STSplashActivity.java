package com.starter.dinerssecrets.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STDBHelper;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

public class STSplashActivity extends STBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_splash);

        try {
            STDBHelper.initializeLocalDatabase(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Observable.empty().delay(4, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Intent intent = new Intent(STSplashActivity.this, STMainActivity.class);
                        stStartActivity(intent);
                        STSplashActivity.this.finish();
                    }
                })
        .subscribe();
    }

}