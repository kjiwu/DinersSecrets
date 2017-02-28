package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.starter.dinerssecrets.models.STCookbookDetail;

/**
 * Created by wulei on 2017/2/28.
 */

public class STCookbookDetailDBHelper extends STDBHelper {

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


    public final static String CREATE_DETAIL_TABLE = "create table if not exists ST_COOKBOOK_DETAIL (" +
            "[cooking_id] varchar(100) primary key," +
            "[cooking_img] varchar(200)," +
            "[cooking_time] varchar(100)," +
            "[cooking_tips] varchar(200)" +
            ");";

    public final static String CREATE_MATERIALS_TABLE = "create table if not exists ST_COOKBOOK_MATERIALS (" +
            "[id] integer primary key AUTOINCREMENT," +
            "[cooking_id] varchar(100)," +
            "[material_name] varchar(100)," +
            "[material_count] varchar(100)" +
            "[material_type] integer" +
            ");";

    public final static String CREATE_STEPS_TABLE = "create table if not exists ST_COOKBOOK_STEPS (" +
            "[id] integer primary key AUTOINCREMENT," +
            "[cooking_id] varchar(100)," +
            "[step_order] integer," +
            "[step_name] varchar(200)," +
            "[step_img] varchar(200)," +
            ");";

    public STCookbookDetailDBHelper(Context context) {
        super(context);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > DATABASE_VERSION) {
            db.execSQL(CREATE_DETAIL_TABLE);
            db.execSQL(CREATE_MATERIALS_TABLE);
            db.execSQL(CREATE_STEPS_TABLE);
        }
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public STCookbookDetail getCookbookDetail(String cooking_id) {
        return null;
    }

    public void insertCookbookDetail(STCookbookDetail detail) {

    }
}
