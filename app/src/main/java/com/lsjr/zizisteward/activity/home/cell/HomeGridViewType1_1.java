package com.lsjr.zizisteward.activity.home.cell;

import com.lsjr.zizisteward.activity.home.adapter.RecycleViewTypeAdapter;
import com.lsjr.zizisteward.activity.home.cell.HomeGridViewCell1;
import com.lsjr.zizisteward.bean.HomeBean;

import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */

public  class HomeGridViewType1_1 extends HomeGridViewCell1 {

    public HomeGridViewType1_1(List<HomeBean.DiligentRecommendBean> diligentRecommendBeans) {
        super(diligentRecommendBeans);
    }


    @Override
    protected RecycleViewTypeAdapter getViewTypeAdapter() {
        return new RecycleViewTypeAdapter(getItemType(),mData);
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
