package com.lingjuan.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lingjuan.app.R;
import com.lingjuan.app.activity.FirstActivity;
import com.lingjuan.app.activity.PurchaseActivity;
import com.lingjuan.app.activity.SearchActivity;
import com.lingjuan.app.adapter.BaoYouAdapter;
import com.lingjuan.app.adapter.LatesAdapter;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.base.BaseFrament;
import com.lingjuan.app.fragment.later.LatestFragment;
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

import static cn.jpush.android.api.JPushInterface.a.v;

/**
 * Created by sks on 2017/8/19.
 */

public class BaoYouZiSaleFragment extends BaseFrament implements View.OnClickListener {
    private PullToRefreshListView mPullRefreshGridView;
    private ListView mGridView;
    @Bind(R.id.pull_refresh_grid)
    PullToRefreshListView pullRefreshGrid;
    @Bind(R.id.progressBar2)
    ProgressBar progressBar2;
    @Bind(R.id.buju)
    LinearLayout buju;
    private ArrayList<Map<String,Object>> mapArrayList;
    private BaoYouAdapter latesAdapter;
    private View Heaner;
    private int yuanjia,xianjia;
    private int path = 1;
    private View v;
    private KProgressHUD hud;
    private PopupWindow popupWindow;
    private TextView leibie1,leibie2,leibie3,leibie4,leibie5,leibie6,leibie7,leibie8,leibie9,leibie10,leibie0;
    private int type = 0;
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
    private boolean isOnSucc = true;
    private int ListYtpe = 2;
    /**
     * Fragment传值
     *
     * @param
     * @return
     */
    public static BaoYouZiSaleFragment newIntes(int yuanjia,int xianjia) {
        BaoYouZiSaleFragment df = new BaoYouZiSaleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("yuanjia", yuanjia);
        bundle.putInt("xianjia", xianjia);
        df.setArguments(bundle);
        return df;
    }
    @Override
    protected void init(View view) {
        ButterKnife.bind(this,view);
        v = view.findViewById(R.id.view_weizhi);
        tvZonghe.setOnClickListener(this);
        tvXiaoliang.setOnClickListener(this);
        tvZhekou.setOnClickListener(this);
        tvJuanhoujia.setOnClickListener(this);
        poopwin.setOnClickListener(this);
        mPullRefreshGridView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
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
                                Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
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
                path = path+1;
                //加载更多
                getErRequestdata(yuanjia,xianjia,path);
            }

        });
        Bundle bundle = getArguments();
        yuanjia = bundle.getInt("yuanjia");
        xianjia = bundle.getInt("xianjia");
        //加载数据
        getRequestdata(yuanjia,xianjia,1,0);
        /**
         * 跳转
         */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PurchaseActivity.class);
                intent.putExtra("list",mapArrayList);
                intent.putExtra("posd", position);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });


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
    protected int getLayout() {
        return R.layout.framgnet_latest;
    }

    private void getRequestdata(int yuanjia,int xianjia,int sort,int cat){

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getYouHuii(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
                hud.dismiss();
            }

            @Override
            public void onNext(String s) {
                hud.dismiss();
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                mapArrayList = getArrJson(s);
                latesAdapter = new BaoYouAdapter(mapArrayList,getActivity());
                mGridView.setAdapter(latesAdapter);
            }
        },1,yuanjia,xianjia,sort,cat);
    }


    /**
     * 加载更多请求
     * @param yuanjia
     * @param xianjia
     * @param path
     */
    private void getErRequestdata(int yuanjia,int xianjia,int path){
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getYouHuii(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                buju.setVisibility(View.GONE);
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                mapArrayList = getArrJson(s);
                latesAdapter = new BaoYouAdapter(mapArrayList,getActivity());
                mGridView.setAdapter(latesAdapter);
            }
        },path,yuanjia,xianjia);
    }

    /**
     * 解析JSOn
     * @param string
     * @return
     */
    private ArrayList<Map<String,Object>> getArrJson(String string){
        ArrayList<Map<String,Object>> List = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject jsonArray = jsonObject.getJSONObject("data");
            JSONArray listjsonarrya = jsonArray.getJSONArray("list");
            for (int i = 0 ;i < listjsonarrya.length();i++){
                JSONObject jsonObject1 = listjsonarrya.getJSONObject(i);
                Map<String,Object> map = new HashMap<>();
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
        View view = View.inflate(getActivity(), R.layout.layout_popsaii, null);
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
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,location[0]+v.getWidth(),location[1]);
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

    @Override
    public void onClick(View v) {
        if(popupWindow != null){
            popupWindow.dismiss();
        }
        switch (v.getId()){
            case R.id.tv_zonghe://综合
                if(isOnSucc){
                    ListYtpe = 2;
                    getRequestdata(yuanjia,xianjia,ListYtpe,type);
                    mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.xuanzhong,R.color.weixuan,R.color.weixuan,R.color.weixuan);
                }else {
                    Toast.makeText(getActivity(), "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_xiaoliang://销量
                if(isOnSucc){
                    ListYtpe = 3;
                    getRequestdata(yuanjia,xianjia,ListYtpe,type);
                    mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.weixuan,R.color.xuanzhong,R.color.weixuan,R.color.weixuan);
                }else {
                    Toast.makeText(getActivity(), "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_juanhoujia://劵后价
                if(isOnSucc){
                    ListYtpe = 4;
                    getRequestdata(yuanjia,xianjia,ListYtpe,type);
                    mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.weixuan,R.color.weixuan,R.color.xuanzhong,R.color.weixuan);
                }else {
                    Toast.makeText(getActivity(), "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_zhekou://人气
                if(isOnSucc){
                    ListYtpe = 1;
                    getRequestdata(yuanjia,xianjia,ListYtpe,type);
                    mapArrayList.clear();
                    //设置选择颜色
                    getTextCorlos(R.color.weixuan,R.color.weixuan,R.color.weixuan,R.color.xuanzhong);
                }else {
                    Toast.makeText(getActivity(), "请滑动结束后在进行操作", Toast.LENGTH_SHORT).show();
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
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie2:
                type = 12;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie3:
                type = 11;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie4:
                type = 2;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie5:
                type = 3;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie6:
                type = 4;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie7:
                type = 5;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie8:
                type = 6;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie9:
                type = 7;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
                break;
            case R.id.leibie10:
                type = 8;
                getRequestdata(yuanjia,xianjia,ListYtpe,type);
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

}
