package com.lingjuan.app.witde.image;

/**
 * 图片加载工厂
 * todo  获取图片加载实现类的对象
 * .
 * Create by YuanDong Qiao
 * Create on 2016/11/15  11:22
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */

public class ImageLoaderFactory {

    private static ImageLoader imageLoader;


    private ImageLoaderFactory(){}


    /**
     * 单例提供 ImageLoader对象
     * @return
     */
    public static ImageLoader getImageLoader(){
        if(imageLoader == null){
            synchronized (ImageLoaderFactory.class){
                if(imageLoader == null){
                    //使用 UniversalImageLoader
//                    imageLoader = new UniversalImageLoader();
//                    imageLoader = new CustomImageLoader();
//                    imageLoader = new VolleyImageLoader();

                    imageLoader = new PicassoImageLoader();
                }
            }
        }
        return imageLoader;
    }



}
