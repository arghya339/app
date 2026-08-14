package com.offlinew.practica.db.boosterMocksDB;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import com.offlinew.practica.utils.TimeUtil;

public class BoosterMocksDBShim {
    static String TAG="BoosterMocksDBShim";
    Context context;
    public BoosterMocksDBShim(Context context){
        this.context = context;
    }

    public void createTable(){
        BoosterMocksDBManager manager = new BoosterMocksDBManager(context);
        manager.open();
        manager.createTable();
        manager.close();
    }
    public void upgradeTable(){
        BoosterMocksDBManager manager = new BoosterMocksDBManager(context);
        manager.open();
        manager.upgradeTable();
        manager.close();
    }

    /**
     *
     * APIS
     * increamentBy(mcq_id, correct_cnt, incorrect_cnt, not_attempted_cnt)
     * getCnts(mcq_id)
     *
     */


//    public MockItem getMcqCnts(String mcq_id){
//        MockItem res = null;//new McqCnts(mcq_id,0,0,0);
//
//        try {
//        BoosterMocksDBManager kvDBManager = new BoosterMocksDBManager(context);
//        kvDBManager.open();
//
//            Cursor cursor = kvDBManager.fetchVal(mcq_id);
//            if (cursor.moveToFirst()) {
//                do {
//                    String correct = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.CORRECT_CNT));
//                    String incorrect = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.INCORRECT_CNT));
//                    String notAttempted = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT));
//                    if (correct != null && incorrect != null && notAttempted != null) {
//                        res = new MockItem(mcq_id, Long.parseLong(correct), Long.parseLong(incorrect), Long.parseLong(notAttempted));
//                    }
//                    //Log.d(TAG," "+key+":"+res);
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//            kvDBManager.close();
//        }catch (Exception ignored){}
//
//        return res;
//    }


    public void insertMockItem(MockItem mockItem){
        BoosterMocksDBManager boosterMcqDBManager = new BoosterMocksDBManager(context);
        boosterMcqDBManager.open();
        boosterMcqDBManager.insert(mockItem.topicId,
                String.valueOf(mockItem.correctCnt),
                String.valueOf(mockItem.incorrectCnt),
                String.valueOf(mockItem.notAttemptedCnt),
                String.valueOf(mockItem.timestamp),
                String.valueOf(mockItem.timeTakenMs));
        boosterMcqDBManager.close();

    }


    public boolean isMockExist(String topicId){
        boolean res = false;
        BoosterMocksDBManager boosterMocksDBManager = new BoosterMocksDBManager(context);
        boosterMocksDBManager.open();

        Cursor cursor = boosterMocksDBManager.fetchVal(topicId);
        if(cursor.moveToFirst()){
            res = true;
        }
        cursor.close();
        boosterMocksDBManager.close();
        return res;
    }

//    private void updateVal(McqCnts updatedMcqCnts){
//        BoosterMocksDBManager boosterMcqDBManager = new BoosterMocksDBManager(context);
//        boosterMcqDBManager.open();
//        int rowChanged = boosterMcqDBManager.updateVal(updatedMcqCnts.mcqId,
//                String.valueOf(updatedMcqCnts.correctCnt),
//                String.valueOf(updatedMcqCnts.incorrectCnt),
//                String.valueOf(updatedMcqCnts.notAttemptedCnt));
//        Log.d(TAG,"Update rowChanged:"+rowChanged);
//        boosterMcqDBManager.close();
//    }

