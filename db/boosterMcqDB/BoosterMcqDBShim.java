package com.offlinew.practica.db.boosterMcqDB;

import android.content.Context;
import android.database.Cursor;

import com.offlinew.practica.Log.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class BoosterMcqDBShim {
    static String TAG="kvDBShim";
    Context context;
    public BoosterMcqDBShim(Context context){
        this.context = context;
    }

    public void createTable(){
        BoosterMcqDBManager manager = new BoosterMcqDBManager(context);
        manager.open();
        manager.createTable();
        manager.close();
    }

    /**
     *
     * APIS
     * increamentBy(mcq_id, correct_cnt, incorrect_cnt, not_attempted_cnt)
     * getCnts(mcq_id)
     *
     */


    public McqCnts getMcqCnts(String mcq_id){
        McqCnts res = null;//new McqCnts(mcq_id,0,0,0);

        try {
        BoosterMcqDBManager kvDBManager = new BoosterMcqDBManager(context);
        kvDBManager.open();

            Cursor cursor = kvDBManager.fetchVal(mcq_id);
            if (cursor.moveToFirst()) {
                do {
                    String correct = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.CORRECT_CNT));
                    String incorrect = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.INCORRECT_CNT));
                    String notAttempted = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT));
                    if (correct != null && incorrect != null && notAttempted != null) {
                        res = new McqCnts(mcq_id, Long.parseLong(correct), Long.parseLong(incorrect), Long.parseLong(notAttempted));
                    }
                    //Log.d(TAG," "+key+":"+res);
                } while (cursor.moveToNext());
            }

            cursor.close();
            kvDBManager.close();
        }catch (Exception ignored){}

        return res;
    }


    public void incrementBy(McqCnts deltaMcqCnts){
        //String prevSetting = getVal(key);
        if (isMcqExist(deltaMcqCnts.mcqId)) {
            McqCnts mcqCnts = getMcqCnts(deltaMcqCnts.mcqId);
            mcqCnts.correctCnt += deltaMcqCnts.correctCnt;
            mcqCnts.incorrectCnt += deltaMcqCnts.incorrectCnt;
            mcqCnts.notAttemptedCnt += deltaMcqCnts.notAttemptedCnt;

            updateVal(mcqCnts);
        } else {
            BoosterMcqDBManager boosterMcqDBManager = new BoosterMcqDBManager(context);
            boosterMcqDBManager.open();
            boosterMcqDBManager.insert(deltaMcqCnts.mcqId,
                    String.valueOf(deltaMcqCnts.correctCnt),
                    String.valueOf(deltaMcqCnts.incorrectCnt),
                    String.valueOf(deltaMcqCnts.notAttemptedCnt));
            boosterMcqDBManager.close();
        }
    }


    public boolean isMcqExist(String mcqId){
        boolean res = false;
        BoosterMcqDBManager boosterMcqDBManager = new BoosterMcqDBManager(context);
        boosterMcqDBManager.open();

        Cursor cursor = boosterMcqDBManager.fetchVal(mcqId);
        if(cursor.moveToFirst()){
            res = true;
        }
        cursor.close();
        boosterMcqDBManager.close();
        return res;
    }

    private void updateVal(McqCnts updatedMcqCnts){
        BoosterMcqDBManager boosterMcqDBManager = new BoosterMcqDBManager(context);
        boosterMcqDBManager.open();
        int rowChanged = boosterMcqDBManager.updateVal(updatedMcqCnts.mcqId,
                String.valueOf(updatedMcqCnts.correctCnt),
                String.valueOf(updatedMcqCnts.incorrectCnt),
                String.valueOf(updatedMcqCnts.notAttemptedCnt));
        Log.d(TAG,"Update rowChanged:"+rowChanged);
        boosterMcqDBManager.close();
    }

    /**
     *   ANALYTICS
     */
    public HashMap<String, McqCnts> getAnalytics(){
        HashMap<String, McqCnts> res = new HashMap<>();

        try {
            BoosterMcqDBManager boosterMcqDBManager = new BoosterMcqDBManager(context);
            boosterMcqDBManager.open();

            Cursor cursor = boosterMcqDBManager.fetchAll();
            if (cursor.moveToFirst()) {
                do {
                    String mcqId = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.MCQ_ID));
                    String correct = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.CORRECT_CNT));
                    String incorrect = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.INCORRECT_CNT));
                    String notAttempted = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT));
                    if (correct != null && incorrect != null && notAttempted != null) {

                        mcqId = mcqId.split("_batch")[0];

                        if(res.containsKey(mcqId)){
                            McqCnts mcqCnts = res.get(mcqId);
                            mcqCnts.correctCnt += Long.parseLong(correct);
                            mcqCnts.incorrectCnt += Long.parseLong(incorrect);
                            mcqCnts.notAttemptedCnt += Long.parseLong(notAttempted);
                            res.put(mcqId,mcqCnts);
                        }else{
                            res.put(mcqId,new McqCnts(mcqId,
                                    Long.parseLong(correct),
                                    Long.parseLong(incorrect),
                                    Long.parseLong(notAttempted)));
                        }
                    }
                    //Log.d(TAG," "+key+":"+res);
                } while (cursor.moveToNext());
            }

            cursor.close();
            boosterMcqDBManager.close();
        }catch (Exception ignored){}

        return res;
    }

    public ArrayList<McqCnts> getSortedAnalytics(){

        HashMap<String, McqCnts> res = new HashMap<>();

        try {
            BoosterMcqDBManager boosterMcqDBManager = new BoosterMcqDBManager(context);
            boosterMcqDBManager.open();

            Cursor cursor = boosterMcqDBManager.fetchAll();
            if (cursor.moveToFirst()) {
                do {
                    String mcqId = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.MCQ_ID));
                    String correct = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.CORRECT_CNT));
                    String incorrect = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.INCORRECT_CNT));
                    String notAttempted = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT));
                    if (correct != null && incorrect != null && notAttempted != null) {

                        mcqId = mcqId.split("_batch")[0];


                        if(res.containsKey(mcqId)){
                            McqCnts mcqCnts = res.get(mcqId);
                            if(Long.parseLong(correct) > Long.parseLong(incorrect)){
                                mcqCnts.correctCnt += 1;
                            }else if(Long.parseLong(correct) < Long.parseLong(incorrect)){
                                mcqCnts.incorrectCnt += 1;
                            }

                            res.put(mcqId,mcqCnts);
                        }else{
                            McqCnts mcqCnts = new McqCnts(mcqId,0,0,0);
                            if(Long.parseLong(correct) > Long.parseLong(incorrect)){
                                mcqCnts.correctCnt += 1;
                            }else if(Long.parseLong(correct) < Long.parseLong(incorrect)){
                                mcqCnts.incorrectCnt += 1;
                            }
                            res.put(mcqId,mcqCnts);
                        }
                    }
                    //Log.d(TAG," "+key+":"+res);
                } while (cursor.moveToNext());
            }

            cursor.close();
            boosterMcqDBManager.close();
        }catch (Exception ignored){}



        ArrayList<McqCnts> mcqRes = new ArrayList<>();


        for(String key : res.keySet()){
            mcqRes.add(res.get(key));
        }

        mcqRes.sort((a, b) -> {
            double ratioA = (a.correctCnt + a.incorrectCnt == 0) ? 0 : (double) a.correctCnt / (a.correctCnt + a.incorrectCnt);
            double ratioB = (b.correctCnt + b.incorrectCnt == 0) ? 0 : (double) b.correctCnt / (b.correctCnt + b.incorrectCnt);
            return Double.compare(ratioA, ratioB);
        });


        return mcqRes;
    }

