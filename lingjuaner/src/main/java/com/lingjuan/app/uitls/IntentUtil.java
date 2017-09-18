package com.lingjuan.app.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:27
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class IntentUtil {

	public static void startActivity(Context context,Class<?> cls){
		context.startActivity(new Intent(context, cls));
	}

	public static void startActivity(Context context,Intent intent){
		context.startActivity(intent);
	}
	
	public static void startActivity(Context context,Class<?> cls,Intent intent){
		context.startActivity(intent.setClass(context, cls));
	}
	
	public static void startActivityForResult(Activity activity,Intent intent,int requestCode){
		activity.startActivityForResult(intent, requestCode);
	}
	


	
	/**
	 * @Description: 返回主页
	 * @param activity
	 * @param cls
	 */
	public static void goHome(Activity activity,Class<?> cls){
		Intent intent=new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(activity, cls, intent);
		activity.finish();
	}
	
}
