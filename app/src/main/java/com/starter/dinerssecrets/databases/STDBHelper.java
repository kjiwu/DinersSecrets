package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by wulei on 2017/2/27.
 */

public class STDBHelper extends SQLiteOpenHelper {

    private final static String ASSETS_DATABASE = "cookbooks.db";
    public final static String DATABASE_NAME = "cookbooks.db";

    public final static String DETAIL_TABLE_NAME = "ST_COOKBOOK_DETAIL";
    public final static String MATERIAL_TABLE_NAME = "ST_COOKBOOK_MATERIALS";
    public final static String STEPS_TABLE_NAME = "ST_COOKBOOK_STEPS";
    public final static String COOKBOOK_TABLE_NAME = "ST_COOKINGS";
    public final static String COLLECTIONS_TABLE_NAME = "ST_COLLECTIONS";




    public final static int DATABASE_VERSION = 2;

    private Context mContext;

    public STDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public STDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 初始化本地数据库到手机文件存储,以便以后SQLiteOpenHelper使用
     * @throws Exception
     */
    public static void initializeLocalDatabase(Context context) throws Exception {
        String path = "/data/data/" + context.getPackageName() + "/databases";
        File file = new File(path);
        if(!file.exists()) {
            file.mkdir();
        }
        path += "/" + DATABASE_NAME;
        File dbFile = new File(path);
        if(!dbFile.exists()) {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open(ASSETS_DATABASE);
            FileOutputStream out = new FileOutputStream(new File(path));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) > 0) {
                out.write(buffer);
            }
            out.flush();
            out.close();
            in.close();
        }
    }
}
