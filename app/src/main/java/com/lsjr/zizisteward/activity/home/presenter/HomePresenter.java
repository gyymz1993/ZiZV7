package com.lsjr.zizisteward.activity.home.presenter;

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

public class HomePresenter extends BasePresenter<IHomeView> {
    public HomePresenter(IHomeView mvpView) {
        super(mvpView);
    }




    public void getHomePager(Map map) {
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {

            @Override
            protected void onSuccess(String response) {
                mvpView.getPageDataSucceed(response);
            }


            @Override
            protected void onError(Exception e) {
                mvpView.onError();
            }

            @Override
            protected void onFailure(String response) {
                mvpView.onError();
            }

        });
    }

    public void loadChangeHomePager(Map map) {
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onSuccess(String response) {
                mvpView.loadChangePagerResult(response);
            }


            @Override
            protected void onError(Exception e) {
                mvpView.onError();
            }

            @Override
            protected void onFailure(String response) {
                mvpView.onError();
            }

        });
    }
}
