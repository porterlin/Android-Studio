package com.cornez.shippingcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlDataBaseHelper extends SQLiteOpenHelper {

    //private static final String DataBaseName = "DataBaseIt";
    //private static final int DataBaseVersion = 1;
    String TableName;

    public SqlDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, name, factory, version);
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                //"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT not null," +
                "Knee REAL," +
                "Age REAL," +
                "Height INTEGER," +
                "Gender TEXT," +
                "Weight REAL," +
                "Activity INTEGER" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE " + TableName;
        sqLiteDatabase.execSQL(SQL);
    }

    //新增資料
    public void addData(String name, double knee, double age, int height, String gender, double weight, int activity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Knee", knee);
        values.put("Age", age);
        values.put("Height", height);
        values.put("Gender", gender);
        values.put("Weight", weight);
        values.put("Activity", activity);
        db.insert(TableName, null, values);
    }

    //刪除特定資料
    public void deleteData(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TableName,"Name = '" + name + "'",null);
    }

    //取得所有資料
    public ArrayList<HashMap<String, String>> showAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName, null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String name = c.getString(0);
            String knee = c.getString(1);
            String age = c.getString(2);
            String height = c.getString(3);
            String gender = c.getString(4);
            String weight = c.getString(5);
            String activity = c.getString(6);

            hashMap.put("name", name);
            hashMap.put("knee", knee);
            hashMap.put("age", age);
            hashMap.put("height", height);
            hashMap.put("gender", gender);
            hashMap.put("weight", weight);
            hashMap.put("activity", activity);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    //修改資料
    public void modify(String name, double knee, double age, int height, String gender, double weight, int activity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Knee", knee);
        values.put("Age", age);
        values.put("Height", height);
        values.put("Gender", gender);
        values.put("Weight", weight);
        values.put("Activity", activity);
        db.update(TableName, values, "Name = '" + name + "'", null);
    }

    //檢查table中有沒有特定資料
    public boolean checkByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE Name =" + "'" + name + "'", null);

        if (c.moveToFirst() == false)
            return false;
        return true;
    }

    //讀取一筆資料
    public ArrayList<HashMap<String,String>> searchByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE Name =" + "'" + name + "'", null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String name1 = c.getString(0);
            String knee = c.getString(1);
            String age = c.getString(2);
            String height = c.getString(3);
            String gender = c.getString(4);
            String weight = c.getString(5);
            String activity = c.getString(6);

            hashMap.put("name", name1);
            hashMap.put("knee", knee);
            hashMap.put("age", age);
            hashMap.put("height", height);
            hashMap.put("gender", gender);
            hashMap.put("weight", weight);
            hashMap.put("activity", activity);
            arrayList.add(hashMap);
        }
        return arrayList;
    }
}
