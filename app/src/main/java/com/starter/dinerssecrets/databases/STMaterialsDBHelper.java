package com.starter.dinerssecrets.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starter.dinerssecrets.models.STMaterialsItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wulei on 2017/3/2.
 */

public class STMaterialsDBHelper extends STDBHelper {
    public STMaterialsDBHelper(Context context) {
        super(context);
    }

    public void insert(STMaterialsItem item) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(MATERIALS_COLUMN_TYPE, item.type);
            values.put(MATERIALS_COLUMN_NAME, item.name);
            values.put(MATERIALS_COLUMN_IMG, item.image);
            db.insert(MATERIALS_TABLE_NAME, null, values);
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

    public Map<String, List<STMaterialsItem>> getMaterials() {
        Map<String, List<STMaterialsItem>> items = null;
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select distinct material_type from st_materials", null, null);
            while (cursor.moveToNext()) {
                String type = cursor.getString(0);
                String sql = "select material_name, material_image from st_materials where material_type='" + type + "';";
                Cursor cursor_material = db.rawQuery(sql, null, null);
                List<STMaterialsItem> materials = new ArrayList<>();
                items = new HashMap<>();
                while (cursor_material.moveToNext()) {
                    STMaterialsItem material = new STMaterialsItem();
                    material.type = type;
                    material.name = cursor_material.getString(cursor_material.getColumnIndex(MATERIALS_COLUMN_NAME));
                    material.image = cursor_material.getString(cursor_material.getColumnIndex(MATERIALS_COLUMN_IMG));
                    materials.add(material);
                }
                items.put(type, materials);
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
