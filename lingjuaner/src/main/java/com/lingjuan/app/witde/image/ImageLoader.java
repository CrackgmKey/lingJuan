package com.lingjuan.app.witde.image;

import android.widget.ImageView;

/**
 * 抽象接口
 * .
 * Create by YuanDong Qiao
 * Create on 2016/11/15  11:17
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */

public interface ImageLoader {

    /**
     * 下载图片的方法
     */
    void loadImage(String imageUrl, ImageView imageView);


    /**
     * 可现实加载中图片和错误图片的方法
     * @param imageUrl
     * @param imageView
     * @param defaultResId
     * @param errorId
     */
    void loadImage(String imageUrl, ImageView imageView, int defaultResId, int errorId);

}
