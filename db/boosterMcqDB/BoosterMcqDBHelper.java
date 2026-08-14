package com.offlinew.practica.db.boosterMcqDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BoosterMcqDBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "Pracrica.booster";
    static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "mcq_table";
    public static final String MCQ_ID = "mcq_id";
    public static final String CORRECT_CNT = "correct";
    public static final String INCORRECT_CNT = "incorrect";
    public static final String NOT_ATTEMPTED_CNT = "notAttempted";
    public static final String EXTRA = "extra";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + MCQ_ID + " TEXT PRIMARY KEY, "
            + CORRECT_CNT + " TEXT NOT NULL, "
            + INCORRECT_CNT + " TEXT NOT NULL, "
            + NOT_ATTEMPTED_CNT + " TEXT NOT NULL, "
            + EXTRA + " TEXT "
            +");";


//    public ChatDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public BoosterMcqDBHelper(Context context) {
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
    }

    public void createNewTable(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            //do nothing
        }
    }
}
