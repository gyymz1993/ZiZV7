package com.lsjr.zizisteward.activity.scope.presenter;

import com.lsjr.zizisteward.activity.scope.view.IBusinessCardView;
import com.lsjr.zizisteward.http.DcodeService;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.HashMap;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/7 12:45
 */

public class BusinessCardPresenter extends BasePresenter<IBusinessCardView> {
    public BusinessCardPresenter(IBusinessCardView mvpView) {
        super(mvpView);
    }


    public void loadDetailsForNet(final HashMap<String, String> map) {
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
                mvpView.onLoadDetailResult(response);
            }
        });
    }
}
