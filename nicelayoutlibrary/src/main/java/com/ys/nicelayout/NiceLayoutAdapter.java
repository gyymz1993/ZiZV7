package com.ys.nicelayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/17 19:58
 */

public class NiceLayoutAdapter extends VirtualLayoutAdapter<NiceLayoutAdapter.ViewHolder> {

    private Context context;
    private int itemMargin;
    private List<String> pictures = new ArrayList<>();

    public NiceLayoutAdapter(@NonNull VirtualLayoutManager layoutManager,Context context,int itemMargin) {
        super(layoutManager);
        this.context=context;
        this.itemMargin=itemMargin;
        Log.e("NiceLayoutAdapter","NiceLayoutAdapter");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_mulit_image,null);
        return new ViewHolder(view);
    }

    public void bindData(List<String> pictures){
        this.pictures=pictures;
        notifyDataSetChanged();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.e("NiceLayoutAdapter ","onBindViewHolder");

        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int width = 0;
        int height = 0;
        int imageCount = pictures.size();
        Log.e("NiceLayoutAdapter "," pictures.size()"+pictures.size());
        Log.e("NiceLayoutAdapter ","pictures"+pictures.get(position));
        int displayW = DisplayUtils.getDisplayWidth(context);
        switch (imageCount) {
            case 1:
                width = displayW;
                height = width;
                break;
            case 2:
                width = displayW / 2;
                height = width;
                break;
            case 3:
                if (position == 0) {
                    width = (int) (displayW * 0.66);
                    height = width;
                    layoutParams.rightMargin = itemMargin;
                    layoutParams.bottomMargin = itemMargin;
                } else {
                    if (position == 1 || position == 2) {
                        if (position == 1) {
                            layoutParams.bottomMargin = itemMargin / 2;
                        } else {
                            layoutParams.bottomMargin = itemMargin;
                        }
                        width = (int) (displayW * 0.33);
                        height = width;
                    }
                }
                break;
            case 4:
                if (position == 0) {
                    width = displayW;
                    height = (int) (displayW * 0.5);
                } else {
                    width = (int) (displayW * 0.33);
                    height = width;
                }
                break;
            case 5:
                if (position == 0 || position == 1) {
                    width = (int) (displayW * 0.5);
                    height = width;
                } else {
                    width = (int) (displayW * 0.33);
                    height = width;
                }
                break;
            case 6:
                if (position == 0) {
                    width = displayW / 2;
                    height = (int) (displayW / 3.0f * 2);
                } else if (position == 1 || position == 2) {
                    width = displayW / 2;
                    height = (int) (displayW / 3.0f);
                } else {
                    width = (int) (displayW / 3.0f);
                    height = width;
                }
                break;
            case 7:
                if (position == 0) {
                    width = displayW;
                    height = (int) (displayW / 2.0f);
                } else {
                    width = (int) (displayW / 3.0f);
                    height = width;
                }
                break;
            case 8:
                if (position==0||position==1){
                    width= (int) (displayW/2.0f);
                    height=width;
                }else {
                    width = (int) (displayW / 3.0f);
                    height = width;
                }
                break;
            case 9:
                width = (int) (displayW / 3.0f);
                height = width;
                break;

        }
        Log.e("NiceLayoutAdapter onBindViewHolder",pictures.get(position)+"");
        layoutParams.width=width;
        layoutParams.height=height;
        viewHolder.imageView.setLayoutParams(layoutParams);

        Glide.with(context).load(pictures.get(position)).centerCrop().into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return pictures == null ? 0 : pictures.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.item_mulit_image);
        }
    }
}
