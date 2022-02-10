package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlDataBaseHelper extends SQLiteOpenHelper {
    String TableName;

    public SqlDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, name, factory, version);
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                //"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                //"Name TEXT not null," +
                "Account TEXT not null," +
                "Password TEXT not null," +
                "Progress INTEGER," +
                "CheckComplete TEXT" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE " + TableName;
        sqLiteDatabase.execSQL(SQL);
    }

    //新增資料
    public void addData(String account, String password, int progress, String checkComplete) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Account", account);
        values.put("Password", password);
        values.put("Progress", progress);
        values.put("CheckComplete", checkComplete);
        db.insert(TableName, null, values);
    }

    //刪除特定資料
    public void deleteData(String account){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TableName,"Account = '" + account + "'",null);
    }

    /*//取得所有資料
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
    }*/

    //修改資料
    public void modify(String account, String password, int progress, String checkComplete) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Account", account);
        values.put("Password", password);
        values.put("Progress", progress);
        values.put("CheckComplete", checkComplete);
        db.update(TableName, values, "Account = '" + account + "'", null);
    }

    //檢查table中有沒有特定資料
    public boolean checkByAccount(String account){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE Account =" + "'" + account + "'", null);

        if (c.moveToFirst() == false)
            return false;
        return true;
    }

    //讀取一筆資料
    public ArrayList<HashMap<String,String>> searchByAC(String account){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName
                + " WHERE Account =" + "'" + account + "'", null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();

            String account1 = c.getString(0);
            String password = c.getString(1);
            String progress = c.getString(2);
            String checkComplete = c.getString(3);

            hashMap.put("account", account1);
            hashMap.put("password", password);
            hashMap.put("progress", progress);
            hashMap.put("checkComplete", checkComplete);

            arrayList.add(hashMap);
        }
        return arrayList;
    }
}
