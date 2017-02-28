package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starter.dinerssecrets.models.STCookbookItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbookDBHelper extends STDBHelper {


    public final static String COOKBOOK_COLUMN_ID = "cooking_id";
    public final static String COOKBOOK_COLUMN_NAME = "cooking_name";
    public final static String COOKBOOK_COLUMN_IMAGE_NAME = "cooking_image_name";
    public final static String COOKBOOK_COLUMN_IMAGE = "cooking_image";
    public final static String COOKBOOK_COLUMN_URL = "cooking_url";
    public final static String COOKBOOK_COLUMN_INTRO = "cooking_intro";
    public final static String COOKBOOK_COLUMN_TYPE = "cooking_type";
    public final static String COOKBOOK_COLUMN_DIFFICULTY = "cooking_difficulty";
    public final static String COOKBOOK_COLUMN_MATERIALS = "cooking_materials";

    public STCookbookDBHelper(Context context) {
        super(context);
    }

    public STCookbookDBHelper(Context context, int version) {
        super(context, version);
    }

    public List<STCookbookItem> getCookBookLists() {
        SQLiteDatabase db = null;
        ArrayList<STCookbookItem> items = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.query(COOKBOOK_TABLE_NAME, null, null, null, null, null, null);
            if(null != cursor) {
                items = new ArrayList<>();
                while (cursor.moveToNext()) {
                    STCookbookItem book = new STCookbookItem();
                    book.cooking_id = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_ID));
                    book.name = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_NAME));
                    book.image = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_IMAGE));
                    book.url = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_URL));
                    items.add(book);
                }
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

        return items;
    }
}
