package com.lsjr.zizisteward.activity.lead;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.lead.adapter.LeadAdapterType1;
import com.lsjr.zizisteward.activity.lead.adapter.LeadAdapterType2;
import com.lsjr.zizisteward.activity.lead.adapter.LeadAdapterType3;
import com.lsjr.zizisteward.bean.FineFoodBean;
import com.ys.base.RVBaseAdapter;
import com.ys.base.RVBaseCell;
import com.ys.base.RVBaseViewHolder;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/10 16:02
 */

public class LeadActivity extends BaseMvpActivity {


    @BindView(R.id.id_rv_lead)
    RecyclerView gridCyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    RVBaseAdapter rvBaseAdapter = new RVBaseAdapter() {
        @Override
        protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

        }
    };

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager llyanager = new LinearLayoutManager(this);
        llyanager.setOrientation(LinearLayoutManager.VERTICAL);
        gridCyclerView.setLayoutManager(llyanager);
        gridCyclerView.setHasFixedSize(true);
        gridCyclerView.setAdapter(rvBaseAdapter);

        /*自定义RecycleView中多类型数据*/
        List<RVBaseCell> cells = new ArrayList<>();

//        int count = 5;
//        switch (count) {
//            case 1:
//                LeadAdapterType1 leadAdapterType1 = new LeadAdapterType1("1");
//                cells.add(leadAdapterType1);
//                break;
//            case 2:
//                LeadAdapterType2 leadAdapterType2 = new LeadAdapterType2("1");
//                cells.add(leadAdapterType2);
//                break;
//            case 3:
//                LeadAdapterType3 leadAdapterType3 = new LeadAdapterType3("1");
//                cells.add(leadAdapterType3);
//                break;
//            case 4:
//                LeadAdapterType1 leadAdapterType4_1 = new LeadAdapterType1("1");
//                cells.add(leadAdapterType4_1);
//                LeadAdapterType3 leadAdapterType4_3 = new LeadAdapterType3("1");
//                cells.add(leadAdapterType4_3);
//                break;
//            case 5:
//                LeadAdapterType2 leadAdapterType5_2 = new LeadAdapterType2("1");
//                cells.add(leadAdapterType5_2);
//                LeadAdapterType3 leadAdapterType5_3 = new LeadAdapterType3("1");
//                cells.add(leadAdapterType5_3);
//                break;
//            case 6:
//                LeadAdapterType1 leadAdapterType6_1 = new LeadAdapterType1("1");
//                cells.add(leadAdapterType6_1);
//                LeadAdapterType2 leadAdapterType6_2 = new LeadAdapterType2("1");
//                cells.add(leadAdapterType6_2);
//                LeadAdapterType3 leadAdapterType6_3 = new LeadAdapterType3("1");
//                cells.add(leadAdapterType6_3);
//                break;
//            case 7:
//                LeadAdapterType1 leadAdapterType7_1 = new LeadAdapterType1("1");
//                cells.add(leadAdapterType7_1);
//                LeadAdapterType1 leadAdapterType7_1_1 = new LeadAdapterType1("1");
//                cells.add(leadAdapterType7_1_1);
//                LeadAdapterType2 leadAdapterType7_2 = new LeadAdapterType2("1");
//                cells.add(leadAdapterType7_2);
//                LeadAdapterType3 leadAdapterType7_3 = new LeadAdapterType3("1");
//                cells.add(leadAdapterType7_3);
//                break;
//            case 8:
//                LeadAdapterType2 leadAdapterType8_2 = new LeadAdapterType2("1");
//                cells.add(leadAdapterType8_2);
//                LeadAdapterType1 leadAdapterType8_1 = new LeadAdapterType1("1");
//                cells.add(leadAdapterType8_1);
//                LeadAdapterType2 leadAdapterType8_2_2 = new LeadAdapterType2("1");
//                cells.add(leadAdapterType8_2_2);
//                LeadAdapterType3 leadAdapterType8_3 = new LeadAdapterType3("1");
//                cells.add(leadAdapterType8_3);
//                break;
//        }





        LeadAdapterType1 leadAdapterType1 = new LeadAdapterType1("1");
        cells.add(leadAdapterType1);


        LeadAdapterType3 leadAdapterType3 = new LeadAdapterType3("1");
        cells.add(leadAdapterType3);

        LeadAdapterType2 leadAdapterType2 = new LeadAdapterType2("1");
        cells.add(leadAdapterType2);


        LeadAdapterType1 leadAdapterType14 = new LeadAdapterType1("1");
        cells.add(leadAdapterType14);


        LeadAdapterType3 leadAdapterType34 = new LeadAdapterType3("1");
        cells.add(leadAdapterType34);


        LeadAdapterType1 leadAdapterType5 = new LeadAdapterType1("1");
        cells.add(leadAdapterType5);


        //cells.add(2,leadAdapterType1);
        rvBaseAdapter.setData(cells);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.activity_lead;
    }
}
