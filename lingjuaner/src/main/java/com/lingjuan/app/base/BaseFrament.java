package com.lingjuan.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;

/**
 * Created by sks on 2017/8/19.
 */

public abstract class BaseFrament extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);

    }

    /**
     * 返回初始化布局
     */
    protected abstract void init(View view);

    /**
     * 设置根布局资源id
     * @author 漆可
     * @date 2016-5-26 下午3:57:09
     * @return
     */
    protected abstract int getLayout();


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
