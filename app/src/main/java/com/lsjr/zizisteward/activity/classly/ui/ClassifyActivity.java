package com.lsjr.zizisteward.activity.classly.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.classly.adapter.TitlePagerAdapter;
import com.lsjr.zizisteward.activity.classly.fragment.ClasslyFragment;
import com.lsjr.zizisteward.activity.classly.presenter.ClasslyPresenter;
import com.lsjr.zizisteward.activity.classly.view.IClasslyView;
import com.lsjr.zizisteward.activity.group.ui.GroupActivity;
import com.lsjr.zizisteward.bean.AsProductBean;
import com.lsjr.zizisteward.bean.ProductType;
import com.yangshao.base.BaseFragment;
import com.yangshao.customview.ColorTrackTabViewIndicator;
import com.yangshao.customview.ColorTrackView;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.title.NavigationBarView;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/5/13.
 */

public class ClassifyActivity extends BaseMvpActivity<ClasslyPresenter> implements GroupActivity.GetDataOnclickListener,IClasslyView {
    private String[] titles ;

    @BindView(R.id.id_bar_view)
    NavigationBarView idBarView;

    @BindView(R.id.tab)
    ColorTrackTabViewIndicator indicatroViewp;
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;

    private String classType;
    @Override
    protected ClasslyPresenter createPresenter() {
        return new ClasslyPresenter(this);
    }

    @Override
    protected void initData() {
        loadAllClasslyforNet();

    }

    public void  loadAllClasslyforNet(){
        Bundle bundle=getIntent().getExtras();
        classType=bundle.getString(Config.CLASSIC_TYPE);
        HashMap<String, String> map = new HashMap<>();
        if (classType!=null){
            if (classType.equals("AS")){
                map.put("OPT", "421");
            } if (classType.equals("EX")) {
                map.put("OPT", "421");
            }
        }
        map.put("currPage", "1");
        createPresenter().loadAllClasslyforNet(map);
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initTitle() {
        SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.colorBlack));
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.activity_classly;
    }


    @Override
    public void setData(String data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadDataSucceed(String data) {

    }

    @Override
    public void onLoadClassfyResult(String result) {
        AsProductBean bean = new Gson().fromJson(result, AsProductBean.class);
                L_.e("bean.getProductType().size()"+bean.getProductType().size());
        setClassFragment(bean.getProductType());
        L_.e(result);
//        if (classType!=null){
//            if (classType.equals("AS")){
//                AsProductBean bean = new Gson().fromJson(result, AsProductBean.class);
//                List<AsProductBean.FamousTypeBean> productType = bean.getFamousType();
//                setClassFragment(productType);
//            } if (classType.equals("EX")) {
//                AsProductBean bean = new Gson().fromJson(result, AsProductBean.class);
//                List<AsProductBean.FamousTypeBean> productType = bean.getFamousType();
//                setClassFragment(productType);
//            }
//        }
    }

    public void setClassFragment(List<ProductType> productType){
        List<BaseFragment> fragments = new ArrayList<>();
        titles=new String[productType.size()];
        for (int i = 0; i < titles.length; i++) {
            titles[i]=productType.get(i).getTname();
            ClasslyFragment fragment = new ClasslyFragment();
            fragments.add(fragment);
        }
        indicatroViewp.setTitles(titles, new ColorTrackTabViewIndicator.CorlorTrackTabBack() {
            @Override
            public void onClickButton(Integer position, ColorTrackView colorTrackView) {
                //mViewPager.setCurrentItem(position,false);
            }
        });
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager(), fragments, titles));
        indicatroViewp.setupViewPager(mViewPager);
    }
}
