package com.easeui.home.base;

import android.os.Bundle;
import android.view.View;

import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

public abstract class EaseBaseFragment<P extends BasePresenter> extends BaseMvpFragment{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setUpView();
    }

    protected abstract void initView();
    
    protected abstract void setUpView();


}
