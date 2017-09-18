package com.lingjuan.app.api;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sks on 2017/8/22.
 */

public class Iben {
    //欢迎页加载数据的缓存
    public static  ArrayList<Map<String,Object>> mapArrayList;
    //欢迎页加载数据的缓存
    public static  ArrayList<String> mRenSou;
    //首页轮播数据的缓存
    public static  ArrayList<String> mLunBo;
    //轮播图对应的ID的缓存
    public static  ArrayList<String> mLunId = new ArrayList<>();
    //优惠券的PID
    public static  String YURL = "0";
    //领红包的客服QQ
    public static  String QQ = "0";
    //我的PID
//mm_111291686_36082353_128534832
    /**
     * 拼接数据
     * @param yId
     * @param sId
     * @return
     */
    public static String getUrl(String yId,String sId,String pid){
        System.out.println("=======我拼接时候获取到的PID是:"+pid);
        return "https://uland.taobao.com/coupon/edetail?activityId="+yId+"&pid="+pid+"&itemId="+sId+"&src=qtka_wxxt&dx=1";
    }

    public static  String NameId;
}