//    public void setKeyVal(String key,String val){
//        //String prevSetting = getVal(key);
//        if (isKeyExist(key)) {
//            updateVal(key,val);
//        } else {
//            KvDBManager kvDBManager = new KvDBManager(context);
//            kvDBManager.open();
//            kvDBManager.insert(key,val);
//            kvDBManager.close();
//        }
//    }

//    public String getVal(String key){
//        String res = "";
//        KvDBManager kvDBManager = new KvDBManager(context);
//        kvDBManager.open();
//
//        Cursor cursor = kvDBManager.fetchVal(key);
//        if(cursor.moveToFirst()){
//            do {
//                String val = cursor.getString((int)cursor.getColumnIndex(KvDBHelper.VALUE));
//                if(val!=null) {
//                    res = val;
//                }
//                Log.d(TAG," "+key+":"+res);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        kvDBManager.close();
//        return res;
//    }



//    public boolean deleteKey(String key){
//        KvDBManager kvDBManager = new KvDBManager(context);
//        kvDBManager.open();
//        boolean isDeleted = kvDBManager.deleteKey(key);
//        kvDBManager.close();
//        return isDeleted;
//    }

//    public boolean deleteKeysStartingWith(String prefix) {
//        KvDBManager kvDBManager = new KvDBManager(context);
//        kvDBManager.open();
//        boolean isDeleted = kvDBManager.deleteKeysStartingWith(prefix);
//        kvDBManager.close();
//        return isDeleted;
//    }

//    private void updateVal(String key, String val){
//        KvDBManager kvDBManager = new KvDBManager(context);
//        kvDBManager.open();
//        int rowChanged = kvDBManager.updateVal(key,val);
//        Log.d(TAG,"Update rowChanged:"+rowChanged);
//
//        kvDBManager.close();
//    }

//    private void setDefaultVal(String key, String val){
//        String existingVal = getVal(key);
//        //Log.d(TAG,"default value: key:"+existingVal);
//        if(existingVal.equals("")){
//            setKeyVal(key,val);
//        }
//    }


}
