package com.lingjuan.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lingjuan.app.R;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.api.Iben;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.uitls.HttpMethods;
import com.lingjuan.app.uitls.KeyBoardUtil;
import com.lingjuan.app.uitls.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import rx.Subscriber;

/**
 * Created by sks on 2017/8/31.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView mPullRefreshGridView;
    private ListView mGridView;
    private int qishi;
    private View Heaner;
    private LinearLayout buju,buju2;
    private ArrayList<Map<String,Object>> mapArrayList;
    private YouPinAdapter latesAdapter;
    private TextView te_sousuo;
    private EditText et_searchs;
    private String SouSUoNeiRong;
    private int fenleiTab = 0;
    private PopupWindow popupWindow;
    private TagContainerLayout mTagContainerLayout2,mTagContainerLayout1;
    private int paths = 1;
    private KProgressHUD hud;
    private int posd;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_sear);
        mTagContainerLayout2 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout2);
        mTagContainerLayout2.setTags(Iben.mRenSou);
        mTagContainerLayout2.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Toast.makeText(SearchActivity.this, "click-position:" + position + ", text:" + text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTagLongClick(int position, String text) {
                Toast.makeText(SearchActivity.this, "click-position:" + position + ", text:" + text,
                        Toast.LENGTH_SHORT).show();
            }
        });
        te_sousuo = (TextView) findViewById(R.id.te_sousuo);
        et_searchs = (EditText) findViewById(R.id.et_searchs);
        te_sousuo.setOnClickListener(this);
        buju = (LinearLayout) findViewById(R.id.buju);
        buju2 = (LinearLayout) findViewById(R.id.buju2);
        mPullRefreshGridView = (PullToRefreshListView) findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(SearchActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullRefreshGridView.onRefreshComplete();
                                Toast.makeText(SearchActivity.this, "别乱搞", Toast.LENGTH_SHORT).show();
                            }
                        },2000);
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(SearchActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                paths = paths+1;
                getErRequestdata(paths,SouSUoNeiRong,fenleiTab);
            }

        });
        /**
         * 跳转
         */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, PurchaseActivity.class);
                intent.putExtra("list",mapArrayList);
                intent.putExtra("posd", position);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        //getRequestdata();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sear;
    }

    @Override
    protected void init() {

    }


    /**
     * 第一次请求网络数据
     */
    private void getRequestdata(String nei,int fenlei) {

        hud = KProgressHUD.create(SearchActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                 .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        buju.setVisibility(View.GONE);
        mTagContainerLayout2.setVisibility(View.GONE);
        popupWindow.dismiss();
        backgroundAlpha(1f);
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getSOuSuo(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                hud.dismiss();
                Toast.makeText(SearchActivity.this, "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
               // Toast.makeText(SearchActivity.this, "===" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                hud.dismiss();
                mapArrayList = getArrJson(s);
                if(mapArrayList.size() == 0){
                    buju2.setVisibility(View.VISIBLE);
                    mGridView.setVisibility(View.GONE);
                }else {
                    mGridView.setVisibility(View.VISIBLE);
                    buju2.setVisibility(View.GONE);
                    latesAdapter = new YouPinAdapter(mapArrayList, SearchActivity.this);
                    mGridView.setAdapter(latesAdapter);
                }


            }
        },1, nei,fenlei);
    }


    /**
     * 第二次请求网络数据
     */
    private void getErRequestdata(int path,String nei,int fenlei) {
       if(popupWindow != null){
           if(popupWindow.isShowing()){
               popupWindow.dismiss();
           }
       }
        backgroundAlpha(1f);
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getSOuSuo(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SearchActivity.this, "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
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
            }
        },path, nei,fenlei);
    }

    /**
     * 解析json
     * @param string 要解析的字符串
     * @return s
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.te_sousuo:
                //隐藏虚拟键盘
                KeyBoardUtil.closeKeybord(SearchActivity.this,et_searchs);
                setPopupWindow(v);
                break;
            case R.id.fenlei1:
                //美食
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 6;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei2:
                //男装
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 12;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei3:
                //家电
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 8;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei4:
                //美妆
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 3;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei5:
                //鞋包
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 5;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei6:
                //母婴
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 2;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei7:
                //文体
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 7;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei8:
                //女装
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 10;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei9:
                //内衣
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 11;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei10:
                //居家
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 4;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
            case R.id.fenlei11:
                //全部
                SouSUoNeiRong = et_searchs.getText().toString();
                fenleiTab = 0;
                getRequestdata(SouSUoNeiRong,fenleiTab);
                break;
        }
    }

    /**
     * 弹出POPUT布局
     * @param view
     */
    private void setPopupWindow(View view){
        ToastUtils.showToast(SearchActivity.this,"请选择商品分类",Toast.LENGTH_SHORT);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.poput_layout, null);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        backgroundAlpha(0.3f);
        Button fenlei1 = (Button) layout.findViewById(R.id.fenlei1);
        Button fenlei2 = (Button) layout.findViewById(R.id.fenlei2);
        Button fenlei3 = (Button) layout.findViewById(R.id.fenlei3);
        Button fenlei4 = (Button) layout.findViewById(R.id.fenlei4);
        Button fenlei5 = (Button) layout.findViewById(R.id.fenlei5);
        Button fenlei6 = (Button) layout.findViewById(R.id.fenlei6);
        Button fenlei7 = (Button) layout.findViewById(R.id.fenlei7);
        Button fenlei8 = (Button) layout.findViewById(R.id.fenlei8);
        Button fenlei9 = (Button) layout.findViewById(R.id.fenlei9);
        Button fenlei10 = (Button) layout.findViewById(R.id.fenlei10);
        Button fenlei11 = (Button) layout.findViewById(R.id.fenlei11);

        fenlei1.setOnClickListener(this);
        fenlei2.setOnClickListener(this);
        fenlei3.setOnClickListener(this);
        fenlei4.setOnClickListener(this);
        fenlei5.setOnClickListener(this);
        fenlei6.setOnClickListener(this);
        fenlei7.setOnClickListener(this);
        fenlei8.setOnClickListener(this);
        fenlei9.setOnClickListener(this);
        fenlei10.setOnClickListener(this);
        fenlei11.setOnClickListener(this);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha s
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);  getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}
