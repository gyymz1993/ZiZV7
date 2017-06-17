package com.lsjr.zizisteward.activity.home.presenter;

import com.lsjr.zizisteward.activity.home.view.IFineFoodView;
import com.lsjr.zizisteward.http.DcodeService;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.Map;

/**
 * Created by admin on 2017/6/5.
 */

public class FineFoodPresenter extends BasePresenter<IFineFoodView> {
    public FineFoodPresenter(IFineFoodView mvpView) {
        super(mvpView);
    }

    public void loadDataForNet(Map<String,String> map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                mvpView.onError();
            }

            @Override
            protected void onFailure(String response) {
                mvpView.onError();
            }

            @Override
            protected void onSuccess(String response) {
                L_.e(response);
                mvpView.onLoadDataResult(response);
            }

        });

    }
}
