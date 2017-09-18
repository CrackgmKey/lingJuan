package com.lingjuan.app.uitls;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:27
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class KeyBoardUtil {
	/**
	 * 打开软键盘
	 * @param mContext 上下文
	 * @param mEditText 输入框
	 */
	public static void openKeybord(Context mContext,EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * @param mContext 上下文
	 * @param mEditText 输入框
	 */
	public static void closeKeybord(Context mContext,EditText mEditText) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	// 隐藏虚拟键盘
	public static void HideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	// 显示虚拟键盘
	public static void ShowKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
	}

	// 强制显示或者关闭系统键盘
	public static void KeyBoard(final EditText txtSearchKey, final String status) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager m = (InputMethodManager) txtSearchKey
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				if (status.equals("open")) {
					m.showSoftInput(txtSearchKey,
							InputMethodManager.SHOW_FORCED);
				} else {
					m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
				}
			}
		}, 300);
	}

	// 通过定时器强制隐藏虚拟键盘
	public static void TimerHideKeyboard(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							0);
				}
			}
		}, 10);
	}

	// 输入法是否显示着
	public static boolean KeyBoard(EditText edittext) {
		boolean bool = false;
		InputMethodManager imm = (InputMethodManager) edittext.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			bool = true;
		}
		return bool;
	}
}
