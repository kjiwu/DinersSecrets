package com.starter.dinerssecrets.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STDBHelper;
import com.starter.dinerssecrets.managers.YouMiManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class STSplashActivity extends STBaseActivity {

    Observable mObservable = null;
    Disposable mDisposable = null;

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_splash);

        YouMiManager.getInstance().initAD(this);

        mLinearLayout = (LinearLayout) findViewById(R.id.ad_container);


        try {
            STDBHelper.initializeLocalDatabase(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        gotoMainActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(null != mObservable) {
            mObservable.subscribe(new Observer() {
                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable = d;
                }

                @Override
                public void onNext(Object value) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null != mDisposable) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void gotoMainActivity() {
        mObservable = Observable.empty().delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Intent intent = new Intent(STSplashActivity.this, STMainActivity.class);
                        stStartActivity(intent);
                        STSplashActivity.this.finish();
                    }
                });
    }

}