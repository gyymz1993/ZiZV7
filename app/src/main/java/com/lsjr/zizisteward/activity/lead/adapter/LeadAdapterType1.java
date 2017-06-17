package com.lsjr.zizisteward.activity.lead.adapter;

import android.content.Context;
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

public class LeadAdapterType1 extends RVBaseCell<String>{
    public LeadAdapterType1(String o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return 1;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lead_one_new,parent,false);
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
