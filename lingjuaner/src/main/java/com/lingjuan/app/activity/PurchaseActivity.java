package com.lingjuan.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lingjuan.app.R;
import com.lingjuan.app.adapter.ListPicAdapter;
import com.lingjuan.app.adapter.YouPinAdapter;
import com.lingjuan.app.api.Iben;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.base.Share;
import com.lingjuan.app.uitls.Duwenjian;
import com.lingjuan.app.uitls.HttpMethods;
import com.lingjuan.app.uitls.L;
import com.lingjuan.app.uitls.Logger;
import com.lingjuan.app.uitls.Util;
import com.lingjuan.app.witde.DemoTradeCallback;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 购买页面
 * Created by sks on 2017/8/27.
 */

public class PurchaseActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_searchs)
    EditText etSearchs;
    @Bind(R.id.include11dsw)
    RelativeLayout include11dsw;
    @Bind(R.id.buju23)
    LinearLayout buju23;
    @Bind(R.id.buju243)
    LinearLayout buju243;
    @Bind(R.id.meiriqian)
    LinearLayout meiriqian;
    @Bind(R.id.gerenzhongxin)
    LinearLayout gerenzhongxin;
    @Bind(R.id.shangpin)
    LinearLayout shangpin;
    @Bind(R.id.linearLayout_focus)
    RelativeLayout linearLayoutFocus;
    @Bind(R.id.sh_pic)
    ImageView shPic;
    @Bind(R.id.butt_name)
    TextView buttName;
    @Bind(R.id.butt_jiage)
    TextView buttJiage;
    @Bind(R.id.butt_xianjia)
    TextView buttXianjia;
    @Bind(R.id.butt_yuanjia)
    TextView buttYuanjia;
    @Bind(R.id.butt_xiaoliang)
    TextView buttXiaoliang;
    @Bind(R.id.butt_fuw)
    TextView buttFuw;
    @Bind(R.id.butt_chakan)
    Button buttChakan;
    @Bind(R.id.webview)
    ListView webview;
    @Bind(R.id.sc_jiehsao)
    TextView scJiehsao;
    Button button_lingjuan;
    private ArrayList<Map<String, Object>> mapArrayList;
    private int posd;
    private Map<String, String> exParams;//yhhpass参数
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    public static int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    public static int mExtarFlag = 0x00;
    private String fenxiangjiage;
    private String PicUrl;
    private KProgressHUD hud;
    private static final String OSCHINA_START="<div class=\"white\"><div class=\"highlight\">";
    private static final String IMGSRC_REG = "http(s)?:\"?(.*?)(\"|>|)+.(jpg|jpeg|gif|png|bmp)";
    private String urls;
    private ArrayList<String> picList;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.purchase_activity);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        setshangqin();
        mapArrayList = (ArrayList<Map<String, Object>>) getIntent().getSerializableExtra("list");
        type = getIntent().getIntExtra("type",3);
        //判定是否是点击轮播图进来的
        buttJiage = (TextView) findViewById(R.id.butt_jiage);
        buttXianjia = (TextView) findViewById(R.id.butt_xianjia);
        buttName = (TextView) findViewById(R.id.butt_name);
        buttYuanjia = (TextView) findViewById(R.id.butt_yuanjia);
        buttChakan = (Button) findViewById(R.id.butt_chakan);
        buttXiaoliang = (TextView) findViewById(R.id.butt_xiaoliang);
        scJiehsao = (TextView) findViewById(R.id.sc_jiehsao);
        shPic = (ImageView) findViewById(R.id.sh_pic);
        webview = (ListView) findViewById(R.id.webview);
        //判定是不是点击轮播图来的
        if(type != 5){//不是
            allGoods(type);
        }else {//是
            posd = 0;
            carouseGoods(posd);
        }
        View include11 = findViewById(R.id.include11);
        ImageView image_fenxiang = (ImageView) include11.findViewById(R.id.image_fenxiang);
        ImageView image_fanhui = (ImageView) include11.findViewById(R.id.image_fanhui);
        image_fanhui.setOnClickListener(this);
        image_fenxiang.setOnClickListener(this);
        buttChakan.setOnClickListener(this);
        urls = Duwenjian.getString(this,"wwbview.html");


    }

    @Override
    protected int getLayout() {
        return R.layout.purchase_activity;
    }

    @Override
    protected void init() {

    }


    /**
     * @param view
     * 打开指定链接
     */
    public void showUrl(View view) {
        String yUrl = (String) mapArrayList.get(posd).get("coupon_id");
        String sId;
        //判定是不是点击轮播图来的
        if(type != 5){
            sId = (String) mapArrayList.get(posd).get("goods_id");
        }else {
            sId = (String) mapArrayList.get(posd).get("quan_id");
        }
        System.out.println("====拼接的地址="+yUrl);
        Toast.makeText(this, "正在跳转至淘宝,请稍等", Toast.LENGTH_SHORT).show();
        alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");
        AlibcTrade.show(this, new AlibcPage(Iben.getUrl(yUrl,sId,Iben.YURL)), alibcShowParams, null, exParams , new DemoTradeCallback());
    }

    /**
     * QQ分享附带方法
     * @param params
     */
    protected  void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                ExampleApplication.mTencent.shareToQQ(PurchaseActivity.this, params, qqShareListener);
            }
        });
    }

    /**
     * QQ接口回调
     */
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                Util.toastMessage(PurchaseActivity.this, "取消分享");
            }
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(PurchaseActivity.this, "分享成功");

        }
        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(PurchaseActivity.this, "onError: " + e.errorMessage, "e");
        }
    };

    /**
     * 这才是QQ分享
     * @param view
     */
    public void shareOnlyImageOnQQ(String view,String jiage,String miaoshu,String url,String tupian) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, view+"→秒杀价:"+jiage);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  miaoshu);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,tupian);
        // params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "领卷儿");

        // mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
        doShareToQQ(params);
    }

    //如果要收到QQ分享，或登录的一些状态，必须加入代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_fanhui:
                finish();
                break;
            case R.id.image_fenxiang:
                if(type != 5){
                    //销量
                    String miaoshu = (String) mapArrayList.get(posd).get("goods_introduce");
                    //打开的地址
                    String yUrl = (String) mapArrayList.get(posd).get("coupon_id");
                    String sId = (String) mapArrayList.get(posd).get("goods_id");
                    String name = (String) mapArrayList.get(posd).get("goods_short_title");
                    //分享商品
                    shareOnlyImageOnQQ(name,fenxiangjiage,miaoshu,Iben.getUrl(yUrl,sId,Iben.YURL),PicUrl);
                }else {
                    //销量
                    String miaoshu = (String) mapArrayList.get(0).get("quan_guid_content");
                    //打开的地址
                    String yUrl = (String) mapArrayList.get(0).get("quan_id");
                    String sId = (String) mapArrayList.get(0).get("goods_id");
                    String name = (String) mapArrayList.get(0).get("goods_name");
                    String price_after_coupons = (String) mapArrayList.get(0).get("price_after_coupons");
                    //分享商品
                    shareOnlyImageOnQQ(name,price_after_coupons,miaoshu,Iben.getUrl(yUrl,sId,Iben.YURL),PicUrl);
                }
                break;
            case R.id.butt_chakan://加载详情
                String sId2 = (String) mapArrayList.get(posd).get("goods_id");
                getXiqng(sId2);
                break;
        }
    }

    /**
     * 加载商品详情
     */
    private void getXiqng(String nei) {

        hud = KProgressHUD.create(PurchaseActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("疯狂加载中")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        String url = "http://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data={item_num_id:"+nei+"}";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        webview.setVisibility(View.VISIBLE);
                        hud.dismiss();
                        picList = getArrJson(response);
                        ListPicAdapter listPicAdapter = new ListPicAdapter(PurchaseActivity.this,picList);
                        webview.setAdapter(listPicAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hud.dismiss();
                Toast.makeText(PurchaseActivity.this, "请求失败,请稍后尝试", Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onDestroy() {
        if(picList != null){
            picList.clear();
            picList = null;
        }
        super.onDestroy();
    }

    /**
     * 解析json
     * @param string 要解析的字符串
     * @return s
     */
    private ArrayList<String> getArrJson(String string) {
        picList = new ArrayList<>();
       String  List = "";
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject jsonArray = jsonObject.getJSONObject("data");
            JSONArray listjsonarrya = jsonArray.getJSONArray("images");
            for (int i = 0;i < listjsonarrya.length();i++){
                picList.add(listjsonarrya.getString(i));
            }
            //  List = jsonObject1.getString("pages");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return picList;
    }

    /**
     * 弹出布局
     * @param yirme
     */
    private void Diods(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseActivity.this,R.style.AlertDialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.hong_diaoliag, null);
        ImageView bt = (ImageView) view.findViewById(R.id.imageView2);
        builder.setView(view);
        final AlertDialog ad = builder.create();
        ad.show();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据QQ号跳转聊天页面
                String url1 ="mqqwpa://im/chat?chat_type=wpa&uin="+Iben.QQ;

                Intent i1 =new Intent(Intent.ACTION_VIEW, Uri.parse(url1));

                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i1.setAction(Intent.ACTION_VIEW);

                startActivity(i1);

                ad.dismiss();

                System.out.println("======我的客服QQ是多少:"+Iben.QQ);
            }
        });
    }

    /**
     * 是全部商品的
     * @param type type值
     */
    private void allGoods(int type){
        String sId = (String) mapArrayList.get(posd).get("goods_id");
        //超过五次的时候清楚数据
        if(Share.scList.size() > 5){
            Share.scList.clear();
        }
        //添加商品累计次数
        if(Share.scList.get(sId)!= null){
            //不为空的时候获取已点击的熟练
            int i = Share.scList.get(sId);
            //自增加
            i++;
            //把增加的熟练添加进入
            Share.scList.put(sId,i);
            //满足5次弹出红包 清楚布局
            if(i >= 5){
                Diods();
                Share.scList.remove(sId);
            }
            L.d("我走到添加数据了，商品号是;"+sId+",这是第几次点击：= "+i);
        }else {
            L.d("我走到添加数据了,第一次；"+sId);
            //没有的话直接添加
            Share.scList.put(sId,1);
        }
        if(type == 1){
            posd = getIntent().getIntExtra("posd", 0)-1;
        }else if(type == 2){
            posd = getIntent().getIntExtra("posd", 0)-2;
        }
        //商品名字
        buttName.setText((String) mapArrayList.get(posd).get("goods_short_title"));
        //商品价格
        buttYuanjia.setText("优惠券:￥"+String.valueOf(mapArrayList.get(posd).get("coupon_price"))+"");
        //商品销量
        buttXiaoliang.setText("月销量:"+mapArrayList.get(posd).get("goods_sales"));
        //商品描述
        scJiehsao.setText((String) mapArrayList.get(posd).get("goods_introduce"));
        PicUrl = (String) mapArrayList.get(posd).get("goods_pic");

        if(PicUrl.startsWith("//")){
            PicUrl = "https:"+PicUrl;
        }
        //商品图片
        Glide.with(PurchaseActivity.this).load(PicUrl)
                .placeholder(R.mipmap.ic_loading_large)
                .error(R.mipmap.ic_loading_large)
                .into(shPic);
        //原价
        buttXianjia.setText("原价:￥"+mapArrayList.get(posd).get("goods_price"));

        double yuanjiage = (double) mapArrayList.get(posd).get("goods_price");
        double youhuijuanjiage = (double) mapArrayList.get(posd).get("coupon_price");
        DecimalFormat df   = new DecimalFormat("######0.00");
        buttJiage.setText("现价:￥"+df.format(yuanjiage-youhuijuanjiage));
        fenxiangjiage = df.format(yuanjiage-youhuijuanjiage);
    }

    /**
     * 轮播商品进来的
     */
    private void carouseGoods (int posd){
        //商品名字
        buttName.setText((String) mapArrayList.get(posd).get("goods_name"));
        //商品价格
        buttYuanjia.setText("优惠券:￥"+String.valueOf(mapArrayList.get(posd).get("price_coupons"))+"");
        //商品销量
        buttXiaoliang.setText("月销量:"+mapArrayList.get(posd).get("sales"));
        //商品描述
        scJiehsao.setText((String) mapArrayList.get(posd).get("quan_guid_content"));
        PicUrl = (String) mapArrayList.get(posd).get("pic");

        if(PicUrl.startsWith("//")){
            PicUrl = "http:"+PicUrl;
        }
        //商品图片
        Glide.with(PurchaseActivity.this).load(PicUrl)
                .placeholder(R.mipmap.ic_loading_large)
                .error(R.mipmap.ic_loading_large)
                .into(shPic);
        //原价
        buttXianjia.setText("原价:￥"+mapArrayList.get(posd).get("price"));
        buttJiage.setText("现价:￥"+mapArrayList.get(posd).get("price_after_coupons"));

    }
}
