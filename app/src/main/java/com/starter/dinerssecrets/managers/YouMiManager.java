package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.view.View;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

/**
 * Created by wulei on 2017/3/7.
 */

public class YouMiManager {
    public final static String APPID = "2770ea98cf2883df";
    public final static String APPSECRET = "a07a5eb05ac66883";

    public boolean isTestModel = true;
    public boolean isEnableYoumiLog = true;

    private boolean mIsInit = false;

    private YouMiManager() {

    }

    private static class YouMiManagerHolder {
        static YouMiManager instance = new YouMiManager();
    }

    public static YouMiManager getInstance() {
        return YouMiManagerHolder.instance;
    }

    public View getBarAD(Context context, BannerViewListener listener) {
        View bannerView = BannerManager.getInstance(context)
                .getBannerView(context, listener);
        return bannerView;
    }


    public void initAD(Context context) {
        if(!mIsInit) {
            AdManager.getInstance(context)
                    .init(APPID, APPSECRET, isTestModel, isEnableYoumiLog);
            mIsInit = true;
        }
    }
}