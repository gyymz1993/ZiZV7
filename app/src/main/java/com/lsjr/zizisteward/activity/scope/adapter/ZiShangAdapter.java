package com.lsjr.zizisteward.activity.scope.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.utils.LevelUtils;
import com.lsjr.zizisteward.bean.ShiJieBean;
import com.lsjr.zizisteward.coustom.CheckableImageView;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.ys.lib.ImageNice9Layout;
import com.ys.nicelayout.NiceLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */

public class ZiShangAdapter extends BaseRecyclerAdapter<ZiShangAdapter.ViewHolder> {

    private Context mContext;
    private List<ShiJieBean.ShiJieListDetail> mData;

    public ZiShangAdapter(Context mContext, List<ShiJieBean.ShiJieListDetail> data) {
        this.mContext = mContext;
        this.mData = data;
    }


    public void notifyDataSetChanged(List<ShiJieBean.ShiJieListDetail> data) {
        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i, boolean isItem) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_zishang, null);
        return new ViewHolder(view, true);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view, false);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position, boolean isItem) {
        String[] mSize_imgs = mData.get(position).getShareImg().split(",");
        List<String> listImages = new ArrayList();
        for (int i = 0; i < mSize_imgs.length; i++) {
            listImages.add(AppUrl.Http + mSize_imgs[i]);
        }
        /*测速每次显示一张*/
        ImageLoader.with().url(AppUrl.Http + mData.get(position).getSpicfirst()).asCircle().into(viewHolder.mYouliao_yuantu);
        if (listImages.size()!=0){
            viewHolder.nineGrid.bindData(listImages);
        }
        viewHolder.mTv_name.setText(mData.get(position).getUser_name());
        viewHolder.mTv_content.setText(mData.get(position).getContent());
        LevelUtils.setLevelIcon(mData.get(position).getCredit_level_id(), viewHolder.mIv_level);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String share_time = formatter.format(Long.valueOf(mData.get(position).getShare_time().getTime()));
        viewHolder.mTv_time.setText(share_time);
        // viewHolder.imageCollect.setEnabled(shiJieListDetail.isCollect());
        viewHolder.imageCollect.setChecked(mData.get(position).isIscollect());

    }


    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mYouliao_yuantu;// 用户头像
        private TextView mTv_key;// 身份类型
        private TextView mTv_name;// 用户名
        private TextView mTv_time;// 时间
        private ImageView mIv_level;// 会员等级图标
        private TextView mTv_content;// 内容
        private CheckableImageView imageLike;
        private CheckBox imageCollect;

        private ImageNice9Layout nineGrid;

        public ViewHolder(View view, boolean isItem) {
            super(view);
            if (isItem) {
                mYouliao_yuantu = (ImageView) view.findViewById(R.id.youliao_yuantu);
                mTv_name = (TextView) view.findViewById(R.id.tv_name);
                mIv_level = (ImageView) view.findViewById(R.id.iv_level);
                mTv_time = (TextView) view.findViewById(R.id.tv_time);
                mTv_content = (TextView) view.findViewById(R.id.tv_content);
                nineGrid = (ImageNice9Layout) itemView.findViewById(R.id.item_nice9_image);
                imageLike = (CheckableImageView) view.findViewById(R.id.image_shijie_zan);
                imageCollect = (CheckBox) view.findViewById(R.id.image_shijie_collect);
            }
        }
    }


}
