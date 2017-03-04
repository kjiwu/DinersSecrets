package com.starter.dinerssecrets.utilities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wulei on 2017/2/27.
 */

public class StringHelper {
    public static String getCookingId(String url) {
        String id = null;
        if(null != url) {
            int index = url.lastIndexOf("/");
            if(index != -1) {
                String sub = url.substring(index + 1);
                index = sub.indexOf(".");
                if(index != -1) {
                    return sub.substring(0, index);
                }
            }
        }
        return id;
    }

    public static String getImageName(String url) {
        String imageName = null;
        if(null != url) {
            int index = url.lastIndexOf("/");
            imageName = url.substring(index + 1);
            index = imageName.lastIndexOf(".");
            imageName = imageName.substring(0, index) + ".jpg";
        }
        return imageName;
    }

    public static String formatTips(List<String> tips) {
        if(null == tips || tips.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (String tip : tips) {
            builder.append(tip + "|");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static List<String> reformatTips(String tips) {
        if(null == tips) {
            return null;
        }
        return Arrays.asList(tips.split("|"));
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
