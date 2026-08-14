package com.offlinew.practica.ads;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;


import com.offlinew.practica.ads.thirdPartyAds.AdMob;
import com.offlinew.practica.online.InternetUtil;
import com.offlinew.practica.settings.Settings;
import com.offlinew.practica.Log.Log;
import com.offlinew.practica.utils.TimeUtil;


public class AdEngine {
    private static String TAG = "AdEngine";
    private static AdEngine instance;
    public static boolean isAdPlaying = false;
    public static  boolean isLastAdValid = false;
    Context context;
    Activity activity = null;
    AdMob adMob;

    boolean isPlaying;

    private AdEngine(Context context){
        this.context = context;
        adMob = new AdMob(context);

        if(InternetUtil.isInternetAvailable(context)){
            Log.d(TAG,"internet available");
            //adMob.loadInterstitialAd();
            //adMob.loadRewardedAd();
        }else{
            Log.d(TAG,"internet unavailable");
        }
    }
    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public static synchronized AdEngine getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG,"creating new instance of AdEngine");
            instance = new AdEngine(context);
        }
        return instance;
    }


    public void showAdmobBanner(RelativeLayout adHolder){
        adMob.loadBannerAd(adHolder);
    }

    public void loadAndShowRewardedAd(Activity activity){
        adMob.loadRewardedAd(activity);
    }

    public void loadRewardedIntAd(Context context){
        adMob.loadRewardedInterstitialAd(context);
    }

    public void showRewardedIntAd(Activity activity){
        adMob.showRewardedInterstitialAd(activity);
    }

//    public void showAnAd( View v){
//        Log.d(TAG,"isAdToShow? "+isAdToShowNow());
//
//        if(isAdToShowNow()) {
//            //create weight array and name array
//            ArrayList<Long> weights = new ArrayList<>();
//            ArrayList<String> adProviderNames = new ArrayList<>();
//
//            if (AdConst.isIntLoaded && activity!=null){
//                String adProviderName = AdMob.adProviderName;
//                long defaultPrio = AdMob.adProviderDefaultPrio;
//                long priority = Long.parseLong(Settings.getSetting(context,adProviderName+"priority",String.valueOf(defaultPrio)));
//                weights.add(priority);
//                adProviderNames.add(adProviderName);
//            }
//
//            if(oLAds.isAdAvailable()){
//                String adProviderName = OLAds.adProviderName;
//                long defaultPrio = OLAds.adProviderDefaultPrio;
//                long priority = Long.parseLong(Settings.getSetting(context,adProviderName+"priority",String.valueOf(defaultPrio)));
//                weights.add(priority);
//                adProviderNames.add(adProviderName);
//            }
//
//            if(metaAd.isAdAvailable()){
//                String adProviderName = MetaAd.adProviderName;
//                long defaultPrio = MetaAd.adProviderDefaultPrio;
//                long priority = Long.parseLong(Settings.getSetting(context,adProviderName+"priority",String.valueOf(defaultPrio)));
//                weights.add(priority);
//                adProviderNames.add(adProviderName);
//            }
//
//            if(weights.size()>0) {
//
//                long[] weghtsLong = new long[weights.size()];
//                for(int i=0;i<weights.size();i++){
//                    weghtsLong[i] = weights.get(i);
//                    Log.d(TAG,"available ad providers:"+adProviderNames.get(i));
//                }
//                int randomSelectedIndex = RandomNumberUtil.weightedRandomNumber(weghtsLong,weghtsLong.length);
//                String randomProviderName = adProviderNames.get(randomSelectedIndex);
//
//
//                Log.d(TAG,"choosen ad: "+ randomProviderName);
//                switch (randomProviderName){
//
//                    case  OLAds.adProviderName:
//                        oLAds.show_ad(v);
//                        Settings.setSetting(context,AdsSettingKey.LAST_AD_SHOWN_TS_MS,TimeUtil.getTimeStampMillisString());
//                        isLastAdValid = true;
//                        break;
//
//                    case AdMob.adProviderName:
//                        adMob.showInterstitialAd(activity);
//                        Settings.setSetting(context,AdsSettingKey.LAST_AD_SHOWN_TS_MS,TimeUtil.getTimeStampMillisString());
//                        isLastAdValid = true;
//                        break;
//
//                    case MetaAd.adProviderName:
//                        metaAd.showAd();
//                        Settings.setSetting(context,AdsSettingKey.LAST_AD_SHOWN_TS_MS,TimeUtil.getTimeStampMillisString());
//                        isLastAdValid = true;
//                        break;
//
//                    default:
//                        Log.e(TAG,"choosing some other unlisted provider");
//                        break;
//                }
//
//            }else{
//                AdEngine.isAdPlaying = false;
//                isLastAdValid = false;
//            }
//
//
//
//
////            if (AdConst.isIntLoaded && activity!=null) {
////                adMob.showInterstitialAd(activity);
////                AdConst.lastAdShownMs = TimeUtil.getTimeStampMillis();
////                isLastAdValid = true;
////            } else if(oLAds.isAdAvailable()) {
////                oLAds.show_ad(v);
////                AdConst.lastAdShownMs = TimeUtil.getTimeStampMillis();
////                isLastAdValid = true;
////            }else{
////                //do nothing
////                AdEngine.isAdPlaying = false;
////                isLastAdValid = false;
////            }
//
//        }
//    }

    public void adClosed(){
        Log.d(TAG,"Ad closed callled!");
        if(isLastAdValid) {
            Settings.setSetting(context,AdsSettingKey.LAST_AD_SHOWN_TS_MS,TimeUtil.getTimeStampMillisString());
            //AdConst.lastAdShownTsMs = TimeUtil.getTimeStampMillis();
        }
    }

}
