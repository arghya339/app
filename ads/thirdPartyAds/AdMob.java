package com.offlinew.practica.ads.thirdPartyAds;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.offlinew.practica.AppConfig;
import com.offlinew.practica.Log.Log;
import com.offlinew.practica.R;


public class AdMob {

    Context context;
    private static final String TAG = "AdMob";
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;

    private RewardedAd rewardedAd;

    public static final String adProviderName = "AdMob";
    public static long adProviderDefaultPrio = 25;

    private final String[] interstitialAdIds ={
    };

    private final String[] bannerAdIds ={
            AppConfig.AD_BANNER_1,//b1
            AppConfig.AD_BANNER_2//b2
    };



    private final String[] rewardedAdIds = {
            AppConfig.AD_R_1//r1
    };

    private final String[] rewardedIntAdIds = {
            AppConfig.AD_RI_1//ri_1
    };
    private RewardedInterstitialAd rewardedInterstitialAd;

    private final String[] customAdIds = {
            "ca-app-pub-2209172975448475/6495326742"//custom_ad_4
                    //,"ca-app-pub-3940256099942544/2247696110"//test
    };
    public AdMob(Context context){
        this.context = context;
        initializeAdMob();
    }

    public void initializeAdMob(){
        // Initialize the Mobile Ads SDK.
        //MobileAds.initialize(context, initializationStatus -> {});

        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(context, initializationStatus -> {});
                })
                .start();
    }

    public void loadBannerAd(RelativeLayout adHolder){
        // Inflate the fsa_lay.xml
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View adMobBanner1 = inflater.inflate(R.layout.ad_mob_banner_1, null);

        AdView mAdView = (AdView) adMobBanner1;
        adHolder.addView(mAdView);

        //mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }




    public void loadRewardedAd(Activity activity){
        // test ad ca-app-pub-3940256099942544/5224354917
        // real ad ca-app-pub-2209172975448475/7619678482
        AdRequest adRequest = new AdRequest.Builder().build();//todo replace with real ad
        RewardedAd.load(context,rewardedAdIds[0], //test ad unit "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "Ad was loaded.");
                        showRewardedAd(activity);
                    }
                });
    }


    public void showRewardedAd(Activity activity){
        if (rewardedAd != null) {
            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                    //Coins.addNewCoins(context,2);
                    Log.d(TAG, "Ad was clicked.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad dismissed fullscreen content.");
                    rewardedAd = null;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    Log.e(TAG, "Ad failed to show fullscreen content.");
                    rewardedAd = null;
                }

                @Override
                public void onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.");
                }
            });


            rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    if(rewardType.equals("coins")) {
                        //Coins.addNewCoins(context, rewardAmount);
                        //todo update coins count ui
                    }


                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

//    public void loadRewardedAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        int ranInt = RandomNumberUtil.randomNumberBetween(0,rewardedAdIds.length);
//        Log.d(TAG,"ad selection rewarded:"+ranInt);
//
//        RewardedAd.load(context, rewardedAdIds[ranInt], adRequest, new RewardedAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
//                Log.d(TAG,"rew ad loaded!");
//                mRewardedAd = rewardedAd;
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                Log.d(TAG,"rew ad load failed!");
//                mRewardedAd = null;
//            }
//        });
//    }

//    public void showRewardedAd(Activity activity) {
//        if (mRewardedAd != null) {
//            mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
//                @Override
//                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                    // Handle the reward
//                    int rewardAmount = rewardItem.getAmount();
//                    String rewardType = rewardItem.getType();
//                    Log.d(TAG,"reward earned: "+rewardType+" :"+rewardAmount);
//                    // TODO: Reward the user
//                }
//            });
//
//            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    mRewardedAd = null;
//                    loadRewardedAd(); // Load another ad for future use
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                    mRewardedAd = null;
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                    Log.d(TAG,"rew ad shown!");
//                    // Called when ad is shown.
//                }
//            });
//        } else {
//            // The rewarded ad wasn't ready yet.
//        }
//    }

//    public void loadInterstitialAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        int ranInt = RandomNumberUtil.randomNumberBetween(0,interstitialAdIds.length);
//        Log.d(TAG,"ad selection interstitial:"+ranInt);
//
//        AdConst.isIntLoading = true;
//        InterstitialAd.load(context, interstitialAdIds[ranInt], adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                Log.d(TAG,"inters ad loaded");
//                AdConst.isIntLoaded = true;
//                AdConst.isIntLoading = false;
//                //ToastUtil.Toast("interstitial ad loaded",context);
//                mInterstitialAd = interstitialAd;
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                Log.d(TAG,"inters ad load failed");
//                AdConst.isIntLoaded = false;
//                AdConst.isIntLoading = false;
//                //ToastUtil.Toast("interstitial ad load failed",context);
//                mInterstitialAd = null;
//            }
//        });
//    }


//    public void showInterstitialAd(Activity activity) {
//        if (mInterstitialAd != null) {
//            mInterstitialAd.show(activity);
//
//            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdClicked() {
//                    super.onAdClicked();
//                }
//
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    mInterstitialAd = null;
//                    AdEngine.isAdPlaying = false;
//                    loadInterstitialAd(); // Load another ad for future use
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                    mInterstitialAd = null;
//                    AdEngine.isAdPlaying = false;
//                }
//
//                @Override
//                public void onAdImpression() {
//                    super.onAdImpression();
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                    // Called when ad is shown.
//                    AdEngine.isAdPlaying = true;
//
//                }
//            });
//        }
////        else {
////            // The interstitial ad wasn't ready yet.
////        }
//    }

    /**
     * Rewarded interstitial ads
     */

    public void loadRewardedInterstitialAd(Context context){
        RewardedInterstitialAd.load(
                context,
                rewardedIntAdIds[0],
                new AdRequest.Builder().build(),
                new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        Log.d(TAG, "Ad was loaded.");
                        rewardedInterstitialAd = ad;
                        registerCallbackRewardedInterstitialAd(context);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                        rewardedInterstitialAd = null;
                    }
                });

    }

    private void registerCallbackRewardedInterstitialAd(Context context){
        rewardedInterstitialAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d(TAG, "The ad was dismissed.");
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        rewardedInterstitialAd = null;
                        //if (googleMobileAdsConsentManager.canRequestAds()) {
                            loadRewardedInterstitialAd(context);
                        //}
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d(TAG, "The ad failed to show.");
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        rewardedInterstitialAd = null;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        Log.d(TAG, "The ad was shown.");
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "The ad recorded an impression.");
                    }

                    @Override
                    public void onAdClicked() {
                        // Called when ad is clicked.
                        Log.d(TAG, "The ad was clicked.");
                    }
                });
    }

    public void showRewardedInterstitialAd(Activity activity){
        if(rewardedInterstitialAd != null) {
            rewardedInterstitialAd.show(
                    activity,
                    new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Log.d(TAG, "The user earned the reward.");
                            // Handle the reward.
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
        }
    }


}
