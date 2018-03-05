package com.lingjuan.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lingjuan.app.R;
import com.lingjuan.app.activity.MainActivity;
import com.lingjuan.app.activity.SearchActivity;

import butterknife.ButterKnife;

/**
 * Created by sks on 2017/8/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    LinearLayout linearLayout,buju243,meiriqian,gerenzhongxin,shangpin,fenleishangp;
    RelativeLayout relativeLayout,souuo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        //输入框
        relativeLayout = (RelativeLayout) findViewById(R.id.include11dsw);
        //文字
        linearLayout = (LinearLayout) findViewById(R.id.buju23);
        //文字
        buju243 = (LinearLayout) findViewById(R.id.buju243);
        //每日前100
        meiriqian = (LinearLayout) findViewById(R.id.meiriqian);
        //个人中心
        gerenzhongxin = (LinearLayout) findViewById(R.id.gerenzhongxin);
        //商品详情
        shangpin = (LinearLayout) findViewById(R.id.shangpin);
        //搜索页面
        souuo = (RelativeLayout) findViewById(R.id.souuo);
        //分类商品
        fenleishangp = (LinearLayout) findViewById(R.id.fenleishangp);
        init();
    }

    /**
     * 返回布局ID
     */
    protected abstract int getLayout();

    protected abstract void  init();


    /**
     * 显示文字
     */
    protected void setGongSou(){
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        buju243.setVisibility(View.GONE);
        gerenzhongxin.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        souuo.setVisibility(View.GONE);
        meiriqian.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.GONE);

    }


    /**
     * 显示搜索
     */
    protected void setVingSou(){
        relativeLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.GONE);
        gerenzhongxin.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        souuo.setVisibility(View.GONE);
        meiriqian.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.GONE);
    }


    /**
     * 显示超值
     */
    protected void setVingChao(){
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.VISIBLE);
        souuo.setVisibility(View.GONE);
        gerenzhongxin.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        meiriqian.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.GONE);
    }

    /**
     * 显示超值
     */
    protected void setQian100(){
        meiriqian.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.GONE);
        gerenzhongxin.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        souuo.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.GONE);
    }


    /**
     * 显示个人中心
     */
    protected void setGerenzhongxin(){
        gerenzhongxin.setVisibility(View.VISIBLE);
        meiriqian.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        souuo.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.GONE);
    }


    /**
     * 显示个人中心
     */
    protected void setshangqin(){
        gerenzhongxin.setVisibility(View.GONE);
        meiriqian.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.GONE);
        shangpin.setVisibility(View.VISIBLE);
        souuo.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.GONE);
    }


    /**
     * 显示搜索页面
     */
    protected void setSousuo(){
        gerenzhongxin.setVisibility(View.GONE);
        meiriqian.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        souuo.setVisibility(View.VISIBLE);
        fenleishangp.setVisibility(View.GONE);
    }

    /**
     * 显示搜索页面
     */
    protected void setFenLei(){
        gerenzhongxin.setVisibility(View.GONE);
        meiriqian.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        buju243.setVisibility(View.GONE);
        shangpin.setVisibility(View.GONE);
        souuo.setVisibility(View.GONE);
        fenleishangp.setVisibility(View.VISIBLE);
    }





    protected RelativeLayout getOnCli() {
        return relativeLayout;
    }
}
