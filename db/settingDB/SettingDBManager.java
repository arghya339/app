package com.offlinew.practica.db.settingDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.offlinew.practica.Log.Log;

public class SettingDBManager {
    String TAG = "SettingDBManager";

    private SettingDBHelper settingDBHelper;
    private Context context;
    private SQLiteDatabase database;

    public SettingDBManager(Context context) {
        this.context = context;
    }

    public SettingDBManager open() throws SQLException {
        settingDBHelper = new SettingDBHelper(context);
        database = settingDBHelper.getWritableDatabase();
        //settingDBHelper.createNewTable(database);
        return this;
    }

    public void createTable(){
        settingDBHelper.createNewTable();
    }

    public void close() {
        settingDBHelper.close();
    }


    public void insert(String key, String val) {

            ContentValues contentValue = new ContentValues();
            contentValue.put(SettingDBHelper.KEY, key);
            contentValue.put(SettingDBHelper.VALUE, val);

            database.insert(SettingDBHelper.TABLE_NAME, null, contentValue);

            Log.d(TAG, "data inserted!");

    }

    public Cursor fetchSettingCol(String key) {
        String rawQuery = "SELECT * FROM "+SettingDBHelper.TABLE_NAME
                +" WHERE ("+SettingDBHelper.KEY+"= ? )";
        String[] rawQueryArgs={key};
        Cursor cursor=null;
        try {
            cursor = database.rawQuery(rawQuery, rawQueryArgs);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateSetting(String key, String val){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SettingDBHelper.KEY, key);
        contentValues.put(SettingDBHelper.VALUE, val);

        String[] whereArgs = new String[] { key  };

        int i = database.update(SettingDBHelper.TABLE_NAME, contentValues, SettingDBHelper.KEY + " = ? ", whereArgs);
        return i;
    }

}
