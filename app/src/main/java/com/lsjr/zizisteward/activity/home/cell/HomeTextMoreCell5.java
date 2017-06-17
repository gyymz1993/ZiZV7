package com.lsjr.zizisteward.activity.home.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.HomeBean;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.base.RVBaseCell;
import com.ys.base.RVBaseViewHolder;

/**
 * Created by admin on 2017/6/2.
 */

public class HomeTextMoreCell5 extends RVBaseCell<String> {


    public static final int TYPE = 5;
    public HomeTextMoreCell5(String str) {
        super(str);
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inculed_home_head_more,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder viewHolder, int position) {

    }
}
