package com.lsjr.zizisteward.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.HomeBean;
import com.lsjr.zizisteward.coustom.RoundImageView;
import com.lsjr.zizisteward.coustom.myrecycleview.WrapRecyclerView;
import com.lsjr.zizisteward.http.AppUrl;
import com.lsjr.zizisteward.mvp.recycle.RecycleViewTypeAdapter;
import com.yangshao.utils.L_;

import java.util.List;

/**
 * Created by admin on 2017/5/17.
 */

public class FHomeAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {


    private List<HomeBean.DiligentRecommendBean> recommends;
    private Context context;
    private LayoutInflater inflater;
    private static final int TYPE_1 = 0X11;
    private static final int TYPE_2 = 0X22;
    private static final int TYPE_3 = 0X33;
    private static final int TYPE_4 = 0X44;

    public FHomeAdapter(Context context, List<HomeBean.DiligentRecommendBean> recommends) {
        this.recommends = recommends;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void notifyDataChange(List<HomeBean.DiligentRecommendBean> commends){
        this.recommends.clear();
        this.recommends.addAll(commends);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View rootView=null;
        switch (viewType){
            case TYPE_1:
                rootView = inflater.inflate(R.layout.test_vh_one, parent, false);
                return new OnCreateViewHolder1(rootView);
            case TYPE_2:
                break;
            case TYPE_3:
                break;
        }
        return new ViewHolder(rootView);
    }


    static class OnCreateViewHolder1 extends RecyclerView.ViewHolder {
        WrapRecyclerView recyclerView1;
        public OnCreateViewHolder1(View itemView) {
            super(itemView);
            recyclerView1 = (WrapRecyclerView) itemView.findViewById(R.id.id_recyview1);
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {
        HomeBean.DiligentRecommendBean diligentRecommendBean = recommends.get(position);
       // Glide.with(context).load(AppUrl.Http + diligentRecommendBean.getSpicfirst())
         //       .into(holder.ivPhoto);
       // L_.e(recommends.get(position).getSpicfirst());
       // holder.tvName.setText(diligentRecommendBean.getSkeyword()==null?"":diligentRecommendBean.getSkeyword());
      //  holder.tvContent.setText(diligentRecommendBean.getSname()==null?"":diligentRecommendBean.getSname());
    }


    @Override
    public int getAdapterItemCount() {
        return recommends.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        RoundImageView ivPhoto;
        TextView tvName;
        TextView tvContent;
         ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (RoundImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
