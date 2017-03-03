package com.starter.dinerssecrets.utilities;

import android.content.Context;

/**
 * Created by wulei on 2017/3/3.
 */

public class DipPixelHelper {
    public static int dipToPixels(Context context, float dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int)(valueDips * SCALE + 0.5f);
        return valuePixels;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
