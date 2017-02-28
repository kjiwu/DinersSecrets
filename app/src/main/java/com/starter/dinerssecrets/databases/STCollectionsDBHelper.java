package com.starter.dinerssecrets.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starter.dinerssecrets.models.STCookbookItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2017/2/28.
 */

public class STCollectionsDBHelper extends STDBHelper {

    private final static String CREATE_COLLECTIONS_TABLE = "CREATE TABLE IF NOT EXISTS [ST_COLLECTIONS](\n" +
            "[collection_order] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            "[cooking_id] VARCHAR(100) NOT NULL UNIQUE \n" +
            ");";

    public STCollectionsDBHelper(Context context) {
        super(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > DATABASE_VERSION) {
            db.execSQL(CREATE_COLLECTIONS_TABLE);
        }
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public void insertCollection(String cooking_id) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(COLLECTION_COLUMN_COOKBOOK_ID, cooking_id);
            db.insert(COLLECTIONS_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public void deleteCollection(String cooking_id) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            db.delete(COLLECTIONS_TABLE_NAME, COLLECTION_COLUMN_COOKBOOK_ID + "=?", new String[] { cooking_id });
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != db) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public List<STCookbookItem> getCollections() {
        SQLiteDatabase db = null;
        ArrayList<STCookbookItem> list = null;
        try {
            db = getReadableDatabase();
            String sql = "select a.* from " + STCookbookDBHelper.COOKBOOK_TABLE_NAME +
                    " a, " + COLLECTIONS_TABLE_NAME +" b " +
                    "where a.cooking_id=b.cooking_id";
            Cursor cursor = db.rawQuery(sql, null);
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                STCookbookItem item = new STCookbookItem();
                item.cooking_id = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_ID));
                item.name = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_NAME));
                item.image = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_IMAGE));
                item.url = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_URL));
                item.intro = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_INTRO));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != db) {
                db.close();
            }
        }
        return list;
    }
}