package com.offlinew.practica.db.boosterMocksDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BoosterMocksDBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "Pracrica.booster";
    static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "mock_table";
    public static final String MOCK_ID = "mock_id";
    public static final String TOPIC_ID = "topic_id";
    public static final String TIMESTAMP = "mock_ts";
    public static final String CORRECT_CNT = "correct";
    public static final String INCORRECT_CNT = "incorrect";
    public static final String NOT_ATTEMPTED_CNT = "notAttempted";
    public static final String TIME_TAKEN_MS = "TimeTakenMs";
    public static final String EXTRA = "extra";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + MOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TOPIC_ID + " TEXT NOT NULL, "
            + TIMESTAMP + " TEXT NOT NULL, "
            + CORRECT_CNT + " TEXT NOT NULL, "
            + INCORRECT_CNT + " TEXT NOT NULL, "
            + NOT_ATTEMPTED_CNT + " TEXT NOT NULL, "
            + TIME_TAKEN_MS + " TEXT ,"
            + EXTRA + " TEXT "
            +");";


//    public ChatDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public BoosterMocksDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //onCreate(db);

        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME +
                    " ADD COLUMN " + TIME_TAKEN_MS + " TEXT");
        }
    }

    public void createNewTable(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            //do nothing
        }
    }

    public void upgradeTable(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.execSQL("ALTER TABLE " + TABLE_NAME +
                    " ADD COLUMN " + TIME_TAKEN_MS + " TEXT");
        }catch (Exception e){
            //do nothing
        }

    }
}
