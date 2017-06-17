package com.lsjr.zizisteward.activity.home.presenter;

import com.lsjr.zizisteward.activity.home.view.IAsProductView;
import com.lsjr.zizisteward.activity.home.view.IHomeView;
import com.lsjr.zizisteward.http.DcodeService;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.Map;

/**
 * Created by admin on 2017/5/16.
 */

public class AsProductPresenter extends BasePresenter<IAsProductView> {
    public AsProductPresenter(IAsProductView mvpView) {
        super(mvpView);
    }

    public void loadAsPorductListForNet(final Map<String,String> map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                T_.showToastReal("请求失败");
                mvpView.onError();
            }

            @Override
            protected void onFailure(String response) {
                mvpView.onError();
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onLoadAsProductListResult(response);
            }

        });

    }


}
