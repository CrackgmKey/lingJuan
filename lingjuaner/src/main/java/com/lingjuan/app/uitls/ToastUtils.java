package com.lingjuan.app.uitls;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lingjuan.app.R;


/**
 * Created by Administrator on 2017-05-02.
 */

public class ToastUtils {

    private static Toast toast;


    public static void showToast(final Context context, final String message, final int duration) {
        toast = Toast.makeText(context.getApplicationContext(), message, duration);
        toast.show();
    }
    public static void showIconToast(Context context, String textId, int iconId, int colorId) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast, null);
        ((TextView) layout).setText(textId);
        ((TextView) layout).setTextColor(context.getResources().getColor(colorId));
        ((TextView) layout).setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showLongToast(final Context context, final String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showShortToast(final Context context, final String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }



    private static MCCustomToast centerToast;
    private static Handler mHandler;

    private static Handler getHandler() {
        if (ToastUtils.mHandler == null) {
            ToastUtils.mHandler = new Handler(Looper.getMainLooper());
        }

        return ToastUtils.mHandler;
    }

    public static void showToastInCenter(final Context context, final int type, final String info, final int druation) {
        ToastUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (getCenterToast() == null) {
                    setCenterToast(new MCCustomToast(context));
                }
                ToastUtils.getCenterToast().setType(type);
                ToastUtils.getCenterToast().setText(info);
                ToastUtils.getCenterToast().setDuration(druation);
                ToastUtils.getCenterToast().show();
            }
        });
    }

    static MCCustomToast getCenterToast() {
        return ToastUtils.centerToast;
    }

    static void setCenterToast(MCCustomToast centerToast) {
        ToastUtils.centerToast = centerToast;
    }

}
