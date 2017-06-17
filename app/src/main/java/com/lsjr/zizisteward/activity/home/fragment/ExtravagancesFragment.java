package com.lsjr.zizisteward.activity.home.fragment;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.activity.classly.ui.ClassifyActivity;
import com.yangshao.loading.LoadingController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/6/1.
 */

public class ExtravagancesFragment extends BaseProductFragment {
    @Override
    public View.OnClickListener setOnClasslyListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getContext(), FamousBrandClassicActivity.class).putExtra("type", "jiangpin"));
                Bundle bundle=new Bundle();
                bundle.putString(Config.CLASSIC_TYPE,"EX");
                openActivity(ClassifyActivity.class,bundle);
            }
        };
    }

    @Override
    public String getTextTitle() {
        return "精选奢品";
    }

    @Override
    public void loadNetData() {
        Map<String, String> map = new HashMap<>();
        map.put("OPT", "423");
        map.put("currPage", String.valueOf(1));
        createPresenter().loadAsPorductListForNet(map);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadNetData();
    }
}
