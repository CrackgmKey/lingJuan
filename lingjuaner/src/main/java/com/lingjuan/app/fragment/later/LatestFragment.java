package com.lingjuan.app.fragment.later;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lingjuan.app.R;
import com.lingjuan.app.activity.CategoricalActivity;
import com.lingjuan.app.activity.MainActivity;
import com.lingjuan.app.activity.PurchaseActivity;
import com.lingjuan.app.activity.SearchActivity;
import com.lingjuan.app.adapter.LatesAdapter;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.api.Iben;
import com.lingjuan.app.base.BaseFrament;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.uitls.DialogUtil;
import com.lingjuan.app.uitls.HttpMethods;
import com.lingjuan.app.uitls.SharedPreferenceUtil;
import com.lingjuan.app.uitls.ToastUtils;
import com.lingjuan.app.witde.NetworkImageHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * Created by sks on 2017/8/19.
 * time 2017年8月19日18:50:10
 * titile 推举模块
 */

public class LatestFragment extends BaseFrament implements View.OnClickListener {
    private PullToRefreshListView mPullRefreshGridView;
    private ListView mGridView;
    private int qishi;
    private View Heaner;
    private LinearLayout buju;
    private ArrayList<Map<String,Object>> mapArrayList;
    private ArrayList<Map<String,Object>> DanArrayList;
    private YouPinAdapter latesAdapter;
    private ConvenientBanner convenientBanner;
    private int paths = 1;
    private int [] ints = {R.mipmap.ic_page_indicator,R.mipmap.ic_page_indicator_focused};
    private String pathUrl ;
    private CircleImageView tu1,tu2,tu3,tu4,tu5,tu6,tu7,tu8,tu9,tu10;
    private ProgressDialog dialogs;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dialogs.setProgress(msg.what);
            super.handleMessage(msg);
        }
    };
    private KProgressHUD hud;
    private  AlertDialog adDialog;

    /**
     * Fragment传值
     *
     * @param type
     * @return
     */
    public static LatestFragment newIntes(int type) {
        LatestFragment df = new LatestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", type);
        df.setArguments(bundle);
        return df;
    }

    @Override
    protected void init(View view) {
        dialogs = new ProgressDialog(getActivity());
        buju = (LinearLayout) view.findViewById(R.id.buju);
        Heaner = LayoutInflater.from(getActivity()).inflate(R.layout.listview_addhoe,null);
        mPullRefreshGridView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
        convenientBanner = (ConvenientBanner) Heaner.findViewById(R.id.iamges);
        mGridView.addHeaderView(Heaner);
        addViewinit(Heaner);
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullRefreshGridView.onRefreshComplete();
                                Toast.makeText(ExampleApplication.context, "已经到底了", Toast.LENGTH_SHORT).show();
                            }
                        },6000);
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                paths = paths+1;
                getErCiRequestdata(paths);
            }

        });

        List<String> urls = new ArrayList<>();
        urls.add("https://img.alicdn.com/imgextra/i4/2508158775/TB2jD0adosIL1JjSZPiXXXKmpXa_!!2508158775.jpg");
        urls.add("https://img.alicdn.com/imgextra/i1/2508158775/TB2r6aaXrKFJuJjSszgXXXVnXXa_!!2508158775.jpg");
        urls.add("https://img.alicdn.com/imgextra/i3/2508158775/TB2BmewdEEIL1JjSZFFXXc5kVXa_!!2508158775.jpg");
        urls.add("https://img.alicdn.com/imgextra/i2/2508158775/TB2BDsXchsIL1JjSZFqXXceCpXa_!!2508158775.jpg");
        urls.add("https://img.alicdn.com/imgextra/i3/2508158775/TB2z2RgabsTMeJjSszgXXacpFXa_!!2508158775.jpg");
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.iamges);
        convenientBanner.setPointViewVisible(true);
        convenientBanner.startTurning(7000);
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.setPageIndicator(ints);


        //网络地址
        //网络加载例子
        if(Iben.mLunBo != null){
            convenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new NetworkImageHolderView();
                }
            },Iben.mLunBo);
        }else {
            convenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new NetworkImageHolderView();
                }
            },urls);
        }
        //点击事件
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //跳转商品
                if(DanArrayList != null){
                    DanArrayList.clear();
                }
                getDanPinSouSUo(position);
            }
        });
        if(Iben.mapArrayList != null){
            //加载数据
            mapArrayList = Iben.mapArrayList;
            buju.setVisibility(View.GONE);
            latesAdapter = new YouPinAdapter(mapArrayList,getActivity());
            mGridView.setAdapter(latesAdapter);
            Iben.mapArrayList = null;
            System.gc();
        }else {
            getRequestdata();
        }

        //获取
        StartGengXin();

        //获取PID
        //getYaoqingM();
        //设置邀请码
        String pid = Iben.YURL = (String) SharedPreferenceUtil.get(getActivity(),"name","0");
        if(pid.equals("0")){
            Diods();
        }else {
            getYaoqingM(pid);
        }
        /**
         * 跳转
         */
        /**
         * 跳转
         */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PurchaseActivity.class);
                intent.putExtra("list",mapArrayList);
                intent.putExtra("posd", position);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
    }

    /**
     * 绑定布局
     * @return
     */
    @Override
    protected int getLayout() {
        return R.layout.framgnet_shouye;
    }

    /**
     * 加载更多
     * @param path
     */
    private void getErCiRequestdata(int path) {
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getJiuJIud(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               // Toast.makeText(getActivity(), "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                buju.setVisibility(View.GONE);
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                //mapArrayList = getArrJson(s);
                mapArrayList.addAll(getArrJson(s));
                latesAdapter.setMapArrayList(mapArrayList);
                latesAdapter.notifyDataSetChanged();
                mPullRefreshGridView.onRefreshComplete();
                //Toast.makeText(getActivity(), "13到底了", Toast.LENGTH_SHORT).show();
            }
        }, path,0);
    }


    /**
     * 第一次请求网络数据
     */
    private void getRequestdata() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getJiuJIud(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                hud.dismiss();
                //Toast.makeText(getActivity(), "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                buju.setVisibility(View.GONE);
                hud.dismiss();
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                mapArrayList = getArrJson(s);
                latesAdapter = new YouPinAdapter(mapArrayList, getActivity());
                mGridView.setAdapter(latesAdapter);
            }
        }, 1,0);
    }

    /**
     * 解析json
     * @param string 要解析的字符串
     * @return
     */
    private ArrayList<Map<String, Object>> getArrJson(String string) {
        ArrayList<Map<String, Object>> List = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject jsonArray = jsonObject.getJSONObject("data");
            JSONArray listjsonarrya = jsonArray.getJSONArray("list");
            for (int i = 0; i < listjsonarrya.length(); i++) {
                JSONObject jsonObject1 = listjsonarrya.getJSONObject(i);
                Map<String, Object> map = new HashMap<>();
                map.put("goods_id", jsonObject1.getString("goods_id"));//ID
                map.put("goods_pic", jsonObject1.getString("goods_pic"));//商品名字
                map.put("goods_title", jsonObject1.getString("goods_title"));//商品ID
                map.put("goods_short_title", jsonObject1.getString("goods_short_title"));//分类ID
                map.put("goods_cat", jsonObject1.getString("goods_cat"));//分类名字
                map.put("goods_price", jsonObject1.getDouble("goods_price"));//商品URL
                map.put("goods_sales", jsonObject1.getString("goods_sales"));//商品图片
                map.put("goods_introduce", jsonObject1.getString("goods_introduce"));//在线售价
                map.put("seller_id", jsonObject1.getString("seller_id"));//劵后售价
                map.put("coupon_id", jsonObject1.getString("coupon_id"));//优惠券价格
                map.put("coupon_price", jsonObject1.getDouble("coupon_price"));//月销量
                List.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return List;
    }


    /**
     * 获取版本信息
     */
    private void StartGengXin(){
        HttpMethods.BASE_URL = "http://www.aiboyy.pw/";
        HttpMethods.getInstance().getGengXin(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ExampleApplication.context, "服务器发送错误!,请联系管理员", Toast.LENGTH_SHORT).show();
                //getActivity().finish();
            }

            @Override
            public void onNext(String s) {
                try {
                    //qidong
                    getLists(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 解析JSON
     * @param response
     * @return
     * @throws Exception
     */
    public void getLists(String response) throws Exception {
        try {
            JSONArray json = new JSONArray(response);;
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonObject = json.getJSONObject(i);
                String banben = jsonObject.getString("banebn");
                String gengxin = jsonObject.getString("gengxin");
                final String url = jsonObject.getString("url");
                if(!getVersionName().equals(banben)){
                    Diods(gengxin,url);
    /*                DialogUtil.showMsgDialog(getActivity(), gengxin, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ad.dismiss();
                            //下载更新
                            dialogs.setCanceledOnTouchOutside(false);
                            dialogs.setTitle("正在下载");
                            dialogs.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            dialogs.setCancelable(false);
                            dialogs.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getURL(url);
                                }
                            }).start();
                        }
                    });*/
                }else {
                    Toast.makeText(getActivity(), "当前版本为最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 获取版本号
     * @return
     */
    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getActivity().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 启动应用安装。
     */
    private void setupApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //"file：//"+文件路径。
        Uri uri = Uri.parse("file://"+ pathUrl);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 启动更新
     * @param GengXin
     */
    private void getURL(String GengXin){
        try {
            //构建URL地址
            URL url = new URL(GengXin);
            try {
                //打开打开打开
                HttpURLConnection hcont = (HttpURLConnection) url.openConnection();
                //建立实际链接
                hcont.connect();
                //获取输入流内容
                InputStream is = hcont.getInputStream();
                //获取文件长度
                int ddddd =hcont.getContentLength();
                //为进度条赋值
                dialogs.setMax(ddddd);
                //手机存储地址
                pathUrl = getActivity().getExternalCacheDir().getPath()+"/lingjuan.apk";
                //写入文件
                OutputStream os = new FileOutputStream(pathUrl);
                int length;
                int lengtsh = 0;
                byte [] bytes = new byte[1024];
                while ((length = is.read(bytes))!= -1){
                    os.write(bytes,0,length);
                    //获取当前进度值
                    lengtsh+=length;
                    //把值传给handler
                    handler.sendEmptyMessage(lengtsh);
                }
                //关闭流
                is.close();
                os.close();
                os.flush();
                //dialogs.dismiss();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                        setupApk();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出布局
     * @param yirme
     */
    private  void Diods(String name, final String dizhi){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_two_layout, null);
        TextView bt = (TextView) view.findViewById(R.id.tv_msg);
        Button but_id = (Button) view.findViewById(R.id.btn_update);
        bt.setText(name);
        builder.setView(view);
        final AlertDialog ad = builder.create();
        ad.show();
        ad.setCanceledOnTouchOutside(false);
        ad.setCancelable(false);
        //取消更新
        //确定更新
        but_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                dialogs.setCanceledOnTouchOutside(false);
                dialogs.setTitle("正在下载");
                dialogs.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialogs.setCancelable(false);
                dialogs.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getURL(dizhi);
                    }
                }).start();
            }
        });
    }


    /**
     * 初始化图
     * @param view
     */
   private void addViewinit(View view){
       tu1 = (CircleImageView) view.findViewById(R.id.tubiao1);
       tu2 = (CircleImageView) view.findViewById(R.id.tubiao2);
       tu3 = (CircleImageView) view.findViewById(R.id.tubiao3);
       tu4 = (CircleImageView) view.findViewById(R.id.tubiao4);
       tu5 = (CircleImageView) view.findViewById(R.id.tubiao5);
       tu6 = (CircleImageView) view.findViewById(R.id.tubiao6);
       tu7 = (CircleImageView) view.findViewById(R.id.tubiao7);
       tu8 = (CircleImageView) view.findViewById(R.id.tubiao8);
       tu9 = (CircleImageView) view.findViewById(R.id.tubiao9);
       tu10 = (CircleImageView) view.findViewById(R.id.tubiao10);

       tu1.setOnClickListener(this);
       tu2.setOnClickListener(this);
       tu3.setOnClickListener(this);
       tu4.setOnClickListener(this);
       tu5.setOnClickListener(this);
       tu6.setOnClickListener(this);
       tu7.setOnClickListener(this);
       tu8.setOnClickListener(this);
       tu9.setOnClickListener(this);
       tu10.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tubiao1:
                IntesnStrac(12);
                break;
            case R.id.tubiao2:
                IntesnStrac(10);
                break;
            case R.id.tubiao3:
                IntesnStrac(11);
                break;
            case R.id.tubiao4:
                IntesnStrac(8);
                break;
            case R.id.tubiao5:
                IntesnStrac(4);
                break;
            case R.id.tubiao6:
                IntesnStrac(3);
                break;
            case R.id.tubiao7:
                IntesnStrac(5);
                break;
            case R.id.tubiao8:
                IntesnStrac(6);
                break;
            case R.id.tubiao9:
                IntesnStrac(7);
                break;
            case R.id.tubiao10:
                IntesnStrac(2);
                break;
        }
    }


    /**
     * 跳转分类Activity
     */
    private void IntesnStrac(int type){
        Intent intent = new Intent(getActivity(), CategoricalActivity.class);
        intent.putExtra("type",type);
        getActivity().startActivity(intent);
    }


    /**
     * 第一次请求网络数据
     */
    private void getDanPinSouSUo(int position) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        HttpMethods.BASE_URL = "http://api.tkjidi.com/";
        HttpMethods.getInstance().getDanGe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                hud.dismiss();
                Toast.makeText(getActivity(), "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(String s) {
                buju.setVisibility(View.GONE);
                hud.dismiss();
                DanArrayList = getDanList(s);
                //判定商品数据不为空
                if(DanArrayList.get(0).size() != 1){
                    Intent intent = new Intent(getActivity(), PurchaseActivity.class);
                    intent.putExtra("list",DanArrayList);
                    intent.putExtra("posd", 0);
                    intent.putExtra("type", 5);
                    startActivity(intent);
                }else {
                    ToastUtils.showToastInCenter(getActivity(),1,"暂时没有这个商品",Toast.LENGTH_SHORT);
                }
            }
        },Iben.mLunId.get(position));
    }

    /**
     * 解析轮播图的商品
     * @param json
     * @return
     */
    private ArrayList<Map<String,Object>> getDanList(String json){
        ArrayList<Map<String,Object>> DanArrayListds = null;
        try {
            DanArrayListds = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            Map<String,Object> map = new HashMap<>();
            String quan_id2 = jsonObject1.getString("quan_id");
            if(!quan_id2.equals("")){
                String goods_id = jsonObject1.getString("goods_id");
                String price = jsonObject1.getString("price");
                String price_after_coupons = jsonObject1.getString("price_after_coupons");
                String price_coupons = jsonObject1.getString("price_coupons");
                String pic = jsonObject1.getString("pic");
                String quan_guid_content = jsonObject1.getString("quan_guid_content");
                String quan_id = jsonObject1.getString("quan_id");
                String sales = jsonObject1.getString("sales");
                String goods_name = jsonObject1.getString("goods_name");
                map.put("goods_id",goods_id);
                map.put("price",price);
                map.put("sales",sales);
                map.put("price_after_coupons",price_after_coupons);
                map.put("price_coupons",price_coupons);
                map.put("pic",pic);
                map.put("quan_guid_content",quan_guid_content);
                map.put("quan_id",quan_id);
                map.put("goods_name",goods_name);
                DanArrayListds.add(map);
            }else {
                map.put("21313","为空");
                DanArrayListds.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return DanArrayListds;
    }


    /**
     * PID的获取
     */
    private void getYaoqingM(final String name, final AlertDialog alertDialog) {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        HttpMethods.BASE_URL = "http://www.aiboyy.pw/";
        HttpMethods.getInstance().getPid(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                alertDialog.dismiss();
                hud.dismiss();
                Iben.YURL = (String) SharedPreferenceUtil.get(getActivity(),"pid","0");
                Iben.QQ = (String) SharedPreferenceUtil.get(getActivity(),"qq","123");
                //SharedPreferenceUtil.put(getActivity(),"qq",Iben.QQ);
            }

            @Override
            public void onNext(String s) {
                hud.dismiss();
                try {
                    JSONArray jsonObject2 = new JSONArray(s);
                    JSONObject jsonObject = jsonObject2.getJSONObject(0);
                    if(!jsonObject.getString("name").equals("110") && !jsonObject.getString("cate_id").equals("110")){
                        alertDialog.dismiss();
                        Iben.YURL = jsonObject.getString("name");
                        Iben.QQ = jsonObject.getString("cate_id");
                        SharedPreferenceUtil.put(getActivity(),"pid",Iben.YURL);
                        SharedPreferenceUtil.put(getActivity(),"qq",Iben.QQ);
                        SharedPreferenceUtil.put(getActivity(),"name",name);
                        System.out.println("=======我获取到的PID是:"+Iben.YURL+",获取到的QQ是:"+Iben.QQ);
                        ToastUtils.showToastInCenter(getActivity(),2,"当日数据已更新",Toast.LENGTH_SHORT);
                    }else {
                        // getActivity().finish();
                        ToastUtils.showToastInCenter(getActivity(),1,"专属码错误请重新输入",Toast.LENGTH_SHORT);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },name);
    }


    /**
     * PID的获取
     */
    private void getYaoqingM(String name) {
      /*  hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();*/
        HttpMethods.BASE_URL = "http://www.aiboyy.pw/";
        HttpMethods.getInstance().getPid(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
               // hud.dismiss();
                Iben.YURL = (String) SharedPreferenceUtil.get(getActivity(),"pid","0");
                Iben.QQ = (String) SharedPreferenceUtil.get(getActivity(),"qq","123");
                //SharedPreferenceUtil.put(getActivity(),"qq",Iben.QQ);
            }

            @Override
            public void onNext(String s) {
               // hud.dismiss();
                try {
                    JSONArray jsonObject2 = new JSONArray(s);
                    JSONObject jsonObject = jsonObject2.getJSONObject(0);
                    if(!jsonObject.getString("name").equals("110") && !jsonObject.getString("cate_id").equals("110")){
                        Iben.YURL = jsonObject.getString("name");
                        Iben.QQ = jsonObject.getString("cate_id");
                        SharedPreferenceUtil.put(getActivity(),"pid",Iben.YURL);
                        SharedPreferenceUtil.put(getActivity(),"qq",Iben.QQ);
                        System.out.println("=======我获取到的PID是:"+Iben.YURL+",获取到的QQ是:"+Iben.QQ);
                        ToastUtils.showToastInCenter(getActivity(),2,"当日数据已更新",Toast.LENGTH_SHORT);
                    }else {
                       // getActivity().finish();
                        ToastUtils.showToastInCenter(getActivity(),1,"请重新输入",Toast.LENGTH_SHORT);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },name);
    }


    /**
     * 弹出布局
     */
    private void Diods(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_share,null);
        Button bt = (Button) view.findViewById(R.id.dialog_btn_pay);
        final EditText editName = (EditText) view.findViewById(R.id.et_registerPhone);
        builder.setView(view);
        adDialog = builder.create();
        adDialog.show();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = editName.getText().toString();
                if(!Name.equals("") && Name.trim().length() != 0 ){
                    getYaoqingM(Name,adDialog);
                }else {
                    Toast.makeText(getActivity(), "请输入内容在点击,谢谢", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}