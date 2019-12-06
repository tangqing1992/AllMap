package com.tang.allmap.permissionmanger;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    private static final boolean debuge = true;
    private static final String tag = "PermissionManager";
    public static String[] permissions = new String[]{
            /*允许程序打开网络套接字*/
            Manifest.permission.INTERNET,
            /*允许程序设置内置sd卡的写权限*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            /*允许程序获取网络状态*/
            Manifest.permission.ACCESS_NETWORK_STATE,
            /*允许程序访问WiFi网络信息*/
            Manifest.permission.ACCESS_WIFI_STATE,
            /*允许程序读写手机状态和身份*/
            Manifest.permission.READ_PHONE_STATE,
            /*允许程序访问CellID或WiFi热点来获取粗略的位置*/
            Manifest.permission.ACCESS_COARSE_LOCATION,
            /*用于访问GPS定位*/
            Manifest.permission.ACCESS_FINE_LOCATION,
            /*用于获取wifi的获取权限，wifi信息会用来进行网络定位*/
            Manifest.permission.CHANGE_WIFI_STATE,
            /*用于申请调用A-GPS模块*/
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,

    };

    public static void initPermission(Activity activity, String[] permissions, int indexo){

        if (debuge) Log.e(tag,permissions.length+"----initPermission-----indexo="+indexo);
        if (ContextCompat.checkSelfPermission(activity,permissions[indexo])!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,permissions,indexo);
        }
        else {
            indexo++;
            if (indexo>=permissions.length)
                return;
            initPermission(activity,permissions,indexo);
        }

    }
}
