package com.starter.dinerssecrets.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starter.dinerssecrets.models.STCookbookDetail;
import com.starter.dinerssecrets.models.STCookbookMaterial;
import com.starter.dinerssecrets.models.STCookbookStep;
import com.starter.dinerssecrets.utilities.StringHelper;

import java.util.ArrayList;

/**
 * Created by wulei on 2017/2/28.
 */

public class STCookbookDetailDBHelper extends STDBHelper {

    public STCookbookDetailDBHelper(Context context) {
        super(context);
    }

    private boolean haveThisDetail(String cooking_id) {
        SQLiteDatabase db = null;
        boolean result= false;

        try {
            db = getReadableDatabase();
            Cursor cursor = db.query(DETAIL_TABLE_NAME, new String[] { "cooking_id" },
                    "cooking_id=?", new String[] { cooking_id } , null, null, null);
            result = cursor.getCount() > 0;
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

    public STCookbookDetail getCookbookDetail(String cooking_id) {
        STCookbookDetail detail = null;
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();

            String sql = "select a.cooking_name, a.cooking_intro, a.cooking_difficulty, b.* " +
                    "from st_cookings a, st_cookbook_detail b " +
                    "where b.cooking_id=a.cooking_id and b.cooking_id='"+ cooking_id +"'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                detail.cooking_id = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_ID));
                detail.difficulty = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_DIFFICULTY));
                detail.intro = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_INTRO));
                detail.title = cursor.getString(cursor.getColumnIndex(COOKBOOK_COLUMN_NAME));
                detail.time = cursor.getString(cursor.getColumnIndex(DETAIL_COLUMN_TIME));
                detail.image = cursor.getString(cursor.getColumnIndex(DETAIL_COLUMN_IMAGE));
                detail.tips = StringHelper.reformatTips(cursor.getString(cursor.getColumnIndex(DETAIL_COLUMN_TIPS)));
            }

            sql = "select * from ST_COOKBOOK_MATERIALS where cooking_id='" + cooking_id + "'";
            cursor = db.rawQuery(sql, null);
            if(cursor.getCount() > 0) {
                detail.materials = new ArrayList<>();
                while (cursor.moveToNext()) {
                    STCookbookMaterial material = new STCookbookMaterial();
                    material.name = cursor.getString(cursor.getColumnIndex(MATERIAL_COLUMN_NAME));
                    material.count = cursor.getString(cursor.getColumnIndex(MATERIAL_COLUMN_COUNT));
                    material.type = cursor.getInt(cursor.getColumnIndex(MATERIAL_COLUMN_TYPE));
                    detail.materials.add(material);
                }
            }

            sql = "select * from ST_COOKBOOK_STEPS where cooking_id='" + cooking_id + "' order by step_order";
            cursor = db.rawQuery(sql, null);
            if(cursor.getCount() > 0) {
                detail.steps = new ArrayList<>();
                while (cursor.moveToNext()) {
                    STCookbookStep step = new STCookbookStep();
                    step.order = cursor.getString(cursor.getColumnIndex(STEP_COLUMN_ORDER));
                    step.image = cursor.getString(cursor.getColumnIndex(STEP_COLUMN_IMG));
                    step.step = cursor.getString(cursor.getColumnIndex(STEP_COLUMN_NAME));
                    detail.steps.add(step);
                }
            }

            sql = "select * from ST_COOKBOOK_COMPLETES where cooking_id='" + cooking_id + "'";
            cursor = db.rawQuery(sql, null);
            if(cursor.getCount() > 0) {
                detail.complete_pic = new ArrayList<>();
                while (cursor.moveToNext()) {
                    detail.complete_pic.add(cursor.getString(cursor.getColumnIndex(COMPLETE_COLUMN_PIC)));
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

        return detail;
    }

    public void insertCookbookDetail(STCookbookDetail detail) {
        if(null == detail) {
            return;
        }

        if(haveThisDetail(detail.cooking_id)) {
            return;
        }

        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(DETAIL_COLUMN_ID, detail.cooking_id);
            values.put(DETAIL_COLUMN_IMAGE, detail.image);
            values.put(DETAIL_COLUMN_TIME, detail.time);
            values.put(DETAIL_COLUMN_TIPS, StringHelper.formatTips(detail.tips));
            db.insert(DETAIL_TABLE_NAME, null, values);

            if(null != detail.materials) {
                for (STCookbookMaterial material : detail.materials) {
                    ContentValues materials = new ContentValues();
                    materials.put(MATERIAL_COLUMN_ID, detail.cooking_id);
                    materials.put(MATERIAL_COLUMN_COUNT, material.count);
                    materials.put(MATERIAL_COLUMN_NAME, material.name);
                    materials.put(MATERIAL_COLUMN_TYPE, material.type);
                    db.insert(MATERIAL_TABLE_NAME, null, materials);
                }
            }

            if(null != detail.steps) {
                for (STCookbookStep step : detail.steps) {
                    ContentValues step_values = new ContentValues();
                    step_values.put(STEP_COLUMN_ID, detail.cooking_id);
                    step_values.put(STEP_COLUMN_IMG, step.image);
                    step_values.put(STEP_COLUMN_ORDER, step.order);
                    step_values.put(STEP_COLUMN_NAME, step.step);
                    db.insert(STEPS_TABLE_NAME, null, step_values);
                }
            }

            if(null != detail.complete_pic) {
                for (String pic : detail.complete_pic) {
                    ContentValues pic_values = new ContentValues();
                    pic_values.put(COMPLETE_COLUMN_PIC, pic);
                    db.insert(COMPLETE_PIC_TABLE_NAME, null, pic_values);
                }
            }

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
}
