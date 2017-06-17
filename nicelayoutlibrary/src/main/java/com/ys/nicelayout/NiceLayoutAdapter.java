package com.ys.nicelayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/17 19:58
 */

public class NiceLayoutAdapter extends VirtualLayoutAdapter<NiceLayoutAdapter.ViewHolder>{

    private int itemMargin;
    private List<String> pictures=new ArrayList<>();
    public NiceLayoutAdapter(@NonNull VirtualLayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return pictures==null?0:pictures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
