package com.starter.dinerssecrets.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.starter.dinerssecrets.managers.NetwrokManager;

/**
 * Created by wulei on 2017/2/27.
 */

public class NetworkChangedReceiver extends BroadcastReceiver {
    public interface OnNetworkChangedListener {
        void onChanged(boolean haveNetwork, int type);
    }

    private OnNetworkChangedListener mListener;

    public void setNetworkChangedListener(OnNetworkChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NetworkChangedReceiver", "network changed.");
        boolean haveNetwork = NetwrokManager.isNetworkAvailable(context);
        int type = NetwrokManager.currentNetworkType(context);
        if(null != mListener) {
            mListener.onChanged(haveNetwork, type);
        }
    }
}
