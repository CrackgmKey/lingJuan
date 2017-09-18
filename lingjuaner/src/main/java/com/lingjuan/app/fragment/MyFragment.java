package com.lingjuan.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lingjuan.app.R;
import com.lingjuan.app.activity.MainActivity;
import com.lingjuan.app.base.BaseFrament;
import com.lingjuan.app.base.ExampleApplication;
import com.lingjuan.app.witde.OnClists;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sks on 2017/8/19.
 * time 2017年8月19日18:50:10
 * titile 我的模块
 */

public class MyFragment extends BaseFrament {
    @Bind(R.id.tou_image)
    CircleImageView touImage;
    @Bind(R.id.butt_name)
    TextView buttName;
   // @Bind(R.id.butt_fenxiangyouli)
   // Button buttFenxiangyouli;

    public static OnClists onClis;
    @Override
    protected void init(View view) {
        //注册广播
        IntentFilter intentFile = new IntentFilter(ExampleApplication.BORDA_ID);
        getActivity().registerReceiver(rroadrece,intentFile);
/*        buttFenxiangyouli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClis.getOncilick(v);
            }
        });*/
    }
    @Override
    protected int getLayout() {
        return R.layout.framgnet_my;
    }

    /**
     * 接受用户登录成功的广播
     */
    private BroadcastReceiver rroadrece = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //加载头像
            Glide.with(getActivity()).load(intent.getExtras().getString("image")).placeholder(R.mipmap.toubiao).into(touImage);
            //用户名
            buttName.setText(intent.getExtras().getString("name")+"你好");
            Toast.makeText(context, "尊敬的"+intent.getExtras().getString("name")+"欢迎您登录领卷儿", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }


    public static  void setOnClists(OnClists onCliss) {
         onClis = onCliss;
    }
}
