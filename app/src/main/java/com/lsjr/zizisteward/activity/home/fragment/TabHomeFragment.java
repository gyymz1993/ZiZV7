package com.lsjr.zizisteward.activity.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.group.ui.GroupActivity;
import com.lsjr.zizisteward.activity.home.adapter.GalleryAdapter;
import com.lsjr.zizisteward.activity.product.ui.ProductListActivity;
import com.lsjr.zizisteward.activity.steward.ui.StewardActivity;
import com.lsjr.zizisteward.coustom.AutoAdjustRecylerView;
import com.lsjr.zizisteward.coustom.NoScrollViewPager;
import com.yangshao.base.BaseFragment;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.loading.BaseLoadingLayout;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yangshao.loading.LoadingController.AlertParams.Empty;
import static com.yangshao.loading.LoadingController.AlertParams.Error;
import static com.yangshao.loading.LoadingController.AlertParams.Loading;
import static com.yangshao.loading.LoadingController.AlertParams.No_Network;
import static com.yangshao.loading.LoadingController.AlertParams.Success;


public class TabHomeFragment extends BaseMvpFragment implements GroupActivity.GetDataOnclickListener, View.OnClickListener {


    private String[] titles = new String[]{"匠品", "奢品", "出行", "首页", "美食", "健康", "家族"};
    private Integer[] imageNormal = new Integer[]{R.drawable.jiangpin_normal, R.drawable.shepin_normal,
            R.drawable.travel_normal, R.drawable.home_normal, R.drawable.food_normal, R.drawable.health_normal,
            R.drawable.family_nromal};
    private Integer[] imageSelect = new Integer[]{R.drawable.jiangpin_select, R.drawable.shepin_select,
            R.drawable.travel_select, R.drawable.home_select, R.drawable.food_select, R.drawable.health_select,
            R.drawable.family_select};
    @BindView(R.id.id_recyview)
    AutoAdjustRecylerView idRecyview;
   // @BindView(R.id.id_baseloadingly)
    BaseLoadingLayout loadingly;
    @BindView(R.id.id_vp_home)
    NoScrollViewPager mVpHome;
    @BindView(R.id.id_ig_homechange)
    ImageView homeChage;
    @BindView(R.id.id_tv_city)
    TextView tvCity;

    GalleryAdapter galleryAdapter;
    BaseFragment asProductFragment,extravagancesFragment,tripFragment,fHomeFragment
    ,fineFoodFragment,tripFragment1;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void initData() {
        GroupActivity.setGetDataOnclickListener(this);
    }


    @Override
    public void initView() {
        initGallery();
        initFragments();
        initViewPager();
        initViewOnClick();
        // testLoading();

    }

    private void initViewOnClick() {
        homeChage.setOnClickListener(this);
        tvCity.setOnClickListener(this);
    }

    private List<BaseFragment> fragments = new ArrayList<>();

    private void initFragments() {
        for (int i = 0; i < titles.length; i++) {
            switch (i) {
                case 0:
                     asProductFragment = new AsProductFragment();
                    fragments.add(asProductFragment);
                    break;
                case 1:
                     extravagancesFragment = new ExtravagancesFragment();
                    fragments.add(extravagancesFragment);
                    break;
                case 2:
                     tripFragment = new TripFragment();
                    fragments.add(tripFragment);
                    break;
                case 3:
                     fHomeFragment = new HomeFragment();
                    fragments.add(fHomeFragment);
                    break;
                case 4:
                     fineFoodFragment = new FineFoodFragment();
                    fragments.add(fineFoodFragment);
                    break;
                default:
                     tripFragment1 = new TripFragment();
                    fragments.add(tripFragment1);
                    break;
            }
        }
    }

    private void initViewPager() {

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        mVpHome.setNoScroll(false);
        mVpHome.setAdapter(pagerAdapter);
        mVpHome.setCurrentItem(3);
        mVpHome.setOffscreenPageLimit(titles.length);
        mVpHome.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                galleryAdapter.setCurrentItme(position);
            }
        });
    }

    private void initGallery() {

        galleryAdapter = new GalleryAdapter(getContext().getApplicationContext(), idRecyview, titles, imageNormal, imageSelect);
        LinearLayoutManager llyanager = new LinearLayoutManager(getActivity());
        llyanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        idRecyview.setLayoutManager(llyanager);
        idRecyview.setAdapter(galleryAdapter);
        galleryAdapter.setCurrentItme(3);
        galleryAdapter.setOnItmeOnclickListener(new GalleryAdapter.OnItmeOnclickListener() {
            @Override
            public void onItemClick(int position) {
                mVpHome.setCurrentItem(position, false);
            }
        });
        //idRecyview.setOnScrollListener(mOnScrollListener);
    }




    private static final float MIN_SCALE = .95f;
    private static final float MAX_SCALE = 1.15f;
    private int mMinWidth;
    private int mMaxWidth;
    private int mScreenWidth;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            mScreenWidth = getResources().getDisplayMetrics().widthPixels;
            mMinWidth = (int) (mScreenWidth * 0.28f);
            mMaxWidth = mScreenWidth - 2 * mMinWidth;
            final int childCount = recyclerView.getChildCount();
            Log.e("tag", childCount + "");
            for (int i = 0; i < childCount; i++) {
                LinearLayout child = (LinearLayout) recyclerView.getChildAt(i);
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                lp.rightMargin = 5;
                lp.height = 200;

                int left = child.getLeft();
                int right = mScreenWidth - child.getRight();
                final float percent = left < 0 || right < 0 ? 0 : Math.min(left, right) * 1f / Math.max(left, right);
                Log.e("tag", "percent = " + percent);
                float scaleFactor = MIN_SCALE + Math.abs(percent) * (MAX_SCALE - MIN_SCALE);
                int width = (int) (mMinWidth + Math.abs(percent) * (mMaxWidth - mMinWidth));
                lp.width = width;
                child.setLayoutParams(lp);
                child.setScaleY(scaleFactor);
                if (percent > 1f / 3) {
                    ((TextView) child.getChildAt(1)).setTextColor(Color.BLUE);
                } else {
                    ((TextView) child.getChildAt(1)).setTextColor(Color.BLACK);
                }
            }
        }
    };


    @Override
    protected void initTitle() {
        // navigationBarView.setVisibility(View.GONE);
        SystemBarHelper.tintStatusBar(getActivity(), UIUtils.getColor(R.color.colorBlack));
    }

    @Override
    public void setData(String data) {
        String name = TabHomeFragment.class.getName();
        L_.e(name + data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    private void testLoading() {
        loadingly.setStatus(Loading);
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingly.setStatus(Empty);
            }
        }, 3000);

        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingly.setStatus(Error);
            }
        }, 4000);

        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingly.setStatus(No_Network);
            }
        }, 6000);

        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingly.setStatus(Success);
            }
        }, 8000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_ig_homechange:
                openActivity(StewardActivity.class);
                break;
            case R.id.id_tv_city:
                //openActivity(ClassifyActivity.class);
                openActivity(ProductListActivity.class);
                break;
        }
    }
}
