package com.lsjr.zizisteward.activity.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.home.adapter.AsProductListAdapter;
import com.lsjr.zizisteward.activity.home.presenter.AsProductPresenter;
import com.lsjr.zizisteward.activity.home.view.IAsProductView;
import com.lsjr.zizisteward.activity.product.ui.ProductDetailActivity;
import com.lsjr.zizisteward.bean.AsProductListInfo;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.yangshao.image.utils.ScaleMode;
import com.yangshao.loading.BaseLoadingLayout;
import com.yangshao.loading.LoadingController;
import com.yangshao.page.ErrorPageView;
import com.yangshao.utils.L_;
import com.ys.lib.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/6/1.
 */

public abstract class BaseProductFragment extends BaseMvpFragment<AsProductPresenter> implements IAsProductView {

    @BindView(R.id.id_rv_view)
    RecyclerView recycleView;
    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshview;
    @BindView(R.id.id_net_layout)
    LinearLayout netLayout;
    private ImageView ivOne, ivTwo;
    private LinearLayoutManager layoutManager;
    private AsProductListAdapter asProductListAdapter;
    private List<AsProductListInfo.JiangPinListInfo> mdata = new ArrayList<>();
    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD = 2;
    private int pullStatus;
    private int pageNum = 1;
    //用来标记是否正在向最后一个滑动，既是否向下滑动
    int lastVisibleItem = 0;
    private boolean isBottom = false;
    private TextView tvTitle;

    @Override
    protected AsProductPresenter createPresenter() {
        return new AsProductPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return inflater.inflate(R.layout.fragment_asproduct, null);
    }

    @Override
    protected void initView() {
        super.initView();
        asProductListAdapter = new AsProductListAdapter(getContext(), mdata);
        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recycleView.setAdapter(asProductListAdapter);
        asProductListAdapter.setHeaderView(getHeadView(), recycleView);
        asProductListAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        asProductListAdapter.setOnItemClickListener(new AsProductListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Config.PRODUCT_ID, mdata.get(position).getId());
                openActivity(ProductDetailActivity.class, bundle);
            }
        });
        xRefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullStatus = ON_REFRESH;
                        pageNum = 1;
                        loadNetData();
                    }
                }, 500);
            }
        });
        // 实现Recyclerview的滚动监听，在这里可以自己处理到达底部加载更多的操作，可以不实现onLoadMore方法，更加自由
        xRefreshview.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    L_.e("asProductListAdapter.getItemCount() " + asProductListAdapter.getItemCount());
                    L_.e("lastVisibleItem " + lastVisibleItem);
                    if (isBottom = asProductListAdapter.getItemCount() - 1 == lastVisibleItem) {
                        L_.e("加载更多");
                        pullStatus = ON_LOAD;
                        pageNum++;
                        xRefreshview.setLoadComplete(false);
                        loadNetData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });



        errorPageView.setOnReloadingListener(new ErrorPageView.OnReloadingListener() {
            @Override
            public void onReloading() {
                loadNetData();
            }
        });
    }


    TextView tvClasy;

    public View getHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.include_asproduct_head, null);
        ivOne = (ImageView) headView.findViewById(R.id.iv_one);
        ivTwo = (ImageView) headView.findViewById(R.id.iv_two);
        tvTitle = (TextView) headView.findViewById(R.id.tv_name);
        tvClasy = (TextView) headView.findViewById(R.id.id_tv_class);
        tvClasy.setOnClickListener(setOnClasslyListener());
        tvTitle.setText(getTextTitle());

        return headView;
    }

    public abstract View.OnClickListener setOnClasslyListener();

    public abstract String getTextTitle();

    public abstract void loadNetData();


    @Override
    public void onLoadAsProductListResult(String result) {
        errorPageView.showResultView(netLayout, ErrorPageView.Success);
        AsProductListInfo asProductListInfo = new Gson().fromJson(result, AsProductListInfo.class);
        List<AsProductListInfo.BannerInfo> banner = asProductListInfo.getBanner();
        ImageLoader.with().url(AppUrl.Http + banner.get(0).getImage_filename()).scale(ScaleMode.FIT_CENTER).into(ivOne);
        ImageLoader.with().url(AppUrl.Http + banner.get(1).getImage_filename()).scale(ScaleMode.FIT_CENTER).into(ivTwo);
        List<AsProductListInfo.JiangPinListInfo> products = asProductListInfo.getProducts();
        endNetRequse(products);
    }


    public void endNetRequse(List<AsProductListInfo.JiangPinListInfo> products) {
        if (pullStatus == ON_LOAD) {
            xRefreshview.stopLoadMore();
            mdata.addAll(products);
        }
        if (pullStatus == ON_REFRESH || pullStatus == 0) {
            xRefreshview.stopRefresh();
            mdata = products;
        }
        asProductListAdapter.notifyDataSetChanged(mdata);
        pullStatus = 0;
    }

    @Override
    public void onError() {
        endErrorNetRequse();
    }

    public void endErrorNetRequse() {
        L_.e("onError");
        if (pullStatus == ON_LOAD) {
            xRefreshview.stopLoadMore();
        }
        if (pullStatus == ON_REFRESH || pullStatus == 0) {
            xRefreshview.stopRefresh();
        }
        if (mdata.size() == 0) {
            errorPageView.showResultView(netLayout, ErrorPageView.NoContext,"没有内容哦");
        }
        pullStatus = 0;
    }

}
