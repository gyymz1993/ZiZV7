package com.lsjr.zizisteward.activity.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.home.cell.HomeGridViewType1_1;
import com.lsjr.zizisteward.activity.home.cell.HomeGridViewType1_3;
import com.lsjr.zizisteward.activity.home.cell.HomeGridViewType1_4;
import com.lsjr.zizisteward.activity.home.cell.HomeImageViewCell2;
import com.lsjr.zizisteward.activity.home.cell.HomeTextMoreCell5;
import com.lsjr.zizisteward.activity.home.presenter.HomePresenter;
import com.lsjr.zizisteward.activity.home.view.IHomeView;
import com.lsjr.zizisteward.bean.HomeBean;
import com.lsjr.zizisteward.coustom.MySwipeRefreshLayout;
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
import com.youth.banner.loader.ImageLoader;
import com.ys.base.RVBaseAdapter;
import com.ys.base.RVBaseCell;
import com.ys.base.RVBaseViewHolder;
import com.ys.lib.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.yangshao.loading.LoadingController.AlertParams.Empty;
import static com.yangshao.loading.LoadingController.AlertParams.Error;
import static com.yangshao.loading.LoadingController.AlertParams.Loading;
import static com.yangshao.loading.LoadingController.AlertParams.No_Network;
import static com.yangshao.loading.LoadingController.AlertParams.Success;

