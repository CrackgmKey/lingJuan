package com.lingjuan.app.witde;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.lingjuan.app.activity.MainActivity;
import com.lingjuan.app.base.ExampleApplication;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;

/**
 * Created by sks on 2017/8/27.
 */

public class TengQue {
    public static int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    public static int mExtarFlag = 0x00;
    public static void setFenXiang(){




        final Bundle params = new Bundle();
        if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "您的朋友向您推举了一款应用");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "www.baidu.com");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "下载APP,天天双11,还在等什么,快来加入我们吧");
        }
        if (shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, "http://cuimg.zuyushop.com:8013/cuxiaoPic/20166/2016060013352290551.jpg");
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://cuimg.zuyushop.com:8013/cuxiaoPic/20166/2016060013352290551.jpg");
        }
        params.putString(shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE ? QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL
                : QQShare.SHARE_TO_QQ_IMAGE_URL, "http://cuimg.zuyushop.com:8013/cuxiaoPic/20166/2016060013352290551.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "领卷APP,您的省钱专家");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);
        if (shareType == QQShare.SHARE_TO_QQ_TYPE_AUDIO) {
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, "AA啊啊");
        }

        doShareToQQ(params);

    }


    public static void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
               // ExampleApplication.mTencent.shareToQQ(MainActivity.this, params, MainActivity.getIU());
            }
        });
    }
}
