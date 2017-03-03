package com.starter.dinerssecrets.utilities;

import android.content.Context;

/**
 * Created by wulei on 2017/3/3.
 */

public class DipPixelHelper {
    public static int dipToPixels(Context context, int dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int)(valueDips * SCALE + 0.5f);
        return valuePixels;
    }
}
