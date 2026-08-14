package com.offlinew.practica.db.boosterMocksDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.offlinew.practica.Log.Log;

public class BoosterMocksDBManager {
    String TAG = "BoosterMocksDBManager";

    private BoosterMocksDBHelper boosterMocksDBHelper;
    private Context context;
    private SQLiteDatabase database;

    public BoosterMocksDBManager(Context context) {
        this.context = context;
    }

    public BoosterMocksDBManager open() throws SQLException {
        boosterMocksDBHelper = new BoosterMocksDBHelper(context);
        database = boosterMocksDBHelper.getWritableDatabase();
        return this;
    }

    public void createTable(){
        boosterMocksDBHelper.createNewTable();
    }
    public void upgradeTable(){
        boosterMocksDBHelper.upgradeTable();
    }

    public void close() {
        boosterMocksDBHelper.close();
    }


    public void insert(String topicId, String correct_cnt, String incorrect_cnt, String not_attempted_cnt, String ts_ms, String time_taken_ms ) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(BoosterMocksDBHelper.TOPIC_ID, topicId);
        contentValue.put(BoosterMocksDBHelper.TIMESTAMP, ts_ms);
        contentValue.put(BoosterMocksDBHelper.CORRECT_CNT, correct_cnt);
        contentValue.put(BoosterMocksDBHelper.INCORRECT_CNT, incorrect_cnt);
        contentValue.put(BoosterMocksDBHelper.NOT_ATTEMPTED_CNT, not_attempted_cnt);
        contentValue.put(BoosterMocksDBHelper.TIME_TAKEN_MS, time_taken_ms);

            database.insert(BoosterMocksDBHelper.TABLE_NAME, null, contentValue);

            Log.d(TAG, "data inserted!");

    }

    public Cursor fetchVal(String topicId) {
        String rawQuery = "SELECT * FROM "+ BoosterMocksDBHelper.TABLE_NAME
                +" WHERE ("+ BoosterMocksDBHelper.TOPIC_ID+" = ? )";
        String[] rawQueryArgs={topicId};
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

    public Cursor fetchByTimeRange(String startTs, String endTs) {
        String rawQuery = "SELECT * FROM "+ BoosterMocksDBHelper.TABLE_NAME
                + " WHERE " +BoosterMocksDBHelper.TIMESTAMP + " BETWEEN ? AND ?";
        String[] rawQueryArgs={startTs, endTs};
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

    public Cursor fetchStartsWith(String prefix_topic_id) {
        String rawQuery = "SELECT * FROM "+ BoosterMocksDBHelper.TABLE_NAME
                +" WHERE ("+ BoosterMocksDBHelper.TOPIC_ID+" LIKE ? )";
        String[] rawQueryArgs={prefix_topic_id+"%"};
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
        String rawQuery = "SELECT * FROM "+ BoosterMocksDBHelper.TABLE_NAME;
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

//    public int updateVal(String mcqId, String correct_cnt, String incorrect_cnt, String not_attempted_cnt){
//
//        ContentValues contentValue = new ContentValues();
//        //contentValue.put(BoosterMcqDBHelper.MCQ_ID, mcqId);
//        contentValue.put(BoosterMcqDBHelper.CORRECT_CNT, correct_cnt);
//        contentValue.put(BoosterMcqDBHelper.INCORRECT_CNT, incorrect_cnt);
//        contentValue.put(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT, not_attempted_cnt);
//
//        String[] whereArgs = new String[] { mcqId  };
//
//        int i = database.update(BoosterMcqDBHelper.TABLE_NAME, contentValue, BoosterMcqDBHelper.MCQ_ID + " = ? ", whereArgs);
//        return i;
//    }


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
