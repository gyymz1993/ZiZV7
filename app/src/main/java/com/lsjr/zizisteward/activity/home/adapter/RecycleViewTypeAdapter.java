package com.lsjr.zizisteward.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.HomeBean;
import com.lsjr.zizisteward.coustom.RoundImageView;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.yangshao.utils.L_;

import java.util.List;

public class RecycleViewTypeAdapter extends RecyclerView.Adapter<RecycleViewTypeAdapter.ItemViewHolder> {

    private int mItemType;
    private List<HomeBean.DiligentRecommendBean> mData;

    public RecycleViewTypeAdapter(int itemType, List<HomeBean.DiligentRecommendBean> diligentRecommendBeans) {
        this.mItemType = itemType;
        this.mData = diligentRecommendBeans;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        L_.e("选择类型选择布局:" + mItemType);
        if (mItemType == 1) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_girdviewcell_type1, parent, false));
        }
        if (mItemType == 3) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_girdviewcell_type2, parent, false));
        }
        if (mItemType == 4) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_girdviewcell_type3, parent, false));
        } else {
            return null;
        }
    }


    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        HomeBean.DiligentRecommendBean diligentRecommendBean = mData.get(position);
        if (mItemType == 4) {
            ImageLoader.with().url(AppUrl.Http + diligentRecommendBean.getSpicfirst()).into(viewHolder.ivPierre);
        } else {
            ImageLoader.with().url(AppUrl.Http + diligentRecommendBean.getSpicfirst()).into(viewHolder.ivPhoto);
            viewHolder.tvContent.setText(diligentRecommendBean.getSname() == null ? "" : diligentRecommendBean.getSname());
        }
        viewHolder.tvName.setText(diligentRecommendBean.getSkeyword() == null ? "" : diligentRecommendBean.getSkeyword());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /*第一条Itme里面的人子布局*/
    class ItemViewHolder extends RecyclerView.ViewHolder {
        RoundImageView ivPhoto;
        TextView tvName;
        TextView tvContent;

        ImageView ivPierre;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (RoundImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            ivPierre = (ImageView) itemView.findViewById(R.id.iv_pierre);

        }
    }

}


