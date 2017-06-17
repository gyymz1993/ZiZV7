package com.lsjr.zizisteward.activity.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.home.adapter.FineFoodAdapter;
import com.lsjr.zizisteward.activity.home.presenter.FineFoodPresenter;
import com.lsjr.zizisteward.activity.home.view.IFineFoodView;
import com.lsjr.zizisteward.bean.FineFoodBean;
import com.lsjr.zizisteward.coustom.myrecycleview.WrapRecyclerView;
import com.lsjr.zizisteward.http.AppUrl;
import com.lsjr.zizisteward.utils.GlideImageLoader;
import com.yangshao.loading.BaseLoadingLayout;
import com.yangshao.loading.LoadingController;
import com.yangshao.page.ErrorPageView;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.ys.base.RVBaseAdapter;
import com.ys.base.RVBaseViewHolder;
import com.ys.lib.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by admin on 2017/6/2.
 */

public class FineFoodFragment extends BaseMvpFragment<FineFoodPresenter> implements IFineFoodView, OnBannerListener {
    @BindView(R.id.id_recyview)
    WrapRecyclerView idRecyview;
    @BindView(R.id.id_net_layout)
    LinearLayout netLayout;

    List<FineFoodBean.Cate> fhBeanCate = new ArrayList<>();

    @Override
    protected FineFoodPresenter createPresenter() {
        return new FineFoodPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_finefood, null);
    }

    Banner banner;

    /*头部添加Banner*/
    private View getHeaderView() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.inculed_home_head_banner, null);
        banner = (Banner) headView.findViewById(R.id.banner);
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.WHD()[0] * 3 / 5));
        return headView;

    }

    FineFoodAdapter fAdapter;

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager llyanager = new LinearLayoutManager(getActivity());
        llyanager.setOrientation(LinearLayoutManager.VERTICAL);
        idRecyview.addHeaderView(getHeaderView());
        idRecyview.setLayoutManager(llyanager);
        idRecyview.setHasFixedSize(true);
        fAdapter = new FineFoodAdapter(getContext().getApplicationContext(), fhBeanCate);
        idRecyview.setAdapter(fAdapter);

        errorPageView.setOnReloadingListener(new ErrorPageView.OnReloadingListener() {
            @Override
            public void onReloading() {
                loadDataForNet();
            }
        });

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadDataForNet();
    }


    public void loadDataForNet() {
        Map<String, String> map = new HashMap<>();
        map.put("OPT", "411");
        map.put("currPage", "1");
        map.put("ad_city_id", "420100");
        L_.e("loadDataForNet");
        createPresenter().loadDataForNet(map);

    }

    @Override
    public void onLoadDataResult(String result) {
        errorPageView.showResultView(netLayout, ErrorPageView.Success);
        FineFoodBean fhBean = new Gson().fromJson(result, FineFoodBean.class);
        fhBeanCate.addAll(fhBean.getCate());
        List<FineFoodBean.Catebanner> catebanner = fhBean.getCatebanner();
         /*Banner图片*/
        List<String> bannerImag = new ArrayList<>();
        for (int position = 0; position < catebanner.size(); position++) {
            bannerImag.add(AppUrl.Http + catebanner.get(position).getSpic());
        }
        //开始轮播
        banner.setImages(bannerImag)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this).start();//.startAutoPlay();
        fAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        errorPageView.showResultView(netLayout, ErrorPageView.NetError);
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
