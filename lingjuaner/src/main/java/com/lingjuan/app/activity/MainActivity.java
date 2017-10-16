package com.lingjuan.app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lingjuan.app.R;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.fragment.MyFragment;
import com.lingjuan.app.fragment.XinMyFragment;
import com.lingjuan.app.fragment.later.LatestFragment;
import com.lingjuan.app.fragment.perferred.PreferredFragment;
import com.lingjuan.app.fragment.sake.SaleFragment;
import com.lingjuan.app.fragment.later.TuiJuFragment;
import com.lingjuan.app.uitls.NetWorkUtils;
import com.lingjuan.app.uitls.Util;
import com.lingjuan.app.witde.OnClists;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;



import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  time : 2017年8月26日21:28:49
 *  by CrackgnKey
 *  首页
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.fl_id)
    ViewPager viewpager;
    @Bind(R.id.rb_weixin)
    RadioButton rbWeixin;
    @Bind(R.id.rb_wo)
    RadioButton rbWo;
    @Bind(R.id.rb_faxian)
    RadioButton rbFaxian;
    @Bind(R.id.rb_jiu)
    RadioButton rbJiu;
    @Bind(R.id.rg_zu)
    RadioGroup rgZu;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    private Fragment[] fragment = new Fragment[4];
    private RadioButton[] radioButtons = new RadioButton[4];
    public static int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    public static int mExtarFlag = 0x00;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ExampleApplication.AppContent = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // 有些情况下需要先清除透明flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        fragment[0] = new LatestFragment();//首页
        fragment[1] = new PreferredFragment();//包邮
        fragment[2] = new SaleFragment();//优品
        fragment[3] = new XinMyFragment();//我的
        viewpager = (ViewPager)findViewById(R.id.fl_id);
        //构建Adapter
        MyPageAdapter mypag = new MyPageAdapter(getSupportFragmentManager());
        //填充
        viewpager.setAdapter(mypag);
        //设置缓存数量
        viewpager.setOffscreenPageLimit(5);
        //设置滑动监听
        //初始化RadioButton
        radioButtons[0] = (RadioButton) findViewById(R.id.rb_weixin);
        radioButtons[1] = (RadioButton) findViewById(R.id.rb_wo);
        radioButtons[2] = (RadioButton) findViewById(R.id.rb_faxian);
        radioButtons[3] = (RadioButton) findViewById(R.id.rb_jiu);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioButtons[position].setChecked(true);
                if(position == 0){
                    setVingSou();
                }else if(position == 1 ){
                    setGongSou();
                }else if(position == 2 ){
                    setVingChao();
                }else if(position == 3){
                    setGerenzhongxin();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * QQ分享
         */
        XinMyFragment.setOnClists(new OnClists() {
            @Override
            public void getOncilick(View view) {
                //shareText(MainActivity.this,qqShareListener);
                // setFenXiang(view);
                shareOnlyImageOnQQ(view);
            }
        });
        setVingSou();
        //初始化父布局组件
        getOnCli().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }


    /**
     * Fragment填充数据
     */
    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragment[position];
        }

        @Override
        public int getCount() {
            return fragment.length;
        }
    }

    @OnClick({ R.id.rb_weixin, R.id.rb_wo, R.id.rb_faxian,R.id.rb_jiu})
    public void submit(View view) {
        switch (view.getId()){
            case R.id.rb_weixin://微信
                viewpager.setCurrentItem(0,false);
                setVingSou();
                break;
            case R.id.rb_wo://通讯录
                viewpager.setCurrentItem(1,false);
                setGongSou();
                break;
            case R.id.rb_faxian://发现
                viewpager.setCurrentItem(2,false);
                setVingChao();
                break;
            case R.id.rb_jiu://我的
                if(!ExampleApplication.IsLogin) {
                    viewpager.setCurrentItem(3,false);
                    setGerenzhongxin();
                    //startActivity(new Intent(this, LoginActivity.class));
                }else {
                    viewpager.setCurrentItem(3,false);
                    setGerenzhongxin();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
               ExampleApplication.mTencent.shareToQQ(MainActivity.this, params, qqShareListener);
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
                Util.toastMessage(MainActivity.this, "取消分享");
            }
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(MainActivity.this, "分享成功");

        }
        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(MainActivity.this, "onError: " + e.errorMessage, "e");
        }
    };

    /**
     * z这才是QQ分享
     * @param view
     */
    public void shareOnlyImageOnQQ(View view) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "您的朋友向您分享了一本省钱秘笈");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "下载APP,天天双11,还在等什么,快来加入我们吧");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.aiboyy.pw/gx/daling.apk");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://www.aiboyy.pw/images/bg.png");
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
    public void onBackPressed() {
        if(((System.currentTimeMillis()-time)>2000)){
            Toast.makeText(this, "在按一次退出,谢谢", Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }
        time = System.currentTimeMillis();
    }

}
