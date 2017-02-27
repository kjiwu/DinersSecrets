package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wulei on 2017/2/27.
 */

public class STDBHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "";

    public final static int DATABASE_VERSION = 1;

    public STDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public STDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
