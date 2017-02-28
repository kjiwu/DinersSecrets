package com.starter.dinerssecrets.utilities;

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
}
