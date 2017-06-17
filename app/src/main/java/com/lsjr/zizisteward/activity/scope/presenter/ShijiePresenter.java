package com.lsjr.zizisteward.activity.scope.presenter;

import com.lsjr.zizisteward.activity.scope.view.IShijieView;
import com.lsjr.zizisteward.http.DcodeService;
import com.yangshao.utils.L_;
import com.ys.lib.base.BasePresenter;
import com.ys.lib.base.SubscriberCallBack;

import java.util.Map;

/**
 * Created by admin on 2017/6/5.
 */

public class ShijiePresenter extends BasePresenter<IShijieView> {
    public ShijiePresenter(IShijieView mvpView) {
        super(mvpView);
    }


    public void loadHeadforNet(Map map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                L_.e("loadHeadforNet  onError");
                mvpView.onError();
            }

            @Override
            protected void onFailure(String response) {
                L_.e("loadHeadforNet  onError");
                mvpView.onError();
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onLoadHeadResult(response);
            }
        });
    }


    public void loadMackUnCollect(Map map){
        addSubscription(DcodeService.getServiceData(map), new SubscriberCallBack() {
            @Override
            protected void onError(Exception e) {
                L_.e("loadMackUnCollect  onError");
                mvpView.onError();
            }

            @Override
            protected void onFailure(String response) {
                L_.e("loadMackUnCollect  onError");
                mvpView.onError();
            }

            @Override
            protected void onSuccess(String response) {
                mvpView.onMackCollectResult(response);
            }
        });
    }
}
