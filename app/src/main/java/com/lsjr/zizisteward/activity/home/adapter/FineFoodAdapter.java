package com.lsjr.zizisteward.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.FineFoodBean;
import com.lsjr.zizisteward.coustom.RoundImageView;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;

import java.util.List;

/**
 * Created by admin on 2017/6/3.
 */

public class FineFoodAdapter extends RecyclerView.Adapter<FineFoodAdapter.ViewHolder> {

    private List<FineFoodBean.Cate> fineFoodBeanList;
    private Context mContext;

    public FineFoodAdapter(Context context, List<FineFoodBean.Cate> fineFoodBeanList) {
        this.fineFoodBeanList = fineFoodBeanList;
        this.mContext = context;
    }

    @Override
    public FineFoodAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_finefoot, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FineFoodAdapter.ViewHolder view, int position) {
        String[] pic = fineFoodBeanList.get(position).getSpic().split(",");
        if (null != pic && pic.length > 0) {
            ImageLoader.with().url(AppUrl.Http + pic[0]).into(view.iv);
        }
        view.tv_title.setText(fineFoodBeanList.get(position).getSname());
        view.tv_positioning.setText(fineFoodBeanList.get(position).getAreas_name());
        view.tv_recommended.setText(
                fineFoodBeanList.get(position).getExpert().equals("0") ? "" : fineFoodBeanList.get(position).getExpert() + "名美食达人推荐  >");
        view.tv_sentiment.setText(fineFoodBeanList.get(position).getPopularity() + "人气");
        view.tv_content.setText(fineFoodBeanList.get(position).getSinfo());
        List<FineFoodBean.Cate.Comment> comments = fineFoodBeanList.get(position).getComment();
        if (null != comments) {
            for (int i = 0; i < comments.size(); i++) {
                if (i < 3) {
                    if (i == 0) {
                        view.riv_head.setVisibility(View.VISIBLE);
                        view.riv_head_one.setVisibility(View.GONE);
                        view.riv_head_two.setVisibility(View.GONE);
                        ImageLoader.with().url(AppUrl.Http +comments.get(0).getPhoto()).into(view.riv_head);

                    } else if (i == 1) {
                        view.riv_head.setVisibility(View.VISIBLE);
                        view.riv_head_one.setVisibility(View.VISIBLE);
                        view.riv_head_two.setVisibility(View.GONE);
                        ImageLoader.with().url(AppUrl.Http +comments.get(0).getPhoto()).into(view.riv_head);
                        ImageLoader.with().url(AppUrl.Http +comments.get(1).getPhoto()).into(view.riv_head_one);
                    } else if (i == 2) {
                        view.riv_head.setVisibility(View.VISIBLE);
                        view.riv_head_one.setVisibility(View.VISIBLE);
                        view.riv_head_two.setVisibility(View.VISIBLE);
                        ImageLoader.with().url(AppUrl.Http +comments.get(0).getPhoto()).into(view.riv_head);
                        ImageLoader.with().url(AppUrl.Http +comments.get(1).getPhoto()).into(view.riv_head_one);
                        ImageLoader.with().url(AppUrl.Http +comments.get(2).getPhoto()).into(view.riv_head_two);
                    }
                } else {
                    continue;
                }
            }

        } else {
            view.riv_head.setVisibility(View.INVISIBLE);
            view.riv_head_one.setVisibility(View.INVISIBLE);
            view.riv_head_two.setVisibility(View.INVISIBLE);
        }

        view.tv_recommended.setTag(position);
        view.tv_recommended.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
            }
        });

    }

    @Override
    public int getItemCount() {
        return fineFoodBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //图片
        ImageView iv;
        //标题
        TextView tv_title;
        //定位
        TextView tv_positioning;
        //达人头像
        RoundImageView riv_head;
        RoundImageView riv_head_one;
        RoundImageView riv_head_two;
        //达人推荐
        TextView tv_recommended;
        //人气
        TextView tv_sentiment;
        //内容
        TextView tv_content;

        public ViewHolder(View v) {
            super(v);
            this.iv = (ImageView) v.findViewById(R.id.iv);
            this.tv_title = (TextView) v.findViewById(R.id.tv_title);
            this.tv_content = (TextView) v.findViewById(R.id.tv_content);
            this.riv_head = (RoundImageView) v.findViewById(R.id.riv_head);
            this.tv_sentiment = (TextView) v.findViewById(R.id.tv_sentiment);
            this.tv_recommended = (TextView) v.findViewById(R.id.tv_recommended);
            this.tv_positioning = (TextView) v.findViewById(R.id.tv_positioning);
            this.riv_head_one = (RoundImageView) v.findViewById(R.id.riv_head_one);
            this.riv_head_two = (RoundImageView) v.findViewById(R.id.riv_head_two);
        }
    }
}
