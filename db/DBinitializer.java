package com.offlinew.practica.db;

import android.content.Context;

import com.offlinew.practica.db.boosterMcqDB.BoosterMcqDBShim;
import com.offlinew.practica.db.boosterMocksDB.BoosterMocksDBShim;
import com.offlinew.practica.db.kvDB.KvDBShim;
import com.offlinew.practica.db.settingDB.SettingDBShim;


public class DBinitializer {
    public DBinitializer(Context context){

        KvDBShim kvDBShim = new KvDBShim(context);
        kvDBShim.createTable();

        SettingDBShim settingDBShim = new SettingDBShim(context);
        settingDBShim.createTable();

        BoosterMcqDBShim boosterMcqDBShim = new BoosterMcqDBShim(context);
        boosterMcqDBShim.createTable();

        BoosterMocksDBShim boosterMocksDBShim = new BoosterMocksDBShim(context);
        boosterMocksDBShim.createTable();
        boosterMocksDBShim.upgradeTable();

    }
}
