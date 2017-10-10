package com.lingjuan.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.uitls.HttpMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by sks on 2017/9/9.
 */

public class CategoricalActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_zonghe)
    TextView tvZonghe;
    @Bind(R.id.tv_xiaoliang)
    TextView tvXiaoliang;
    @Bind(R.id.tv_juanhoujia)
    TextView tvJuanhoujia;
    @Bind(R.id.tv_zhekou)
    TextView tvZhekou;
    @Bind(R.id.poopwin)
    ImageView poopwin;
    private int type = 0;
    private PullToRefreshListView mPullRefreshGridView;
    private ListView mGridView;
    private ArrayList<Map<String,Object>> mapArrayList;
    private YouPinAdapter latesAdapter;
    private TextView leibie1,leibie2,leibie3,leibie4,leibie5,leibie6,leibie7,leibie8,leibie9,leibie10;
    //分页ID
    private int paths = 1;
    //赛选方式ID
    private int ListYtpe = 2;
    private KProgressHUD hud;
    private PopupWindow popupWindow;
    private boolean isOnSucc = true;
    @Override
    protected int getLayout() {
        return R.layout.activity_sefne;
    }

    @Override
    protected void init() {

    }

    /**
     * 二次请求网络
     * @param path 页数
     */
    private void getErCiRequestdata(int path) {
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getJiuJIud(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                mapArrayList.addAll(getArrJson(s));
                latesAdapter.setMapArrayList(mapArrayList);
                latesAdapter.notifyDataSetChanged();
                mPullRefreshGridView.onRefreshComplete();
            }
        }, path,type,ListYtpe);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // 有些情况下需要先清除透明flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        setContentView(R.layout.activity_sefne);
        ButterKnife.bind(this);
        setFenLei();
        tvZonghe.setOnClickListener(this);
        tvXiaoliang.setOnClickListener(this);
        tvZhekou.setOnClickListener(this);
        tvJuanhoujia.setOnClickListener(this);
        poopwin.setOnClickListener(this);
        //取出传递过来的值
        mPullRefreshGridView = (PullToRefreshListView) findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(CategoricalActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                CategoricalActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullRefreshGridView.onRefreshComplete();
                                Toast.makeText(CategoricalActivity.this, "别乱搞", Toast.LENGTH_SHORT).show();
                            }
                        },2000);
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(CategoricalActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                paths = paths+1;
                getErCiRequestdata(paths);
            }

        });
        type = getIntent().getIntExtra("type",0);
        //加载数据
        getRequestdata(type);
        /**
         * 跳转
         */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoricalActivity.this, PurchaseActivity.class);
                intent.putExtra("list",mapArrayList );
                intent.putExtra("posd", position);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        /**
         * j监听滑动事件
         */
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_IDLE:
                        System.out.println("滑动停止");
                        isOnSucc = true;
                        //滑动停止时调用
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        System.out.println("滑动开始");
                        isOnSucc = false;
                        //正在滚动时调用
                        break;
                    case SCROLL_STATE_FLING:
                        System.out.println("滑动还在继续");
                        isOnSucc = false;
                        //手指快速滑动时,在离开ListView由于惯性滑动
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(popupWindow != null){
            popupWindow.dismiss();
        }
        switch (v.getId()){
            case R.id.tv_zonghe://综合
                if(isOnSucc){
                    ListYtpe = 2;
                    getRequestdata(type);
                    if(mapArrayList != null)
                        mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.xuanzhong,R.color.weixuan,R.color.weixuan,R.color.weixuan);
                }else {
                    Toast.makeText(this, "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_xiaoliang://销量
                if(isOnSucc){
                    ListYtpe = 3;
                    getRequestdata(type);
                    if(mapArrayList != null)
                        mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.weixuan,R.color.xuanzhong,R.color.weixuan,R.color.weixuan);
                }else {
                    Toast.makeText(this, "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_juanhoujia://劵后价
                if(isOnSucc){
                    ListYtpe = 4;
                    getRequestdata(type);
                    if(mapArrayList != null)
                        mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.weixuan,R.color.weixuan,R.color.xuanzhong,R.color.weixuan);
                }else {
                    Toast.makeText(this, "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_zhekou://人气
                if(isOnSucc){
                    ListYtpe = 4;
                    getRequestdata(type);
                    if(mapArrayList != null)
                        mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.weixuan,R.color.weixuan,R.color.weixuan,R.color.xuanzhong);
                }else {
                    Toast.makeText(this, "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.poopwin:
                //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                //弹起布局
                ShowPopUp();
                break;
            //这是POPUP里面的控件
            case R.id.leibie1:
                type = 10;
                getRequestdata(type);
                break;
            case R.id.leibie2:
                type = 12;
                getRequestdata(type);
                break;
            case R.id.leibie3:
                type = 11;
                getRequestdata(type);
                break;
            case R.id.leibie4:
                type = 2;
                getRequestdata(type);
                break;
            case R.id.leibie5:
                type = 3;
                getRequestdata(type);
                break;
            case R.id.leibie6:
                type = 4;
                getRequestdata(type);
                break;
            case R.id.leibie7:
                type = 5;
                getRequestdata(type);
                break;
            case R.id.leibie8:
                type = 6;
                getRequestdata(type);
                break;
            case R.id.leibie9:
                type = 7;
                getRequestdata(type);
                break;
            case R.id.leibie10:
                type = 8;
                getRequestdata(type);
                break;
        }
    }

    /**
     * 改变按钮颜色
     */
    private void getTextCorlos(int colos,int colos2,int colos3,int colos4){
        //综合
        tvZonghe.setTextColor(getResources().getColor(colos));
        //销量
        tvXiaoliang.setTextColor(getResources().getColor(colos2));
        //劵后价
        tvJuanhoujia.setTextColor(getResources().getColor(colos3));
        //折扣
        tvZhekou.setTextColor(getResources().getColor(colos4));
    }
    /**
     * 第一次请求网络
     * @param tid 分类ID
     */
    private void getRequestdata(int tid) {
        hud = KProgressHUD.create(CategoricalActivity.this)
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
                // Toast.makeText(getActivity(), "===" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                hud.dismiss();
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                mapArrayList = getArrJson(s);
                latesAdapter = new YouPinAdapter(mapArrayList, CategoricalActivity.this);
                mGridView.setAdapter(latesAdapter);
            }
        }, 1,tid,ListYtpe);
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


    /**
     * 弹起分类布局
     */
    private void ShowPopUp(){
       // backgroundAlpha(0.5f);
        View v = findViewById(R.id.view_weizhi);
        View view = View.inflate(this, R.layout.layout_popsaii, null);
        //获取PopupWindow中View的宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);//popupwindow设置焦点
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0xaa000000));//设置背景
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        // popupWindow.showAsDropDown(v,0,0);
        //获取点击View的坐标
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        popupWindow.showAsDropDown(v);//在v的下面
        //显示在下方
        popupWindow.showAtLocation(v,Gravity.NO_GRAVITY,location[0]+v.getWidth(),location[1]);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);//设置动画
        backgroundAlpha(view);
    }

    /**
     * 初始化控件并且设置点击事件
     * @param view s
     */
    public void backgroundAlpha(View view) {

        leibie1 = (TextView) view.findViewById(R.id.leibie1);
        leibie2 = (TextView) view.findViewById(R.id.leibie2);
        leibie3 = (TextView) view.findViewById(R.id.leibie3);
        leibie4 = (TextView) view.findViewById(R.id.leibie4);
        leibie5 = (TextView) view.findViewById(R.id.leibie5);
        leibie6 = (TextView) view.findViewById(R.id.leibie6);
        leibie7 = (TextView) view.findViewById(R.id.leibie7);
        leibie8 = (TextView) view.findViewById(R.id.leibie8);
        leibie9 = (TextView) view.findViewById(R.id.leibie9);
        leibie10 = (TextView) view.findViewById(R.id.leibie10);
        leibie1.setOnClickListener(this);
        leibie2.setOnClickListener(this);
        leibie3.setOnClickListener(this);
        leibie4.setOnClickListener(this);
        leibie5.setOnClickListener(this);
        leibie6.setOnClickListener(this);
        leibie7.setOnClickListener(this);
        leibie8.setOnClickListener(this);
        leibie9.setOnClickListener(this);
        leibie10.setOnClickListener(this);
        //设置选择颜色
        switch (type){
            case 2:
                leibie4.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 3:
                leibie5.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 4:
                leibie6.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 5:
                leibie7.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 6:
                leibie8.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 7:
                leibie9.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 8:
                leibie10.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 9:
                leibie1.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 10:
                leibie1.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 11:
                leibie3.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
            case 12:
                leibie2.setTextColor(getResources().getColor(R.color.xuanzhong));
                break;
        }
    }

}
