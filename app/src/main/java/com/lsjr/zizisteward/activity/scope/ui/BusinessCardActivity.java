package com.lsjr.zizisteward.activity.scope.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.classly.adapter.SpacesItemDecoration;
import com.lsjr.zizisteward.activity.scope.adapter.BusinessCardAdapter;
import com.lsjr.zizisteward.activity.scope.presenter.BusinessCardPresenter;
import com.lsjr.zizisteward.activity.scope.view.IBusinessCardView;
import com.lsjr.zizisteward.activity.utils.LevelUtils;
import com.lsjr.zizisteward.bean.BusinessCardBean;
import com.lsjr.zizisteward.http.AppUrl;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.image.ImageLoader;
import com.ys.lib.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/7 12:42
 * <p>
 * 名人榜名片
 **/

public class BusinessCardActivity extends BaseMvpActivity<BusinessCardPresenter> implements IBusinessCardView {

//    @BindView(R.id.re_back)
//    RelativeLayout reBack;
    @BindView(R.id.iv_name_photo)
    ImageView ivNamePhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
//    @BindView(R.id.iv_level)
//    ImageView ivLevel;
//    @BindView(R.id.ll_beijing)
//    LinearLayout llBeijing;
    private String userId;
    private int pageNum = 1;
    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD = 2;
    private int pullStatus;
    private BusinessCardAdapter adapter;
    List<BusinessCardBean.SharesList> mData = new ArrayList<>();
    @BindView(R.id.id_recyview)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BusinessCardPresenter createPresenter() {
        return new BusinessCardPresenter(this);
    }


    @Override
    protected void initTitle() {
        super.initTitle();
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle extras = getIntent().getExtras();
        userId = extras.getString(Config.USER_ID);
        adapter = new BusinessCardAdapter(mData, R.layout.item_businesscard);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {
        super.initData();
        loadDetailsForNet();
    }

    public void loadDetailsForNet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("OPT", "80");
        map.put("user_id", Config.USER_ID);
        map.put("currPage", String.valueOf(pageNum));
        createPresenter().loadDetailsForNet(map);
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.include_activity_bus_header;
    }


    @Override
    public void onLoadDetailResult(String result) {
        BusinessCardBean cardBean = new Gson().fromJson(result, BusinessCardBean.class);
        List<BusinessCardBean.SharesList> page = cardBean.getShares().getPage();
        initTitleView(cardBean);
        endNetRequse(page);

    }

    private void initTitleView(BusinessCardBean cardBean) {
        ImageLoader.with().url(AppUrl.Http+cardBean.getCounts().getPhoto()).asCircle().into(ivNamePhoto);
        tvName.setText(cardBean.getCounts().getUser_name());
      //  LevelUtils.setLevelIcon(cardBean.getCounts().getIdentityType(),ivLevel);
    }

    public void endNetRequse(List<BusinessCardBean.SharesList> page) {
        if (pullStatus == ON_LOAD) {
            //xRefreshView.stopLoadMore();
            mData.addAll(page);
        }
        if (pullStatus == ON_REFRESH || pullStatus == 0) {
            //xRefreshView.stopRefresh();
            mData = page;
        }
        adapter.addToFirst(page);
        pullStatus = 0;
    }

    @Override
    public void onError() {

    }
}
