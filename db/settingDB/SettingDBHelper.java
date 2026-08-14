package com.offlinew.practica.db.settingDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingDBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "AutoCutAI.DB.SETTING";
    static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Setting_table";
    public static final String KEY = "key_str";
    public static final String VALUE = "value_str";
    public static final String EXTRA = "extra";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + KEY + " TEXT PRIMARY KEY, "
            + VALUE + " TEXT NOT NULL, "
            + EXTRA + " TEXT "
            +");";


//    public ChatDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public SettingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
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
