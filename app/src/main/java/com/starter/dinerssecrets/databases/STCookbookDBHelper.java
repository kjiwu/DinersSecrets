package com.starter.dinerssecrets.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starter.dinerssecrets.models.STCookbookItem;

import java.util.ArrayList;
import java.util.List;

import static com.starter.dinerssecrets.databases.STCollectionsDBHelper.IS_COLLECTION_VALUE;

/**
 * Created by wulei on 2017/2/27.
 */

public class STCookbookDBHelper extends STDBHelper {

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
            if (null != cursor) {
                items = new ArrayList<>();
                while (cursor.moveToNext()) {
                    STCookbookItem book = new STCookbookItem();
                    book.cooking_id = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_ID));
                    book.name = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_NAME));
                    book.image = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_IMAGE));
                    book.imageName = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_IMAGE_NAME));
                    book.url = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_URL));
                    book.difficulty = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_DIFFICULTY));
                    book.intro = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_INTRO));
                    book.isCollection = IS_COLLECTION_VALUE == cursor.getInt(
                            cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_ISCOLLECTION));
                    items.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                db.close();
            }
        }

        return items;
    }

    public List<STCookbookItem> searchCookbooks(List<String> materials) {
        SQLiteDatabase db = null;
        ArrayList<STCookbookItem> items = null;
        try {
            if (materials.size() > 0) {
                db = getReadableDatabase();
                StringBuilder builder = new StringBuilder();
                builder.append("select * from " + COOKBOOK_TABLE_NAME);

                builder.append(" where ");
                for (int i = 0; i < materials.size(); i++) {
                    builder.append("(cooking_materials like '%" + materials.get(i) + "%')");
                    if (i != materials.size() - 1) {
                        builder.append(" and ");
                    }
                }


                Cursor cursor = db.rawQuery(builder.toString(), null, null);
                if (null != cursor) {
                    items = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        STCookbookItem book = new STCookbookItem();
                        book.cooking_id = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_ID));
                        book.name = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_NAME));
                        book.image = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_IMAGE));
                        book.imageName = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_IMAGE_NAME));
                        book.url = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_URL));
                        book.difficulty = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_DIFFICULTY));
                        book.intro = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_INTRO));
                        book.isCollection = IS_COLLECTION_VALUE == cursor.getInt(
                                cursor.getColumnIndex(STCookbookDBHelper.COOKBOOK_COLUMN_ISCOLLECTION));
                        items.add(book);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                db.close();
            }
        }

        return items;
    }
}
