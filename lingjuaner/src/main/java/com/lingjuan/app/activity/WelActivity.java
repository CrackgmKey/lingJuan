package com.lingjuan.app.activity;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lingjuan.app.R;
import com.lingjuan.app.adapter.LatesAdapter;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.api.Iben;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.uitls.HttpMethods;
import com.lingjuan.app.uitls.IntentUtil;
import com.lingjuan.app.uitls.NetWorkStateService;
import com.lingjuan.app.uitls.NetWorkUtils;
import com.lingjuan.app.witde.NetworkImageHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by sks on 2017/8/22.
 */

public class WelActivity extends BaseActivity {
    Button button;
    private ArrayList<Map<String,Object>> mapArrayList;
    ConvenientBanner lunId;
    private int[] ints = {R.mipmap.zhi1, R.mipmap.zhi2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wei_aiviti);
        Intent intent = new Intent(WelActivity.this,NetWorkStateService.class);
        startService(intent);
        button = (Button) findViewById(R.id.button);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final List<String> urls = new ArrayList<>();
        urls.add("http://www.aiboyy.pw/lingjuan/images/lun2.jpg");
        urls.add("http://www.aiboyy.pw/lingjuan/images/lun1.jpg");
        urls.add("http://www.aiboyy.pw/lingjuan/images/lun3.png");

        lunId = (ConvenientBanner) findViewById(R.id.lun_id);
        lunId.setPointViewVisible(true);
        lunId.startTurning(115000);
        lunId.setPageIndicator(ints);
        //网络地址
        //网络加载例子
        lunId.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, urls);
        lunId.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("==========="+position);

            }
            @Override
            public void onPageSelected(int position) {
                System.out.println("111111111111111111111111"+position);
                if(position == urls.size()-1){
                    button.setVisibility(View.VISIBLE);
                }else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("222222222222222222"+state);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(NetWorkUtils.NETWORK){
                    finish();

                    //第一个界面跳转的时候执行
/*                    Intent shareIntent = new Intent(WelActivity.this, MainActivity.class);
                    String transitionName ="123";
                    ActivityOptionsCompat transitionActivityOptions
                            = ActivityOptionsCompat.makeSceneTransitionAnimation(WelActivity.this, button, transitionName);
                    startActivity(shareIntent, transitionActivityOptions.toBundle());*/

                    Intent intent1 = new Intent(WelActivity.this,MainActivity.class);
                    startActivity(intent1);
                }else{
                    NetWorkUtils.openSetting(WelActivity.this);
                    Toast.makeText(WelActivity.this, "请先设置网络,谢谢合作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getRequestdata();
        getRequestdataReSou();
        getLunBo();
    }

    @Override
    protected int getLayout() {
        return R.layout.wei_aiviti;
    }

    @Override
    protected void init() {

    }

    /**
     * 首页第一次的数据
     */
    /**
     * 第一次请求网络数据
     */
    private void getRequestdata() {
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
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                Iben.mapArrayList = getArrJson(s);

            }
        }, 1,0);
    }


    /**
     * 热门搜索
     */
    private void getLunBo(){
        HttpMethods.BASE_URL = "http://www.aiboyy.pw/";
        HttpMethods.getInstance().getLunBo(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //Toast.makeText(getActivity(), "==="+e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                Iben.mLunBo = getReLunbo(s);
            }
        });
    }


    /**
     * 热门搜索
     */
    private void getRequestdataReSou(){
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getReSouSuo(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //Toast.makeText(getActivity(), "==="+e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                Iben.mRenSou = getReJson(s);
            }
        });
    }

    /**
     * 解析第一次数据
     * @param string
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
     * 解析热搜的数据
     * @param string
     * @return
     */
    private ArrayList<String> getReLunbo(String string){
        ArrayList<String> List = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            //获取图片的
            JSONArray jsonArray = jsonObject1.getJSONArray("images");
            for (int i = 0 ;i < jsonArray.length();i++){
                String word = jsonArray.getString(i);
                List.add(word);
            }
            //获取对应的商品ID
            JSONArray jsonArray1 = jsonObject1.getJSONArray("scid");
            for (int i = 0 ;i < jsonArray1.length();i++){
                String word = jsonArray1.getString(i);
                Iben.mLunId.add(word);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return List;
    }


    /**
     * 解析热搜的数据
     * @param string
     * @return
     */
    private ArrayList<String> getReJson(String string){
        ArrayList<String> List = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0 ;i < jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String word = jsonObject1.getString("word");
                List.add(word);
                if(i > 20){
                    return List;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return List;
    }


/*    *//**
     * PID的获取
     *//*
    private void getYaoqingM() {
        HttpMethods.BASE_URL = "http://www.aiboyy.pw/";
        HttpMethods.getInstance().getPid(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String name = jsonObject.getString("name");
                    String qq = jsonObject.getString("cate_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

}
