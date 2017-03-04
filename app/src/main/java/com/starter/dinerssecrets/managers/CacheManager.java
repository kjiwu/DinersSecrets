package com.starter.dinerssecrets.managers;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by kjiwu on 2017/3/4.
 */

public class CacheManager {

    private final static  String THUMB_FILE_DIR = "/thumbs/";
    private final static  String IMAGE_FILE_DIR = "/images/";

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            //file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static float getCacheFilesSize(Context context, String subPath) {
        String path = context.getFilesDir() + subPath;
        File file = new File(path);
        long blockSize = 0;
        try {
            if(file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小","获取失败!");
        }

        return blockSize / (1024 * 1024f);
    }

    private static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    private static boolean deleteDirectory(String filePath) {
        boolean flag = false;

        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        return dirFile.delete();
    }


    public static String getCacheSize(Context context) {
        float size = 0;
        size += getCacheFilesSize(context, THUMB_FILE_DIR);
        size += getCacheFilesSize(context, IMAGE_FILE_DIR);
        return String.format("%.2fM", size);
    }

    public static void clearCache(Context context) {
        String path = context.getFilesDir() + THUMB_FILE_DIR;
        deleteDirectory(path);
        path = context.getFilesDir() + IMAGE_FILE_DIR;
        deleteDirectory(path);
    }
}
