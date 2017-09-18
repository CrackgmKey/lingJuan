package com.lingjuan.app.uitls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2017-06-20.
 * 弹窗
 */

public class DialogUtil {

    public static void showComfirmDialog(final Context context, String msg, DialogInterface.OnClickListener confirmListener) {

        final CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle("发现新版本");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", confirmListener);
        builder.setNegativeButton("取消", confirmListener);
        builder.create().show();
    }

    public static void showMsgDialog(Context context, String msg, DialogInterface.OnClickListener confirmListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("发现新版本");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", confirmListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
