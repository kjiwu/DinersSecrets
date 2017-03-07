package com.starter.dinerssecrets.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.starter.dinerssecrets.R;
import com.starter.dinerssecrets.databases.STDBHelper;
import com.starter.dinerssecrets.managers.YouMiManager;

import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class STSplashActivity extends STBaseActivity {

    private final static int DELAY_TIME = 3;


    Observable mObservable = null;
    Disposable mDisposable = null;

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_splash);

        YouMiManager.getInstance().initAD(this);

        mLinearLayout = (LinearLayout) findViewById(R.id.ad_container);
        YouMiManager.getInstance().getSplashAD(this, STMainActivity.class,
                mLinearLayout, new SpotListener() {
                    @Override
                    public void onShowSuccess() {

                    }

                    @Override
                    public void onShowFailed(int i) {

                    }

                    @Override
                    public void onSpotClosed() {

                    }

                    @Override
                    public void onSpotClicked(boolean b) {

                    }
                });


        try {
            STDBHelper.initializeLocalDatabase(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //gotoMainActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SpotManager.getInstance(this).onPause();
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
        SpotManager.getInstance(this).onStop();
        if(null != mDisposable) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onDestroy();
    }

    private void gotoMainActivity() {
        mObservable = Observable.empty().delay(DELAY_TIME, TimeUnit.SECONDS)
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