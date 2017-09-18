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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sks on 2017/8/20.
 */

public class BaoYouAdapter extends BaseAdapter {
    private ArrayList<Map<String,Object>> mapArrayList;
    private Context context;

    public BaoYouAdapter(ArrayList<Map<String, Object>> mapArrayList, Context context) {
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
        double yuanjia = (double) mapArrayList.get(position).get("goods_price");
        double xianjia = (double) mapArrayList.get(position).get("coupon_price");
        viewHoder.title.setText((String)mapArrayList.get(position).get("goods_short_title"));
        DecimalFormat df   = new DecimalFormat("######0.00");
        //销量
        viewHoder.xiaoliang.setText("月销量:"+ mapArrayList.get(position).get("goods_sales"));
        //现价
        viewHoder.xianjia.setText(df.format(yuanjia-xianjia)+"");
        //优惠券
        viewHoder.juan.setText(String.valueOf(mapArrayList.get(position).get("coupon_price"))+"元");
        //原价
        viewHoder.yuanjia.setText(""+mapArrayList.get(position).get("goods_price")+"0");
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
