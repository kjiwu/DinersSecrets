package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by wulei on 2017/3/1.
 */

public class AppManager {
    public final static String APP_TAG = "x-man";

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
