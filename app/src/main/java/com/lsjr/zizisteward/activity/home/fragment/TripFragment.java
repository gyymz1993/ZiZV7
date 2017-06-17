package com.lsjr.zizisteward.activity.home.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;


public class TripFragment  extends BaseMvpFragment {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return inflater.inflate(R.layout.fragment_trip,null);
    }
}
