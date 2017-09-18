package com.lingjuan.app.uitls;

import android.util.Log;


/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/21  14:16
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class L {

	//标签
	private static final String TAG = "--main--";

	//Log输出所在类
	private static String className;

	//Log输出所在方法
	private static String methodName;

	//Log输出所行号
	private static int lineNumber;

	/**
	 * 获取输出所在位置的信息className methodName lineNumber
	 * @param elements
	 */
	private static void getDetail(StackTraceElement[] elements){
		className = elements[1].getFileName().split("\\.")[0];
		methodName = elements[1].getMethodName();
		lineNumber = elements[1].getLineNumber();
	}

	/**
	 * 创建Log输出的基本信息
	 * @param log
	 * @return
	 */
	private static String createLog(String log){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[ ");
		buffer.append(className);
		buffer.append(".java  ");
		buffer.append(methodName);
		buffer.append("()");
		buffer.append("  line:");
		buffer.append(lineNumber);
		buffer.append(" ] -------->>");
		buffer.append(log);
		return buffer.toString();
	}

	public static void v(Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.v(TAG, createLog(message.toString()));
		}
	}

	public static void i(Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.i(TAG, createLog(message.toString()));
		}
	}

	public static void d(Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.d(TAG, createLog(message.toString()));
		}
	}

	public static void w(Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.w(TAG, createLog(message.toString()));
		}
	}

	public static void e(Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.e(TAG, createLog(message.toString()));
		}
	}

	//自定义TAG
	public static void v(String tag, Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.v(tag, createLog(message.toString()));
		}
	}

	public static void i(String tag, Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.i(tag, createLog(message.toString()));
		}
	}

	public static void d(String tag, Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.d(tag, createLog(message.toString()));
		}
	}

	public static void w(String tag, Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.w(tag, createLog(message.toString()));
		}
	}

	public static void e(String tag, Object message){
		if (CoreConstants.IS_DEBUG) {
			getDetail(new Throwable().getStackTrace());
			Log.e(tag, createLog(message.toString()));
		}
	}

}
