package com.lingjuan.app.fragment.sake;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lingjuan.app.R;
import com.lingjuan.app.activity.FirstActivity;
import com.lingjuan.app.activity.PurchaseActivity;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.base.BaseFrament;
import com.lingjuan.app.uitls.HttpMethods;
import com.lingjuan.app.uitls.IntentUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by sks on 2017/8/19.
 */

public class SaleFragment extends BaseFrament implements View.OnClickListener {
    TextView meiri;
    TextView tejia;
    TextView baokuan;
    private PullToRefreshListView mPullRefreshGridView;
    private ListView mGridView;
    LinearLayout buju;
    private ArrayList<Map<String, Object>> mapArrayList;
    private YouPinAdapter latesAdapter;
    private View Heaner;
    private int paths = 1;
    @Override
    protected void init(View view) {
        buju = (LinearLayout) view.findViewById(R.id.buju);
        Heaner = LayoutInflater.from(getActivity()).inflate(R.layout.youpin_addhoe, null);
        meiri = (TextView) Heaner.findViewById(R.id.meiri);
        tejia = (TextView) Heaner.findViewById(R.id.tejia);
        baokuan = (TextView) Heaner.findViewById(R.id.baokuan);
        meiri.setOnClickListener(this);
        tejia.setOnClickListener(this);
        baokuan.setOnClickListener(this);
        mPullRefreshGridView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
        mGridView.addHeaderView(Heaner);
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
                        }, 6000);
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
        //加载数据
        getRequestdata();

        /**
         * 跳转
         */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PurchaseActivity.class);
/*                String sc_Name = (String) mapArrayList.get(position-2).get("goods_short_title");
                String sc_Pic = (String) mapArrayList.get(position-2).get("goods_pic");
                String sc_Id = (String) mapArrayList.get(position-2).get("goods_id");
                String sc_YouHuie = (String) mapArrayList.get(position-2).get("coupon_price");
                String sc_YuanJia = (String) mapArrayList.get(position-2).get("goods_price");
                String sc_XiaoLiang = (String) mapArrayList.get(position-2).get("goods_sales");
                String sc_JuanId = (String) mapArrayList.get(position-2).get("coupon_id");*/
                intent.putExtra("list",mapArrayList );
                intent.putExtra("posd", position);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.framgnet_serat;
    }

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
                Toast.makeText(getActivity(), "===" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Log.d("--Main--","获取到的数据:"+s);
                buju.setVisibility(View.GONE);
                // Toast.makeText(getActivity(), "==="+s, Toast.LENGTH_SHORT).show();
                mapArrayList = getArrJson(s);
                latesAdapter = new YouPinAdapter(mapArrayList, getActivity());
                mGridView.setAdapter(latesAdapter);
            }
        }, 1,0,1);
    }

    /**
     * 第二次请求网络数据
     */
    private void getErCiRequestdata(int path) {
        HttpMethods.BASE_URL = "http://openapi.qingtaoke.com/";
        HttpMethods.getInstance().getJiuJIud(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "===" + e, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.baokuan:
                intent = new Intent(getActivity(), FirstActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.meiri:
                intent = new Intent(getActivity(), FirstActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.tejia:
                intent = new Intent(getActivity(), FirstActivity.class);
                intent.putExtra("type",4);
                startActivity(intent);
                break;
        }
    }
}
