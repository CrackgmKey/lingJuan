package com.lingjuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lingjuan.app.R;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.broad.BaseUIListener;
import com.lingjuan.app.broad.TestEvent;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by sks on 2017/8/17.
 *  登录窗口
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button buttLogin;
    Button buttFenxiang;
    @Bind(R.id.image_imqq)
    ImageView imageImqq;
    @Bind(R.id.image_imwx)
    ImageView imageImwx;
    @Bind(R.id.image_imwb)
    ImageView imageImwb;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private static final String TAG = "MainActivity";
    private Tencent mTencent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void init() {
        mTencent = ExampleApplication.mTencent;
        imageImqq = (ImageView) findViewById(R.id.image_imqq);
        imageImwx = (ImageView) findViewById(R.id.image_imwb);
        imageImwb = (ImageView) findViewById(R.id.image_imwx);
        imageImqq.setOnClickListener(this);
        imageImwx.setOnClickListener(this);
        imageImwb.setOnClickListener(this);
    }


    public void buttonLogin(View v) {
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this, "all", mIUiListener);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.image_imqq://微信
                    buttonLogin(v);
                    break;
                case R.id.image_imwb://通讯录
                case R.id.image_imwx://发现
                    Toast.makeText(this, "暂时只支持QQ登录,谢谢您的参与", Toast.LENGTH_SHORT).show();
                    break;
            }
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
           // Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(LoginActivity.this, qqToken);
                mUserInfo.getUserInfo(new BaseUIListener(LoginActivity.this,"get_simple_userinfo"));
                ExampleApplication.IsLogin = true;
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        finish();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");
                        finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }




    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
