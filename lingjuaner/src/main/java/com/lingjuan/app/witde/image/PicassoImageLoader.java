package com.lingjuan.app.witde.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lingjuan.app.R;


/**
 * Stay  hungry , Stay  foolish
 * .
 * Create by YuanDong Qiao
 * Create on 2016/11/21  15:18
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */

public class PicassoImageLoader implements ImageLoader {

    /**
     * 使用picasso进行图片加载
     * @param imageUrl
     * @param imageView
     */
    @Override
    public void loadImage(String imageUrl, ImageView imageView) {

        loadImage(imageUrl,imageView, R.mipmap.ic_loading_large,R.mipmap.ic_loading_large);
    }

    @Override
    public void loadImage(String imageUrl, ImageView imageView, int defaultResId, int errorId) {
        //Picasso 加载图片
        Glide.with(imageView.getContext())
        .load(imageUrl)
        .placeholder(defaultResId)
        .error(errorId)
        .into(imageView);
    }

    /**
     *  1.
     //        Picasso.with(this).load("").into();
     // 2.
     //        Picasso.with(context)
     //                .load(url)
     //                .placeholder(R.drawable.user_placeholder)
     //                .error(R.drawable.user_placeholder_error)
     //                .into(imageView);
     // 3.
     //Picasso.with(context).load(R.drawable.landing_screen).into(imageView1);
     //
     // 4.
     //        Picasso.with(context)
     //                .load(url)
     //                .resize(50, 50)
     //                .centerCrop()
     //                .into(imageView);
     */
}
