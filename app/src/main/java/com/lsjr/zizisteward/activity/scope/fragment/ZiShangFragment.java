package com.lsjr.zizisteward.activity.scope.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.scope.adapter.ShiJieAdapter;
import com.lsjr.zizisteward.activity.scope.adapter.ZiShangAdapter;
import com.lsjr.zizisteward.activity.scope.presenter.ShijiePresenter;
import com.lsjr.zizisteward.activity.scope.ui.BusinessCardActivity;
import com.lsjr.zizisteward.activity.scope.view.IShijieView;
import com.lsjr.zizisteward.activity.utils.LevelUtils;
import com.lsjr.zizisteward.bean.ShiJieBean;
import com.lsjr.zizisteward.coustom.LazyLoadRecycleView;
import com.lsjr.zizisteward.coustom.vip.IdentityImageView;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.yangshao.page.ErrorPageView;
import com.yangshao.utils.L_;
import com.ys.lib.base.BaseMvpFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/5/8.
 */

public class ZiShangFragment extends BaseMvpFragment<ShijiePresenter> implements IShijieView {


    @BindView(R.id.id_recyview)
    LazyLoadRecycleView recyclerView;

    @BindView(R.id.id_xrefresh)
    XRefreshView xRefreshView;
    @BindView(R.id.id_net_layout)
    LinearLayout netLayout;


    HorizontalScrollView horizontalScrollView;
    LinearLayout ll_parent;
    ZiShangAdapter ziShangAdapter;
    List<ShiJieBean.ShiJieListDetail> mData = new ArrayList<>();
    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD = 2;
    private int pullStatus;
    private int pageNum = 1;
    //用来标记是否正在向最后一个滑动，既是否向下滑动
    int lastVisibleItem = 0;
    private boolean isBottom = false;
    private TextView tvTitle;
    private int currentPostion;//当前选中的  adapter中选择的Item下标


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        final LinearLayoutManager llyanager = new LinearLayoutManager(getActivity());
        llyanager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.addHeaderView(getHeaderView());
        recyclerView.setLayoutManager(llyanager);
        //recyclerView.setItemAnimator(new MyMainItemAnimation());
        //recyclerView.setHasFixedSize(true);
        ziShangAdapter = new ZiShangAdapter(getContext().getApplicationContext(), mData);
        //shiJieAdapter.setHasStableIds(true);
        recyclerView.setAdapter(ziShangAdapter);
        ziShangAdapter.setHeaderView(getHeaderView(), recyclerView);
        ziShangAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullStatus = ON_REFRESH;
                        pageNum = 1;
                        loadHeadforNet();
                    }
                }, 500);
            }
        });

        // 实现Recyclerview的滚动监听，在这里可以自己处理到达底部加载更多的操作，可以不实现onLoadMore方法，更加自由
        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isBottom = ziShangAdapter.getItemCount() - 1 == lastVisibleItem) {
                        pullStatus = ON_LOAD;
                        pageNum++;
                        xRefreshView.setLoadComplete(false);
                        loadHeadforNet();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = llyanager.findLastVisibleItemPosition();
            }
        });


        errorPageView.setOnReloadingListener(new ErrorPageView.OnReloadingListener() {
            @Override
            public void onReloading() {
                loadHeadforNet();
            }
        });

    }


    @Override
    public void onMackCollectResult(String result) {
        L_.e("onMackCollectResult" + result);
        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(result);
            String msg = jsonObject.getString("msg");
            if (mData.get(currentPostion).isIscollect()) {
                mData.get(currentPostion).setIs_collect("0");
                mData.get(currentPostion).setIscollect(false);
            } else {
                mData.get(currentPostion).setIs_collect("1");
                mData.get(currentPostion).setIscollect(true);
            }
            ziShangAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {
        endErrorNetRequse();
    }

    public void endErrorNetRequse() {
        if (pullStatus == ON_LOAD) {
            xRefreshView.stopLoadMore();
        }
        if (pullStatus == ON_REFRESH || pullStatus == 0) {
            xRefreshView.stopRefresh();
        }
        pullStatus = 0;
        if (mData.size() == 0) {

        }
        errorPageView.showResultView(netLayout, ErrorPageView.NetError);

    }

    public void endNetRequse(List<ShiJieBean.ShiJieListDetail> page) {
        errorPageView.showResultView(netLayout, ErrorPageView.Success);
        if (pullStatus == ON_LOAD) {
            xRefreshView.stopLoadMore();
            mData.addAll(page);
        }
        if (pullStatus == ON_REFRESH || pullStatus == 0) {
            xRefreshView.stopRefresh();
            mData = page;
        }
        ziShangAdapter.notifyDataSetChanged(mData);
        pullStatus = 0;
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadHeadforNet();
    }


    public void loadHeadforNet() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("OPT", "87");
        //map.put("user_id", EncryptUtils.addSign(Integer.parseInt("-1"), "u"));
        map.put("user_id", Config.USER_ID);
        map.put("currPage", pageNum + "");
        map.put("identity_type", "");
        createPresenter().loadHeadforNet(map);

    }

    @Override
    public void onLoadHeadResult(String result) {
        ShiJieBean bean = new Gson().fromJson(result, ShiJieBean.class);
        List<ShiJieBean.FamousUsers> users = bean.getUsers();
        initHeadViewData(users);
        List<ShiJieBean.ShiJieListDetail> page = bean.getSight().getPage();
        endNetRequse(page);
    }


    private void initHeadViewData(List<ShiJieBean.FamousUsers> users) {
        ll_parent.removeAllViews();
        for (int i = 0; i < users.size(); i++) {
            L_.e(users.size() + ":" + users.get(i).toString());
            View view = getHeaderItem();
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            IdentityImageView imageView = (IdentityImageView) view.findViewById(R.id.id_ident_view);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(users.get(i).getUser_name());
            ImageLoader.with().url(AppUrl.Http + users.get(i).getPhoto())
                    .into(imageView.getBigCircleImageView());
            LevelUtils.setLevelIcon(users.get(i).getCredit_level_id(), imageView);
            ll_parent.addView(view);
        }
    }


    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString(Config.USER_ID, mData.get(v.getId()).getId());
            openActivity(BusinessCardActivity.class, bundle);

        }
    };

    public View getHeaderItem() {
        return LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_shijie_image, null);
    }

    public View getHeaderView() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.include_shijie_head, null);
        horizontalScrollView = (HorizontalScrollView) headView.findViewById(R.id.id_hsv_contain);
        ll_parent = (LinearLayout) headView.findViewById(R.id.id_ll_parent);
        return headView;

    }


    @Override
    protected ShijiePresenter createPresenter() {
        return new ShijiePresenter(this);
    }


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zishang, null);
    }


}
