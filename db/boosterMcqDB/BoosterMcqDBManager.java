package com.offlinew.practica.db.boosterMcqDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.offlinew.practica.Log.Log;

public class BoosterMcqDBManager {
    String TAG = "BoosterMcqDBManager";

    private BoosterMcqDBHelper boosterMcqDBHelper;
    private Context context;
    private SQLiteDatabase database;

    public BoosterMcqDBManager(Context context) {
        this.context = context;
    }

    public BoosterMcqDBManager open() throws SQLException {
        boosterMcqDBHelper = new BoosterMcqDBHelper(context);
        database = boosterMcqDBHelper.getWritableDatabase();
        //settingDBHelper.createNewTable(database);
        return this;
    }

    public void createTable(){
        boosterMcqDBHelper.createNewTable();
    }

    public void close() {
        boosterMcqDBHelper.close();
    }


    public void insert(String mcqId, String correct_cnt, String incorrect_cnt, String not_attempted_cnt ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(BoosterMcqDBHelper.MCQ_ID, mcqId);
        contentValue.put(BoosterMcqDBHelper.CORRECT_CNT, correct_cnt);
        contentValue.put(BoosterMcqDBHelper.INCORRECT_CNT, incorrect_cnt);
        contentValue.put(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT, not_attempted_cnt);

            database.insert(BoosterMcqDBHelper.TABLE_NAME, null, contentValue);

            Log.d(TAG, "data inserted!");

    }

    public Cursor fetchVal(String mcqid) {
        String rawQuery = "SELECT * FROM "+ BoosterMcqDBHelper.TABLE_NAME
                +" WHERE ("+ BoosterMcqDBHelper.MCQ_ID+"= ? )";
        String[] rawQueryArgs={mcqid};
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

    public Cursor fetchStartsWith(String prefix_mcqid) {
        String rawQuery = "SELECT * FROM "+ BoosterMcqDBHelper.TABLE_NAME
                +" WHERE ("+ BoosterMcqDBHelper.MCQ_ID+" LIKE ? )";
        String[] rawQueryArgs={prefix_mcqid+"%"};
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

    public Cursor fetchAll() {
        String rawQuery = "SELECT * FROM "+ BoosterMcqDBHelper.TABLE_NAME;
        Cursor cursor=null;
        try {
            cursor = database.rawQuery(rawQuery, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateVal(String mcqId, String correct_cnt, String incorrect_cnt, String not_attempted_cnt){

        ContentValues contentValue = new ContentValues();
        //contentValue.put(BoosterMcqDBHelper.MCQ_ID, mcqId);
        contentValue.put(BoosterMcqDBHelper.CORRECT_CNT, correct_cnt);
        contentValue.put(BoosterMcqDBHelper.INCORRECT_CNT, incorrect_cnt);
        contentValue.put(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT, not_attempted_cnt);

        String[] whereArgs = new String[] { mcqId  };

        int i = database.update(BoosterMcqDBHelper.TABLE_NAME, contentValue, BoosterMcqDBHelper.MCQ_ID + " = ? ", whereArgs);
        return i;
    }


//    public boolean deleteKey(String key) {
//        String[] wArgs = {key};
//        int nDeletedRow = database.delete(KvDBHelper.TABLE_NAME, KvDBHelper.KEY + " = ? ", wArgs);
//        return nDeletedRow > 0;
//    }

//    public boolean deleteKeysStartingWith(String prefix) {
//        String whereClause = KvDBHelper.KEY + " LIKE ?";
//        String[] whereArgs = { prefix+"%" };
//        int nDeletedRows = database.delete(KvDBHelper.TABLE_NAME, whereClause, whereArgs);
//        return nDeletedRows > 0;
//    }



}
