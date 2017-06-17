package com.lsjr.zizisteward.activity.scope.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

/**
 * Created by admin on 2017/5/8.
 */

public class ZiShangFragment  extends BaseMvpFragment {



    protected View getCenterView() {
        return null;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zishang, null);
    }



}
