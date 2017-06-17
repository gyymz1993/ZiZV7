package com.lsjr.zizisteward.activity.home.cell;

import com.lsjr.zizisteward.activity.home.cell.HomeImageViewCell2;
import com.lsjr.zizisteward.bean.HomeBean;

/**
 * Created by admin on 2017/6/2.
 */

public class HomeImageViewCell2_6 extends HomeImageViewCell2 {

    public static final int TYPE = 6;
    public HomeImageViewCell2_6(HomeBean.ShouImgBean shouImgBean) {
        super(shouImgBean);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }


}
