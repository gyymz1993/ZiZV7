package com.lsjr.zizisteward.activity.home.fragment;
import android.os.Bundle;
import android.view.View;

import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.activity.classly.ui.ClassifyActivity;
import com.yangshao.loading.LoadingController;

import java.util.HashMap;
import java.util.Map;



/*
*  匠品
* */
public class AsProductFragment extends BaseProductFragment {


    /*进入分类*/
    @Override
    public View.OnClickListener setOnClasslyListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle  bundle=new Bundle();
                bundle.putString(Config.CLASSIC_TYPE,"AS");
                openActivity(ClassifyActivity.class,bundle);
            }
        };
    }

    @Override
    public String getTextTitle() {
        return "精选匠品";
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadNetData();
    }

    @Override
    public void loadNetData() {
        Map<String, String> map = new HashMap<>();
        map.put("OPT", "402");
        map.put("currPage", String.valueOf(1));
        createPresenter().loadAsPorductListForNet(map);
    }
}