/**
 * Created by admin on 2017/5/16.O
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements IHomeView, OnBannerListener {

    @BindView(R.id.id_swpe_refresh_lay)
    MySwipeRefreshLayout refreshLayout;
    @BindView(R.id.gridview_recommend)
    WrapRecyclerView gridCyclerView;

    @BindView(R.id.id_net_layout)
    LinearLayout netLayout;

    @BindView(R.id.id_loadview)
    BaseLoadingLayout loadingLayout;


    /*头部banner*/
    private Banner banner;
    /*底部ImageView*/
    ImageView foodImageView;
    private HomeBean.ShouImgBean shouImg;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return inflater.inflate(R.layout.fragment_f1_home, null);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initGridCycleView() {
        LinearLayoutManager llyanager = new LinearLayoutManager(getActivity());
        llyanager.setOrientation(LinearLayoutManager.VERTICAL);
        gridCyclerView.addHeaderView(getHeaderView());
        gridCyclerView.addFooterView(getFoodView());
        gridCyclerView.setLayoutManager(llyanager);
        gridCyclerView.setHasFixedSize(true);

        gridCyclerView.setAdapter(rvBaseAdapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoadNetData();
            }
        });
        errorPageView.setOnReloadingListener(new ErrorPageView.OnReloadingListener() {
            @Override
            public void onReloading() {
                getLoadNetData();
            }
        });

    }

    RVBaseAdapter rvBaseAdapter = new RVBaseAdapter() {
        @Override
        protected void onViewHolderBound(RVBaseViewHolder holder, int position) {
        }
    };

    /*头部添加Banner*/
    private View getHeaderView() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.inculed_home_head_banner, null);
        banner = (Banner) headView.findViewById(R.id.banner);
        TextView tvChange = (TextView) headView.findViewById(R.id.id_tv_change);
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChangeRecommend();
            }
        });
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.WHD()[0] * 3 / 5));
        return headView;

    }


    private View getFoodView() {
        View foodView = LayoutInflater.from(getContext()).inflate(R.layout.include_home_showimg, null);
        foodImageView = (ImageView) foodView.findViewById(R.id.iv_ziwei);
        RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) foodImageView.getLayoutParams();
        linearParams2.width = UIUtils.WHD()[0];
        linearParams2.height = UIUtils.WHD()[0] / 2;
        foodImageView.setLayoutParams(linearParams2);
        // 孜味天下图片
        return foodView;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getLoadNetData();
    }


    @Override
    protected void initView() {
        initGridCycleView();
    }


    public void getLoadNetData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("OPT", "24");
        map.put("currPage", "1");
        map.put("name", "");
        map.put("city_id", "420100");
        createPresenter().getHomePager(map);
    }

    /*更新推荐列表*/
    public void getChangeRecommend() {
        HashMap<String, String> map = new HashMap<>();
        map.put("OPT", "82");
        map.put("name", "");
        createPresenter().loadChangeHomePager(map);
    }


    @Override
    public void loadChangePagerResult(String result) {
        HomeBean bean = new Gson().fromJson(result, HomeBean.class);
        /*推荐列表 可刷新*/
        recommends = bean.getDiligent_recommend();
        L_.e("改变图片：" + recommends.size());
        //rvBaseAdapter.notifyItemChanged(3);
    }



    List<HomeBean.DiligentRecommendBean> recommends;

    @Override
    public void getPageDataSucceed(String result) {
       // errorPageView.showResultView(gridCyclerView, ErrorPageView.Success);
        loadingLayout.setStatus(Success);
        refreshLayout.setRefreshing(false);
        HomeBean bean = new Gson().fromJson(result, HomeBean.class);
        /*banner*/
        List<HomeBean.AdvertisementsBean> advertisements = bean.getAdvertisements();

        /*推荐列表 可刷新*/
        recommends = bean.getDiligent_recommend();
        /*美食推荐*/
        List<HomeBean.HomePageMapDataBean.DiligentFoodBean> curentDiligentFood = bean.getHomePageMapData().getDiligentFood();
        final List<HomeBean.DiligentRecommendBean> diligentFood = new ArrayList<>();

        /*推荐更多  孜孜臻品*/
        List<HomeBean.HomePageMapDataBean.DiligentPierreBean> curentdiligentPierre = bean.getHomePageMapData().getDiligentPierre();
        final List<HomeBean.DiligentRecommendBean> diligentPierre = new ArrayList<>();

        /*孜孜天下图片显示*/
        shouImg = bean.getShouImg();

        //精选活动图片
        // 精选活动图片
        String diligentActivity = bean.getShouImg().getDiligentActivity();

        /*Banner图片*/
        List<String> bannerImag = new ArrayList<>();
        for (int position = 0; position < advertisements.size(); position++) {
            bannerImag.add(AppUrl.Http + advertisements.get(position).getImage_filename());
        }
        //开始轮播
        banner.setImages(bannerImag)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this).start();//.startAutoPlay();

        /*自定义RecycleView中多类型数据*/
        List<RVBaseCell> cells = new ArrayList<>();
        final HomeGridViewType1_1 homeGridViewCell = new HomeGridViewType1_1(recommends);
        cells.add(homeGridViewCell);

        HomeImageViewCell2 imageViewCell = new HomeImageViewCell2(shouImg);
        cells.add(imageViewCell);

        for (int i = 0; i < curentDiligentFood.size(); i++) {
            HomeBean.DiligentRecommendBean recommendBean = new HomeBean.DiligentRecommendBean();
            recommendBean.setSname(curentDiligentFood.get(i).getSname());
            recommendBean.setSkeyword(curentDiligentFood.get(i).getSkeyword());
            recommendBean.setSpicfirst(curentDiligentFood.get(i).getSpicfirst());
            diligentFood.add(recommendBean);
        }
        HomeGridViewType1_3 homeGridViewCell3 = new HomeGridViewType1_3(diligentFood);
        cells.add(homeGridViewCell3);


        HomeTextMoreCell5 homeTextMoreCell5 = new HomeTextMoreCell5("more");
        cells.add(homeTextMoreCell5);


        for (int i = 0; i < curentdiligentPierre.size(); i++) {
            HomeBean.DiligentRecommendBean recommendBean = new HomeBean.DiligentRecommendBean();
            recommendBean.setSname(curentdiligentPierre.get(i).getSname());
            recommendBean.setSkeyword(curentdiligentPierre.get(i).getSkeyword());
            recommendBean.setSpicfirst(curentdiligentPierre.get(i).getSpicfirst());
            diligentPierre.add(recommendBean);
        }
        HomeGridViewType1_4 homeGridViewCell4 = new HomeGridViewType1_4(diligentPierre);
        cells.add(homeGridViewCell4);

        //HomeImageViewCell2_6 imageViewCell2_6 = new HomeImageViewCell2_6(shouImg);
        // cells.add(imageViewCell2_6);
        com.yangshao.image.ImageLoader.with().url(AppUrl.Http + shouImg.getDiligentActivity()).into(foodImageView);
        rvBaseAdapter.setData(cells);

    }


    @Override
    public void onError() {
        refreshLayout.setRefreshing(false);
        loadingLayout.setStatus(Error);
        //errorPageView.showResultView(gridCyclerView, ErrorPageView.NetError);
    }


    @Override
    public void OnBannerClick(int position) {

    }


    @Override
    public void onDestroy() {
        //结束轮播
        banner.stopAutoPlay();
        super.onDestroy();
    }

//    private void testLoading() {
//        loadingly.setStatus(Loading);
//        UIUtils.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadingly.setStatus(Empty);
//            }
//        }, 3000);
//
//        UIUtils.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadingly.setStatus(Error);
//            }
//        }, 4000);
//
//        UIUtils.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                loadingly.setStatus(No_Network);
//            }
//        }, 6000);
//
//        UIUtils.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadingly.setStatus(Success);
//            }
//        }, 8000);
//
//        loadingly.setOnReloadListener(new BaseLoadingLayout.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                getLoadNetData();
//            }
//        });
//    }


}
