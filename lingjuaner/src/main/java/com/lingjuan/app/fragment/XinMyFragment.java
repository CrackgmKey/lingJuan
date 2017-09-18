package com.lingjuan.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lingjuan.app.R;
import com.lingjuan.app.activity.LoginActivity;
import com.lingjuan.app.api.Iben;
import com.lingjuan.app.base.BaseFrament;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.broad.BaseUIListener;
import com.lingjuan.app.uitls.ToastUtils;
import com.lingjuan.app.witde.OnClists;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sks on 2017/8/19.
 * time 2017年8月19日18:50:10
 * titile 我的模块
 */

public class XinMyFragment extends BaseFrament {
    Button touImage;
    TextView buttName;
   // @Bind(R.id.butt_fenxiangyouli)
   // Button buttFenxiangyouli;
    private LinearLayout fenxiang,lianxi,guanyu;
   private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private static final String TAG = "MainActivity";
    private Tencent mTencent;
    public static OnClists onClis;
    @Override
    protected void init(View view) {
        fenxiang = (LinearLayout) view.findViewById(R.id.fenxiang);
        lianxi = (LinearLayout) view.findViewById(R.id.lianxi);
        guanyu = (LinearLayout) view.findViewById(R.id.guanyu);
        mTencent = ExampleApplication.mTencent;
        buttName = (TextView) view.findViewById(R.id.tv_username);
        touImage = (Button) view.findViewById(R.id.button2);
        //注册登录成功广播
        IntentFilter intentFile = new IntentFilter(ExampleApplication.BORDA_ID);
        getActivity().registerReceiver(rroadrece,intentFile);
        lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据QQ号跳转聊天页面
                String url1 ="mqqwpa://im/chat?chat_type=wpa&uin="+ Iben.QQ;

                Intent i1 =new Intent(Intent.ACTION_VIEW, Uri.parse(url1));

                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i1.setAction(Intent.ACTION_VIEW);

                startActivity(i1);

                System.out.println("======我的客服QQ是多少:"+Iben.QQ);
            }
        });
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClis.getOncilick(v);
            }
        });
        //吊旗QQ登录
        touImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToastInCenter(getActivity(),1,"暂时关闭登录,谢谢合作",Toast.LENGTH_SHORT);
                //buttonLogin(v);
            }
        });
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_wode;
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(rroadrece);
        super.onDestroyView();
    }

    /**
     * 接受用户登录成功的广播
     */
    private BroadcastReceiver rroadrece = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //加载头像
           // Glide.with(getActivity()).load(intent.getExtras().getString("image")).placeholder(R.mipmap.toubiao).into(touImage);
            //用户名
            buttName.setText(intent.getExtras().getString("name")+"你好");
            Toast.makeText(context, "尊敬的"+intent.getExtras().getString("name")+"欢迎您登录领卷儿", Toast.LENGTH_SHORT).show();
            buttName.setVisibility(View.VISIBLE);
            touImage.setVisibility(View.GONE);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }


    public static  void setOnClists(OnClists onCliss) {
         onClis = onCliss;
    }


    public void buttonLogin(View v) {
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(getActivity(), "all", mIUiListener);
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
                mUserInfo = new UserInfo(getActivity(), qqToken);
                mUserInfo.getUserInfo(new BaseUIListener(getActivity(),"get_simple_userinfo"));
                ExampleApplication.IsLogin = true;
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        //finish();
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                        //finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");
                        //finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 接口回调
     * @param onClis 直接接口
     */
    public static void setOnClis(OnClists onClis) {
        XinMyFragment.onClis = onClis;
    }
}
