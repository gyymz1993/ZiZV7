package com.lsjr.zizisteward.activity.home.cell;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.classly.adapter.SpacesItemDecoration;
import com.lsjr.zizisteward.activity.home.adapter.RecycleViewTypeAdapter;
import com.lsjr.zizisteward.bean.HomeBean;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.base.RVBaseCell;
import com.ys.base.RVBaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/6/2.O
 */

public abstract class HomeGridViewCell1 extends RVBaseCell<List<HomeBean.DiligentRecommendBean>> {


    protected abstract RecycleViewTypeAdapter getViewTypeAdapter();
    /*可以自己是设置多个类型*/
    public HomeGridViewCell1(List<HomeBean.DiligentRecommendBean> diligentRecommendBeans) {
        super(diligentRecommendBeans);
        L_.e("init  HomeGridViewCell1  HomeGridViewCell1 init");
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_girdviewcell, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_recyview);
        LinearLayoutManager llyanager = new LinearLayoutManager(UIUtils.getContext());
        llyanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llyanager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        return new RVBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder viewHolder, int position) {
        if ( mData == null||mData.size() == 0)  return;
        RecyclerView recyclerView = (RecyclerView) viewHolder.getView(R.id.id_recyview);
        recyclerView.setAdapter(getViewTypeAdapter());

    }

    OnItmeClickListener  onItmeClickListener;
    public interface OnItmeClickListener{
        void onItemClick(int position);
    }

}
