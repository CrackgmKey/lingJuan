package com.lingjuan.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lingjuan.app.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/12.
 */

public class ListPicAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> picList;

    public ListPicAdapter(Context context, ArrayList<String> picList) {
        this.context = context;
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoHore videoHore;
        if(convertView == null){
            videoHore = new VideoHore();
            convertView = LayoutInflater.from(context).inflate(R.layout.listpic_adapter,parent,false);
            videoHore.imageView = (ImageView) convertView.findViewById(R.id.butt_pic);
            convertView.setTag(videoHore);
        }else {
            videoHore = (VideoHore) convertView.getTag();
        }

        //加载图片
        Glide.with(context)
                .load(picList.get(position))
                .placeholder(R.mipmap.ic_loading_large)
                .error(R.mipmap.ic_loading_large)
                .into(videoHore.imageView);

        return convertView;
    }


    class VideoHore{
        ImageView imageView;
    }
}
