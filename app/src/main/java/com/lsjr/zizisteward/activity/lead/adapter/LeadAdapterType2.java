package com.lsjr.zizisteward.activity.lead.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.ys.base.RVBaseCell;
import com.ys.base.RVBaseViewHolder;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/10 16:30
 */

public class LeadAdapterType2 extends RVBaseCell<String>{
    public LeadAdapterType2(String o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return 2;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lead_two,null);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
