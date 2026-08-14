package com.offlinew.practica.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    This handles all the permissions required to function the application
    It requests all required permissions inside constructor.
    Public APIs of this class
        1. constructor(Context)
        2. isAllPermissionAllowed()
 */
public class PermissionHandler {

    public static final int GENERAL_PERMISSION_REQUEST_CODE = 142;
    Context context=null;
    permissionRequestBuildListener listener = null;
    public interface permissionRequestBuildListener{
        void onStateChange(int state);
    }

    private boolean isAllowed(String Permission){
        return ContextCompat.checkSelfPermission(context, Permission) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPerm(String[] permissions,int permission_code){
        ActivityCompat.requestPermissions((Activity) context,permissions, permission_code);
    }

    public boolean requestMissingPermission(){
        List<String> perms = new ArrayList<>();

        if(!checkWriteToExternalPermission()){
            List<String> m_perm = new ArrayList<>(Arrays.asList(writeToExternalPermission()));
            perms.addAll(m_perm);
        }

        if(!checkNotificationPermission()){
            List<String> m_perm = new ArrayList<>(Arrays.asList(notificationPermissions()));
            perms.addAll(m_perm);
        }


        if(perms.size() > 0) {
            requestPerm(perms.toArray(new String[0]), GENERAL_PERMISSION_REQUEST_CODE);
            return true;
        }
        if(listener != null){
            listener.onStateChange(-1);
        }
        return false;
    }

    public boolean requestNotificationPermission(){
        List<String> perms = new ArrayList<>();


        if(!checkNotificationPermission()){
            List<String> m_perm = new ArrayList<>(Arrays.asList(notificationPermissions()));
            perms.addAll(m_perm);
        }

        if(perms.size() > 0) {
            requestPerm(perms.toArray(new String[0]), GENERAL_PERMISSION_REQUEST_CODE);
            return true;
        }
        if(listener != null){
            listener.onStateChange(-1);
        }
        return false;
    }

    public boolean requestWriteToExternalPermission(){
        List<String> perms = new ArrayList<>();

        if(!checkWriteToExternalPermission()){
            List<String> m_perm = new ArrayList<>(Arrays.asList(writeToExternalPermission()));
            perms.addAll(m_perm);
        }

        if(perms.size() > 0) {
            requestPerm(perms.toArray(new String[0]), GENERAL_PERMISSION_REQUEST_CODE);
            return true;
        }
        if(listener != null){
            listener.onStateChange(-1);
        }
        return false;
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted. You can now access external storage.
                init_after_permission();
            } else {
                // Permission denied. Handle this case (e.g., show a message or request again later).
                Toast.makeText(getApplicationContext(),"App will not work without this permission!",Toast.LENGTH_SHORT).show();
                check_for_permission(context);
            }
        }
    }
     */
    public PermissionHandler(Context context, permissionRequestBuildListener listener){
        this.context=context;
        this.listener = listener;
    }


    private boolean checkWriteToExternalPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            return true;
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return isAllowed(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        return true;
    }
    private String[] writeToExternalPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }
        return new String[]{};
    }

    private boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return isAllowed(Manifest.permission.POST_NOTIFICATIONS);
        }
        return true;
    }

    private String[] notificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return new String[]{Manifest.permission.POST_NOTIFICATIONS};
        }
        return new String[]{};
    }

}
