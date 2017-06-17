package com.lsjr.zizisteward.activity.home.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class HomeImageViewCell2 extends RVBaseCell<HomeBean.ShouImgBean> {


    public static final int TYPE = 2;
    public HomeImageViewCell2(HomeBean.ShouImgBean shouImgBean) {
        super(shouImgBean);
        L_.e("HomeImageViewCell2  HomeImageViewCell2");
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.include_home_showimg,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder viewHolder, int position) {
        if (mData==null||mData.getDiligentFood()==null)return;
        ImageView imageView=viewHolder.getImageView(R.id.iv_ziwei);
        TextView textView=viewHolder.getTextView(R.id.tv_zizi_recommend);
        RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        linearParams2.width = UIUtils.WHD()[0];
        linearParams2.height = UIUtils.WHD()[0] / 2;
        imageView.setLayoutParams(linearParams2);
        if (getItemType()==2){
            // 孜味天下图片
            ImageLoader.with().url(AppUrl.Http + mData.getDiligentFood()).into(imageView);
            textView.setText("孜孜天下");
        }if (getItemType()==6){
            // 孜味天下图片
            ImageLoader.with().url(AppUrl.Http + mData.getDiligentActivity()).into(imageView);
            textView.setText("精选活动");
        }

    }
}
