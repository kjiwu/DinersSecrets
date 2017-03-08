package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

/**
 * Created by wulei on 2017/3/7.
 */

public class YouMiManager {
    public final static String APPID = "2770ea98cf2883df";
    public final static String APPSECRET = "a07a5eb05ac66883";

    public boolean isTestModel = false;
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

    public void getSplashAD(Context context, Class target,
                            ViewGroup container,
                            SpotListener listener) {
        SplashViewSettings settings = new SplashViewSettings();
        settings.setAutoJumpToTargetWhenShowFailed(true);
        settings.setTargetClass(target);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        settings.setSplashViewContainerAndLayoutParams(container, lp);
        SpotManager.getInstance(context).showSplash(context,
                settings, listener);
    }
}
