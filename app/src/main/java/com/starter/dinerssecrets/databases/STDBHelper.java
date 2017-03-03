package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.starter.dinerssecrets.managers.AppManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by wulei on 2017/2/27.
 */

public class STDBHelper extends SQLiteOpenHelper {

    private final static String ASSETS_DATABASE = "cookbooks.db";
    public final static String DATABASE_NAME = ASSETS_DATABASE;

    public final static String DETAIL_TABLE_NAME = "ST_COOKBOOK_DETAIL";
    public final static String MATERIAL_TABLE_NAME = "ST_COOKBOOK_MATERIALS";
    public final static String STEPS_TABLE_NAME = "ST_COOKBOOK_STEPS";
    public final static String COOKBOOK_TABLE_NAME = "ST_COOKINGS";
    public final static String COMPLETE_PIC_TABLE_NAME = "ST_COOKBOOK_COMPLETES";
    public final static String MATERIALS_TABLE_NAME = "st_materials";

    public final static String COOKBOOK_COLUMN_ID = "cooking_id";
    public final static String COOKBOOK_COLUMN_NAME = "cooking_name";
    public final static String COOKBOOK_COLUMN_IMAGE_NAME = "cooking_img_name";
    public final static String COOKBOOK_COLUMN_IMAGE = "cooking_image";
    public final static String COOKBOOK_COLUMN_URL = "cooking_url";
    public final static String COOKBOOK_COLUMN_INTRO = "cooking_intro";
    public final static String COOKBOOK_COLUMN_TYPE = "cooking_type";
    public final static String COOKBOOK_COLUMN_DIFFICULTY = "cooking_difficulty";
    public final static String COOKBOOK_COLUMN_MATERIALS = "cooking_materials";
    public final static String COOKBOOK_COLUMN_ISCOLLECTION = "cooking_is_collection";

    public final static String DETAIL_COLUMN_ID = "cooking_id";
    public final static String DETAIL_COLUMN_IMAGE = "cooking_img";
    public final static String DETAIL_COLUMN_TIME = "cooking_time";
    public final static String DETAIL_COLUMN_TIPS = "cooking_tips";


    public final static String MATERIAL_COLUMN_ID = "cooking_id";
    public final static String MATERIAL_COLUMN_NAME = "material_name";
    public final static String MATERIAL_COLUMN_COUNT = "material_count";
    public final static String MATERIAL_COLUMN_TYPE = "material_type";

    public final static String STEP_COLUMN_ID = "cooking_id";
    public final static String STEP_COLUMN_ORDER = "step_order";
    public final static String STEP_COLUMN_NAME = "step_name";
    public final static String STEP_COLUMN_IMG = "step_img";

    public final static String COLLECTION_COLUMN_COOKBOOK_ID = "cooking_id";
    public final static String COLLECTION_COLUMN_ORDER = "collection_order";

    public final static String MATERIALS_COLUMN_ID = "material_id";
    public final static String MATERIALS_COLUMN_TYPE = "material_type";
    public final static String MATERIALS_COLUMN_NAME = "material_name";
    public final static String MATERIALS_COLUMN_IMG = "material_image";

    public final static String CREATE_DETAIL_TABLE = "create table if not exists ST_COOKBOOK_DETAIL (" +
            "[cooking_id] varchar(100) primary key," +
            "[cooking_img] varchar(200)," +
            "[cooking_time] varchar(100)," +
            "[cooking_tips] varchar(200)" +
            ");";

    public final static String CREATE_MATERIALS_TABLE = "create table if not exists ST_COOKBOOK_MATERIALS (" +
            "[id] integer primary key AUTOINCREMENT," +
            "[cooking_id] varchar(100) not null," +
            "[material_name] varchar(100)," +
            "[material_count] varchar(100)," +
            "[material_type] integer" +
            ");";

    public final static String CREATE_STEPS_TABLE = "create table if not exists ST_COOKBOOK_STEPS (" +
            "[id] integer primary key AUTOINCREMENT," +
            "[cooking_id] varchar(100) not null," +
            "[step_order] varchar(10)," +
            "[step_name] varchar(200)," +
            "[step_img] varchar(200)" +
            ");";

    public final static String CREATE_COMPLETE_PIC_TABLE = "create table if not exists ST_COOKBOOK_COMPLETES (" +
            "[id] integer primary key AUTOINCREMENT," +
            "[cooking_id] varchar(100) not null," +
            "[img] varchar(10)" +
            ");";

    public final static String COMPLETE_COLUMN_PIC = "img";

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
        Log.d(AppManager.APP_TAG, "create database.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(AppManager.APP_TAG, "database update, oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        switch (newVersion) {
            case 1:
            case 2:
                db.execSQL(CREATE_DETAIL_TABLE);
                db.execSQL(CREATE_MATERIALS_TABLE);
                db.execSQL(CREATE_STEPS_TABLE);
                db.execSQL(CREATE_COMPLETE_PIC_TABLE);
        }
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
