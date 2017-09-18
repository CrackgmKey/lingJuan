package com.lingjuan.app.uitls;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.lingjuan.app.R;


/**
 * 网络工具类
 */
public class NetWorkUtils {

    //是否有网络，true有网络，false无网络    todo 默认应该为 false
    public static boolean NETWORK = false;

    /**
     * 初始化网络状态
     * @param context
     */
    public static void initNetStatus(Context context){
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            NETWORK=true;
            L.e("当前网络名称：" + networkInfo.getTypeName());
        } else {
            NETWORK=false;
            L.e("没有可用网络");

        }
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle("请先连接网络")            //
                .setMessage("当前无网络").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到系统的网络设置界面
                Intent intent = null;
                // 先判断当前系统版本
                if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                    intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                }
                context.startActivity(intent);

            }
        }).setNegativeButton("知道了", null).show();
    }

}
