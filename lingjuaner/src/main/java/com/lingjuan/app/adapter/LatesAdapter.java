package com.lingjuan.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lingjuan.app.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sks on 2017/8/20.
 */

public class LatesAdapter extends BaseAdapter {
    private ArrayList<Map<String,Object>> mapArrayList;
    private Context context;

    public LatesAdapter(ArrayList<Map<String, Object>> mapArrayList, Context context) {
        this.mapArrayList = mapArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mapArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mapArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if(convertView == null){
            viewHoder = new ViewHoder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ist_item,parent,false);
            viewHoder.title = (TextView) convertView.findViewById(R.id.sp_title);
            viewHoder.xiaoliang = (TextView) convertView.findViewById(R.id.sp_xiaoliang);
            viewHoder.pingfen = (TextView) convertView.findViewById(R.id.sp_pingfen);
            viewHoder.juan = (TextView) convertView.findViewById(R.id.sp_juan);
            viewHoder.yuanjia = (TextView) convertView.findViewById(R.id.sp_yuanjia);
            viewHoder.xianjia = (TextView) convertView.findViewById(R.id.sp_xianjia);
            viewHoder.sppic = (ImageView) convertView.findViewById(R.id.image_item);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        viewHoder.title.setText((String)mapArrayList.get(position).get("goods_name"));
        viewHoder.xiaoliang.setText("月销量:"+(String)mapArrayList.get(position).get("sales"));
        viewHoder.juan.setText((String)mapArrayList.get(position).get("price_coupons")+"元");
        viewHoder.yuanjia.setText((String)mapArrayList.get(position).get("price"));
        viewHoder.xianjia.setText((String)mapArrayList.get(position).get("price_after_coupons"));
        String PicUrl = (String) mapArrayList.get(position).get("goods_pic");
        if(PicUrl.startsWith("//")){
            PicUrl = "https:"+PicUrl;
        }
        Glide.with(context)
                .load(PicUrl)
                .placeholder(R.mipmap.ic_loading_large)
                .error(R.mipmap.ic_loading_large)
                .into(viewHoder.sppic);
        return convertView;
    }



    class ViewHoder{
        TextView title,xiaoliang,pingfen,juan,yuanjia,xianjia;
        ImageView sppic;
    }
}
