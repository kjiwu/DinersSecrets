package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.view.ViewGroup;

import com.starter.dinerssecrets.activities.STMainActivity;

import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

/**
 * Created by kjiwu on 2017/3/4.
 */

public class YouMiADManager {
    public static void splashADView(Context context, ViewGroup container) {
        SplashViewSettings settings = new SplashViewSettings();
        settings.setAutoJumpToTargetWhenShowFailed(true);
        settings.setTargetClass(STMainActivity.class);
        settings.setSplashViewContainer(container);
        SpotManager.getInstance(context).showSplash(context, settings, new SpotListener() {
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
    }

    public static void insertADView(Context context) {
        SpotManager manager = SpotManager.getInstance(context);
        manager.setImageType(SpotManager.IMAGE_TYPE_HORIZONTAL);
        manager.setAnimationType(SpotManager.ANIMATION_TYPE_SIMPLE);
        manager.showSpot(context, new SpotListener() {
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
    }
}
