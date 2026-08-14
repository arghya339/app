package com.offlinew.practica.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


/**
 * Usage
 */

/*
// broadcast receiver to be called from onCreateView
    private void setUpBroadcastReceiver(){

            localBroadcastNewSelfPostReceived = new LocalBroadcast(context) {
                @Override
                public void onReceiveInterface(String key, String value) {
                    Log.d(TAG, ": NEW SELF POST RECEIVED! key=" + key + " value=" + value);
                    Sounds.playMessageReceivedSound(context);
                    try {
                        init_after_inflate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            broadcastReceiverNewSelfPostReceived = localBroadcastNewSelfPostReceived.createLocalBroadcastReceiver("new_self_post", "new_self_post_key");

    }
// unregister broadcast receiver
    private void unregisterLocalBroadcast(){

        try {//one registered broadcast receiver
            context.unregisterReceiver(broadcastReceiverNewSelfPostReceived);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"error while unregistering localbroadcast context");
        }

        try {//another broadcast receiver
            requireActivity().unregisterReceiver(broadcastReceiverNewSelfPostReceived);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"error while unregistering localbroadcast rekuire activity");
        }

    }

 */
/**
 * Calling instance
 LocalBroadcast.sendLocalBroadcast(context,"new_post","new_post_key",postID);
 */
public abstract class LocalBroadcast {

    Context context;
    public LocalBroadcast(Context context){
        this.context = context;
    }
    public abstract void onReceiveInterface(String key, String value);

    /**
     *
     * @param context
     * @param event_name name of event, listener should be implemented using same name
     * @param key key for the extra data, listener should use same key
     * @param value value to be passed
     */
    public static void sendLocalBroadcast(Context context,String event_name,String key, String value){
        // Send a local broadcast
        Intent intent = new Intent(event_name);
        intent.putExtra(key, value);
        //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        context.sendBroadcast(intent);
    }

    public BroadcastReceiver createLocalBroadcastReceiver(String eventName, String key){
        BroadcastReceiver broadcastReceiver = createBroadcastReceiver(key);
        registerLocalBroadcast(eventName,broadcastReceiver);
        return broadcastReceiver;
    }

    public BroadcastReceiver createBroadcastReceiver(String key){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Handle the received message
                String value = intent.getStringExtra(key);
                onReceiveInterface(key,value);
                //messageTextView.setText(message);
            }
        };

        return broadcastReceiver;
    }

    public void registerLocalBroadcast(String event_name, BroadcastReceiver broadcastReceiver){
        // Register the local broadcast receiver
        IntentFilter intentFilter = new IntentFilter(event_name);
        //LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
        context.registerReceiver(broadcastReceiver,intentFilter,Context.RECEIVER_EXPORTED);
    }

    public  void  unregisterLocalBroadcast(BroadcastReceiver broadcastReceiver){
        // Unregister the local broadcast receiver when the activity is destroyed
        //LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
        context.unregisterReceiver(broadcastReceiver);
    }

}

