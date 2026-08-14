package com.offlinew.practica.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

import com.offlinew.practica.R;
import com.offlinew.practica.db.kvDB.KvDBShim;

public class FeedbackUtil {

    private final Vibrator vibrator;
    private final SoundPool soundPool;
    private final int correctSound;
    private final int wrongSound;

    public static String SOUND_DB_KEY = "is_sound_feed_enable";
    public static String VIBRATION_DB_KEY = "is_vibration_feed_enable";

    KvDBShim kvDBShim;

    public FeedbackUtil(Context context) {
        // Setup vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vibratorManager =
                    (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vibrator = vibratorManager.getDefaultVibrator();
        } else {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }

        //kvdb
        kvDBShim = new KvDBShim(context);

        // Setup SoundPool for fast sound effects
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        // Load sounds from res/raw
        correctSound = soundPool.load(context, R.raw.correct, 1);
        wrongSound = soundPool.load(context, R.raw.wrong, 1);
    }

    /** Call when user gives correct answer */
    public void correct() {
        vibrate(150); // short pulse
        playSound(correctSound);
    }

    /** Call when user gives wrong answer */
    public void wrong() {
        vibratePattern(new long[]{0, 100, 50, 100}); // pattern
        playSound(wrongSound);
    }

    /** Helper: Vibrate once */
    private void vibrate(long durationMs) {
        if(!isVibrationEnabled())return;
        if (vibrator == null || !vibrator.hasVibrator()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(durationMs);
        }
    }

    /** Helper: Vibrate with pattern */
    private void vibratePattern(long[] pattern) {
        if(!isVibrationEnabled())return;
        if (vibrator == null || !vibrator.hasVibrator()) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect effect = VibrationEffect.createWaveform(pattern, -1);
            vibrator.vibrate(effect);
        } else {
            vibrator.vibrate(pattern, -1);
        }
    }

    /** Helper: Play sound */
    private void playSound(int soundId) {
        if(!isSoundEnabled())return;
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f);
    }

    /** Call in Activity.onDestroy() to free resources */
    public void release() {
        soundPool.release();
    }

    public boolean isSoundEnabled(){
        if(kvDBShim.isKeyExist(SOUND_DB_KEY)){
            return kvDBShim.getVal(SOUND_DB_KEY).equals("1");
        }
        return true;
    }

    public boolean isVibrationEnabled(){
        if(kvDBShim.isKeyExist(VIBRATION_DB_KEY)){
            return kvDBShim.getVal(VIBRATION_DB_KEY).equals("1");
        }
        return true;
    }

}
