package com.offlinew.practica.utils;

import android.content.Context;

import com.offlinew.practica.file.InternalFileHandler;

import org.json.JSONObject;

public class ResultCommentUtil {
    Context context;
    InternalFileHandler ifh;

    public ResultCommentUtil(Context context){
        this.context = context;
        ifh = new InternalFileHandler(context);
        loadAllFilesToInternalStorage();
    }

    private void loadAllFilesToInternalStorage(){
        for(int i = 1; i < 7; i++) {
            AssetUtils.copyAssetToInternalStorage(context, "comments"+i+ ".json");
            ifh.moveFile(ifh.getInternalDirPath()+"/comments"+i+".json",
                    ifh.getInternalDirPath()+"/"+ifh.DIR_OTHERS+"/comments"+i+".json");
        }
    }

    public String getResultMessage(int percent){
        int fileIndex = RandomNumberUtil.randomNumberBetween(1,7);
        String comment = "";
        {
            String commentsJsonStr = ifh.readFromTextFile(ifh.DIR_OTHERS,"comments"+fileIndex+".json");
            try{
                JSONObject commentsJson = new JSONObject(commentsJsonStr);
                if(commentsJson.has(""+percent)){
                    comment = commentsJson.getString(""+percent);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return comment;
    }

}
