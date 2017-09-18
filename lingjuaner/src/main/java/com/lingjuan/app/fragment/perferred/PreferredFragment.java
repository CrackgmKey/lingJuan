package com.lingjuan.app.fragment.perferred;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.PointerIcon;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lingjuan.app.R;
import com.lingjuan.app.activity.MainActivity;
import com.lingjuan.app.base.BaseFrament;
import com.lingjuan.app.fragment.BaoYouZiSaleFragment;
import com.lingjuan.app.fragment.later.LatestFragment;
import com.lingjuan.app.fragment.later.TuijuZiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2017/8/19.
 * time 2017年8月19日18:50:10
 * titile 优选模块
 */

public class PreferredFragment extends BaseFrament {
    private ViewPager viewpager;
    private TabLayout tabLayout;
    private TabItem tabItem1,tabItem2,tabItem3;
    private String [] imge = {"9.9包邮","19.9包邮","29.9包邮"};
    @Override
    protected void init(View view) {

        viewpager = (ViewPager)view.findViewById(R.id.fl_id);
        //构建Adapter
      //  MyPageAdapter mypag = new MyPageAdapter(MainActivity.fragmentManager);
        //填充
        viewpager.setAdapter(new MyAdaptr());
        viewpager.setOffscreenPageLimit(4);
        viewpager.setCurrentItem(0,false);
        tabLayout = (TabLayout)view. findViewById(R.id.tablayut);
        //取消tab下面的长横线
        //tablayout标题点击事件
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    protected int getLayout() {
        return R.layout.framgnet_preferred;
    }

    class MyAdaptr extends FragmentPagerAdapter {

        List<BaseFrament> list = new ArrayList<>();
        public MyAdaptr() {
            super(getChildFragmentManager());
            list.add(BaoYouZiSaleFragment.newIntes(1,10));
            list.add(BaoYouZiSaleFragment.newIntes(10,20));
            list.add(BaoYouZiSaleFragment.newIntes(20,99));
        }

        @Override
        public BaseFrament getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return imge[position];
        }
    }

}
