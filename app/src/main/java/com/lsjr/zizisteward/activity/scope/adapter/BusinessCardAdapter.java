package com.lsjr.zizisteward.activity.scope.adapter;

import android.view.ViewGroup;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.BusinessCardBean;
import com.lsjr.zizisteward.coustom.myrecycleview.BaseRecyclerAdapter;
import com.lsjr.zizisteward.coustom.myrecycleview.BaseRecyclerHolder;
import com.yangshao.indicator.UIUtil;
import com.yangshao.utils.UIUtils;

import java.util.List;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/7 16:20
 */

public class BusinessCardAdapter extends BaseRecyclerAdapter<BusinessCardBean.SharesList> {

    public BusinessCardAdapter(List<BusinessCardBean.SharesList> datas, int itemLayoutId) {
        super(datas, itemLayoutId);
    }


    @Override
    protected void convert(BaseRecyclerHolder holder, BusinessCardBean.SharesList item, int position) {
        ViewGroup.LayoutParams layoutParams = holder.getView(R.id.iv_photo).getLayoutParams();
        layoutParams.width= UIUtils.WHD()[0]/3- UIUtils.dip2px(10);
        holder.setImageByUrl(R.id.iv_photo,item.getPhoto());
        holder.setText(R.id.tv_state,item.getCustom_tag());
    }

}
