package com.starter.dinerssecrets.utilities;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by wulei on 2017/3/2.
 */

public class InputHelper {

    public  static void closeSoftInput(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }
}
