package com.lingjuan.app.uitls;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Duwenjian {
	
	public static String getString(Context context,String str){
		try {
			InputStream is = context.getAssets().open(str);
			byte [] bytes = new byte [1024];
			int a;
			StringBuffer sb = new StringBuffer();
			while ((a=is.read(bytes))!= -1) {
				sb.append(new String(bytes,0,a));
			}
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
}
