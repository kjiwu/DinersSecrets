package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.starter.dinerssecrets.models.CookbookItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbookDBHelper extends STDBHelper {

    private final static String COOKBOOK_TABLE_NAME = "ST_COOKINGS";

    private final static String COOKBOOK_COLUMN_ID = "cooking_id";
    private final static String COOKBOOK_COLUMN_NAME = "name";
    private final static String COOKBOOK_COLUMN_IMAGE = "image";
    private final static String COOKBOOK_COLUMN_URL = "url";
    private final static String COOKBOOK_COLUMN_ = "cooking_id";

    public STCookbookDBHelper(Context context) {
        super(context);
    }

    public STCookbookDBHelper(Context context, int version) {
        super(context, version);
    }

    public List<CookbookItem> getCookBookLists() {
        SQLiteDatabase db = null;
        ArrayList<CookbookItem> items = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.query(COOKBOOK_TABLE_NAME, null, null, null, null, null, null);
            if(null != cursor) {
                items = new ArrayList<>();
                while (cursor.moveToNext()) {
                    CookbookItem book = new CookbookItem();
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
