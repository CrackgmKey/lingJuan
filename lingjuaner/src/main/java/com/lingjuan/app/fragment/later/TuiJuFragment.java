package com.lingjuan.app.fragment.later;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lingjuan.app.R;
import com.lingjuan.app.base.BaseFrament;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2017/8/20.
 */

public class TuiJuFragment extends BaseFrament {
    @Override
    protected void init(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_id);
        ViewPager pagerId = (ViewPager) view.findViewById(R.id.pager_id);
        pagerId.setAdapter(new MyAdaptr());
        tabLayout.setupWithViewPager(pagerId);
        //设置缓存数量
        pagerId.setOffscreenPageLimit(24);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected int getLayout() {
        return R.layout.tuiju_fragment;
    }

    class MyAdaptr extends FragmentPagerAdapter {

        String[] strs = new String[]{"推举","男装","女装","化妆品","数码家电","内衣","居家","鞋包","美食","文体","母婴"};
        List<BaseFrament> list = new ArrayList<>();
        public MyAdaptr() {
            super(getChildFragmentManager());
            list.add(new LatestFragment());
            list.add(TuijuZiFragment.newIntes(12));
            list.add(TuijuZiFragment.newIntes(10));
            list.add(TuijuZiFragment.newIntes(3));
            list.add(TuijuZiFragment.newIntes(8));
            list.add(TuijuZiFragment.newIntes(11));
            list.add(TuijuZiFragment.newIntes(4));
            list.add(TuijuZiFragment.newIntes(5));
            list.add(TuijuZiFragment.newIntes(6));
            list.add(TuijuZiFragment.newIntes(7));
            list.add(TuijuZiFragment.newIntes(2));
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

            return strs[position];
        }
    }

}
