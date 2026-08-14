package com.offlinew.practica.db.settingDB;

import android.content.Context;
import android.database.Cursor;

import com.offlinew.practica.Log.Log;
import com.offlinew.practica.settings.SettingKV;


public class SettingDBShim {
    static String TAG="settingDBShim";
    Context context;
    public SettingDBShim(Context context){
        this.context = context;
        createDefaultSettingForFirstTime();
    }

    public void createTable(){
        SettingDBManager manager = new SettingDBManager(context);
        manager.open();
        manager.createTable();
        manager.close();
    }

    public void setSetting(String key,String val){
        String prevSetting = getSetting(key);

        SettingDBManager settingDBManager = new SettingDBManager(context);
        settingDBManager.open();

        if(prevSetting.equals("")){
            settingDBManager.insert(key,val);
        }else{
            updateSetting(key,val);
        }
        settingDBManager.close();
    }

    public String getSetting(String key){
        String res = "";
        SettingDBManager settingDBManager = new SettingDBManager(context);
        settingDBManager.open();

        Cursor cursor = settingDBManager.fetchSettingCol(key);
        if(cursor.moveToFirst()){
            do {
                String val = cursor.getString((int)cursor.getColumnIndex(SettingDBHelper.VALUE));
                if(val!=null) {
                    res = val;
                }
                Log.d(TAG," "+key+":"+res);
            } while (cursor.moveToNext());
        }

        cursor.close();
        settingDBManager.close();
        return res;
    }

    private void updateSetting(String key, String val){
        SettingDBManager settingDBManager = new SettingDBManager(context);
        settingDBManager.open();
        int rowChanged = settingDBManager.updateSetting(key,val);
        Log.d(TAG,"Update like rowChanged:"+rowChanged);

        settingDBManager.close();
    }

    private void setDefaultSetting(String key, String val){
        String existingVal = getSetting(key);
        //Log.d(TAG,"default value: key:"+existingVal);
        if(existingVal.equals("")){
            setSetting(key,val);
        }
    }
    private void createDefaultSettingForFirstTime(){
        String isFirstTime = getSetting(SettingKV.IS_FIRST_TIME);

        if(isFirstTime.equals("")){
            Log.d(TAG,"default setting creation");
            setSetting(SettingKV.IS_FIRST_TIME, SettingKV.IS_FIRST_TIME_VAL_F);


            //TODO add any new default setting key value
        }
    }
}
