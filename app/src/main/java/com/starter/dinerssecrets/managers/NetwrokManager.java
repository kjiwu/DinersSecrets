package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by wulei on 2017/2/27.
 */

public class NetwrokManager {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static int currentNetworkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(null != networkInfo && networkInfo.isConnected()) {
            return networkInfo.getType();
        }

        return -1;
    }

    public static boolean isMobileNetwork(Context context) {
        int type = currentNetworkType(context);
        return type == ConnectivityManager.TYPE_MOBILE;
    }
}
