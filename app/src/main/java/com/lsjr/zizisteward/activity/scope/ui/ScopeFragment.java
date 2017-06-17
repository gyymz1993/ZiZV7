package com.lsjr.zizisteward.activity.scope.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.scope.fragment.ShiJieFragment;
import com.lsjr.zizisteward.activity.scope.fragment.ZiShangFragment;
import com.yangshao.indicator.ColorTransitionPagerTitleView;
import com.yangshao.indicator.CommonNavigator;
import com.yangshao.indicator.CommonNavigatorAdapter;
import com.yangshao.indicator.IPagerIndicator;
import com.yangshao.indicator.IPagerTitleView;
import com.yangshao.indicator.LinePagerIndicator;
import com.yangshao.indicator.MagicIndicator;
import com.yangshao.indicator.SimplePagerTitleView;
import com.yangshao.indicator.ViewPagerHelper;
import com.ys.lib.base.BaseMvpFragment;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 视界
 */

public class ScopeFragment extends BaseMvpFragment {


    @BindView(R.id.id_mgindicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.all_page)
    ViewPager allPage;

    private String[] titles = new String[]{"时视", "孜赏"};
    private ViewPagerAdapter adapter;
    private List<Fragment> fagments = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        ZiShangFragment ziShangFrag = new ZiShangFragment();
        ShiJieFragment wordFrag = new ShiJieFragment();
        fagments.add(wordFrag);
        fagments.add(ziShangFrag);

        //Fragment中嵌套ViewPager 最关键的地方是这里
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        allPage.setAdapter(adapter);
        initMagicIndicator();

    }


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_scope, null);
    }


    public void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(getContext().getApplicationContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fagments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(getContext().getApplicationContext());
                simplePagerTitleView.setNormalColor(Color.parseColor("#929292"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        allPage.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(getContext().getApplicationContext());
                linePagerIndicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, allPage);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fagments.get(position);
        }

        @Override
        public int getCount() {
            return fagments.size();
        }
    }


}