    /**
     *   ANALYTICS
     */
    public HashMap<String, MockItem> getAnalyticsTopicWise(String startTs, String endTs){
        HashMap<String, MockItem> res = new HashMap<>();

        try {
            BoosterMocksDBManager boosterMcqDBManager = new BoosterMocksDBManager(context);
            boosterMcqDBManager.open();

            Cursor cursor = boosterMcqDBManager.fetchByTimeRange(startTs, endTs);
            if (cursor.moveToFirst()) {
                do {
                    String topic_id = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.TOPIC_ID));
                    String ts = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.TIMESTAMP));
                    String correct = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.CORRECT_CNT));
                    String incorrect = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.INCORRECT_CNT));
                    String notAttempted = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.NOT_ATTEMPTED_CNT));
                    String timeTakenMs = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.TIME_TAKEN_MS));
                    if (correct != null && incorrect != null && notAttempted != null) {

                        if(res.containsKey(topic_id)){
                            MockItem mockItem = res.get(topic_id);
                            mockItem.timestamp = Long.parseLong(ts);
                            mockItem.correctCnt += Long.parseLong(correct);
                            mockItem.incorrectCnt += Long.parseLong(incorrect);
                            mockItem.notAttemptedCnt += Long.parseLong(notAttempted);
                            res.put(topic_id,mockItem);
                        }else{
                            res.put(
                                    topic_id,
                                    new MockItem(topic_id,
                                            Long.parseLong(ts),
                                    Long.parseLong(correct),
                                    Long.parseLong(incorrect),
                                    Long.parseLong(notAttempted),
                                            Long.parseLong(timeTakenMs)
                                    )
                            );
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

    public HashMap<String, Integer> getAnalyticsDateVsTestsCnt(String startTs, String endTs){
        HashMap<String, Integer> res = new HashMap<>();

        try {
            BoosterMocksDBManager boosterMocksDBManager = new BoosterMocksDBManager(context);
            boosterMocksDBManager.open();

            Cursor cursor = boosterMocksDBManager.fetchByTimeRange(startTs, endTs);
            if (cursor.moveToFirst()) {
                do {
                    String ts = cursor.getString((int) cursor.getColumnIndex(BoosterMocksDBHelper.TIMESTAMP));
                    String date = TimeUtil.convertToISTDateLegacy(Long.parseLong(ts));
                    if(res.containsKey(date)){
                        res.put(date,res.get(date)+1);
                    }else{
                        res.put(date,1);
                    }
                    //Log.d(TAG," "+key+":"+res);
                } while (cursor.moveToNext());
            }

            cursor.close();
            boosterMocksDBManager.close();
        }catch (Exception ignored){}

        return res;
    }

//    public ArrayList<MockItem> getSortedAnalytics(){
//
//        HashMap<String, MockItem> res = new HashMap<>();
//
//        try {
//            BoosterMocksDBManager boosterMcqDBManager = new BoosterMocksDBManager(context);
//            boosterMcqDBManager.open();
//
//            Cursor cursor = boosterMcqDBManager.fetchAll();
//            if (cursor.moveToFirst()) {
//                do {
//                    String mcqId = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.MCQ_ID));
//                    String correct = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.CORRECT_CNT));
//                    String incorrect = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.INCORRECT_CNT));
//                    String notAttempted = cursor.getString((int) cursor.getColumnIndex(BoosterMcqDBHelper.NOT_ATTEMPTED_CNT));
//                    if (correct != null && incorrect != null && notAttempted != null) {
//
//                        mcqId = mcqId.split("_batch")[0];
//
//
//                        if(res.containsKey(mcqId)){
//                            MockItem mcqCnts = res.get(mcqId);
//                            if(Long.parseLong(correct) > Long.parseLong(incorrect)){
//                                mcqCnts.correctCnt += 1;
//                            }else if(Long.parseLong(correct) < Long.parseLong(incorrect)){
//                                mcqCnts.incorrectCnt += 1;
//                            }
//
//                            res.put(mcqId,mcqCnts);
//                        }else{
//                            MockItem mcqCnts = new MockItem(mcqId,0,0,0);
//                            if(Long.parseLong(correct) > Long.parseLong(incorrect)){
//                                mcqCnts.correctCnt += 1;
//                            }else if(Long.parseLong(correct) < Long.parseLong(incorrect)){
//                                mcqCnts.incorrectCnt += 1;
//                            }
//                            res.put(mcqId,mcqCnts);
//                        }
//                    }
//                    //Log.d(TAG," "+key+":"+res);
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//            boosterMcqDBManager.close();
//        }catch (Exception ignored){}
//
//
//
//        ArrayList<MockItem> mcqRes = new ArrayList<>();
//
//
//        for(String key : res.keySet()){
//            mcqRes.add(res.get(key));
//        }
//
//        mcqRes.sort((a, b) -> {
//            double ratioA = (a.correctCnt + a.incorrectCnt == 0) ? 0 : (double) a.correctCnt / (a.correctCnt + a.incorrectCnt);
//            double ratioB = (b.correctCnt + b.incorrectCnt == 0) ? 0 : (double) b.correctCnt / (b.correctCnt + b.incorrectCnt);
//            return Double.compare(ratioA, ratioB);
//        });
//
//
//        return mcqRes;
//    }

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
