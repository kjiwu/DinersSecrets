package com.starter.dinerssecrets.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.starter.dinerssecrets.managers.AppManager;
import com.starter.dinerssecrets.models.STCookbookItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2017/2/28.
 */

public class STCollectionsDBHelper extends STDBHelper {

    public final static int IS_COLLECTION_VALUE = 1;
    public final static int IS_NOT_COLLECTION_VALUE = 0;

    public STCollectionsDBHelper(Context context) {
        super(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(AppManager.APP_TAG, "database update, oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public boolean haveThisCollection(String cooking_id) {
        SQLiteDatabase db = null;
        boolean result = false;
        try{
            db = getReadableDatabase();
            Cursor cursor = db.query(COOKBOOK_TABLE_NAME, new String[] { COOKBOOK_COLUMN_ISCOLLECTION }, "cooking_id=?",
                    new String[] {cooking_id}, null, null, null);
            result = cursor.getInt(0) == 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != db) {
                db.close();
            }
        }
        return result;
    }

    public void insertCollection(String cooking_id) {
        if(haveThisCollection(cooking_id)) {
            return;
        }

        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(COOKBOOK_COLUMN_ISCOLLECTION, IS_COLLECTION_VALUE);
            db.update(COOKBOOK_TABLE_NAME, values, "cooking_id=?", new String[] { cooking_id } );
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
            values.put(COOKBOOK_COLUMN_ISCOLLECTION, IS_NOT_COLLECTION_VALUE);
            db.update(COOKBOOK_TABLE_NAME, values, "cooking_id=?", new String[] { cooking_id } );
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
            String sql = "select * from " + STCookbookDBHelper.COOKBOOK_TABLE_NAME +
                    " where cooking_is_collection=" + IS_COLLECTION_VALUE;
            Cursor cursor = db.rawQuery(sql, null);
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                STCookbookItem item = new STCookbookItem();
                item.cooking_id = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_ID));
                item.name = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_NAME));
                item.image = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_IMAGE));
                item.imageName = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_IMAGE_NAME));
                item.url = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_URL));
                item.intro = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_INTRO));
                item.difficulty = cursor.getString(cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_DIFFICULTY));
                item.isCollection =
                        IS_COLLECTION_VALUE == cursor.getInt(
                                cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_ISCOLLECTION));
                list.add(item);
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