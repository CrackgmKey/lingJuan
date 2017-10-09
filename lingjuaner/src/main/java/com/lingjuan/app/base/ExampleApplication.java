package com.lingjuan.app.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.trade.common.adapter.ut.AlibcUserTracker;
import com.lingjuan.app.uitls.Logger;
import com.lingjuan.app.uitls.NetWorkStateService;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tauth.Tencent;
import com.ut.mini.internal.UTTeamWork;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class ExampleApplication extends Application {
    public static boolean IsLogin = false;
    public static boolean IsLogind = false;
    public static Context context ;
    public static Context AppContent ;
    public static final String BORDA_ID = "com.lingjuan.app";
    private static final String TAG = "JIGUANG-Example";
    public static  Tencent mTencent;
    private static final String APP_ID = "1106367932";//官方获取的APPID
    @Override
    public void onCreate() {    	     
    	 Logger.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();
        context = this;
         JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
         JPushInterface.init(this);     		// 初始化 JPush
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,this.getApplicationContext());
        //腾讯Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "f75f6ba801", false);
        //监听网络启动
        startService(new Intent(this, NetWorkStateService.class));
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //Toast.makeText(ExampleApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();
                Map utMap = new HashMap<>();
                utMap.put("debug_api_url","http://muvp.alibaba-inc.com/online/UploadRecords.do");
                utMap.put("debug_key","baichuan_sdk_utDetection");
                UTTeamWork.getInstance().turnOnRealTimeDebug(utMap);
                AlibcUserTracker.getInstance().sendInitHit4DAU("19","3.1.1.100");
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(ExampleApplication.this, "初始化失败,错误码="+code+" / 错误消息="+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
