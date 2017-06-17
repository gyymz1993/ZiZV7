package com.ys.nicelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/17 19:07
 * <p>
 * /**
 * Created by wxy on 2017/5/25.
 * 模仿nice首页列表 9种样式图片
 * 依赖淘宝vLayout开源控件 实现
 * 1
 * -------------------------
 * |                       |
 * |                       |
 * |           1           |
 * |                       |
 * |                       |
 * |                       |
 * -------------------------
 * <p>
 * 2
 * * -------------------------
 * |           |           |
 * |           |           |
 * |           |           |
 * |     1     |     2     |
 * |           |           |
 * |           |           |
 * |           |           |
 * -------------------------
 * 3
 * -------------------------
 * |           |           |
 * |           |     2     |
 * |           |           |
 * |     1     |-----------|
 * |           |           |
 * |           |     3     |
 * |           |           |
 * -------------------------
 * 4
 * -------------------------
 * |                       |
 * |           1           |
 * |                       |
 * |-----------------------|
 * |      |        |       |
 * |   2  |     3  |    4  |
 * |      |        |       |
 * -------------------------
 * 5
 * -------------------------
 * |          |            |
 * |    1     |   2        |
 * |          |            |
 * |-----------------------|
 * |      |        |       |
 * |   3  |    4   |    5  |
 * |      |        |       |
 * -------------------------
 * 6
 * -------------------------
 * |           |           |
 * |           |     2     |
 * |           |           |
 * |     1     |-----------|
 * |           |           |
 * |           |     3     |
 * |           |           |
 * -------------------------
 * |      |        |       |
 * |   4  |   5    |    6  |
 * |      |        |       |
 * -------------------------
 * <p>
 * 7
 * -------------------------
 * |                       |
 * |           1           |
 * |                       |
 * |-----------------------|
 * |      |        |       |
 * |   2  |     3  |    4  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   5  |     6  |    7  |
 * |      |        |       |
 * -------------------------
 * 8
 * -------------------------
 * |          |            |
 * |    1     |   2        |
 * |          |            |
 * |-----------------------|
 * |      |        |       |
 * |   3  |    4   |    5  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   6  |     7  |    8  |
 * |      |        |       |
 * -------------------------
 * 9
 * |-----------------------|
 * |      |        |       |
 * |   1  |     2  |    3  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   4  |     5  |    6  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   7  |     8  |    9  |
 * |      |        |       |
 * -------------------------
 */

public class NiceLayout extends LinearLayout {


    public NiceLayout(Context context) {
        this(context, null);
    }

    public NiceLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceLayout);

        final int COUNT = typedArray.getIndexCount();
        for (int i = 0; i < COUNT; i++) {
            initArrt(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();

    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_imagemulit_layout, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_rv_nece);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(virtualLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initArrt(int attr, TypedArray typedArray) {
        if (attr == R.styleable.NiceLayout_nice9_candrag) {
            typedArray.getBoolean(R.styleable.NiceLayout_nice9_candrag, false);
        }
        if (attr == R.styleable.NiceLayout_nice9_itemMargin) {
            itemMargin = (int) typedArray.getDimension(R.styleable.NiceLayout_nice9_itemMargin, 10);
        }
    }

    Context context;
    int itemMargin;
    LinkedList helpers;
    RecyclerView recyclerView;
    VirtualLayoutManager virtualLayoutManager;

    public void bindData(final List<String> pictures) {
        if (pictures != null) {
            helpers = new LinkedList();
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(6);
            gridLayoutHelper.setGap(itemMargin);
            gridLayoutHelper.setVGap(itemMargin);
            gridLayoutHelper.setHGap(itemMargin);

            OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(3);

            final int num = pictures.size();
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            int displayW = DisplayUtils.getDisplayWidth(context);
            int height = 0, width;
            width = displayW;
            layoutParams.width = width;
            switch (num) {
                case 1:
                    height = width;
                    break;
                case 2:
                    height = (int) (displayW * 0.5);
                    break;
                case 3:
                    height = (int) (displayW * 0.66 - itemMargin - itemMargin / 2);
                    break;
                case 4:
                    height = (int) (displayW * 0.5 + itemMargin + displayW * 0.33);
                    break;
                case 5:
                    height = (int) (displayW * 0.5) + itemMargin + (int) (displayW * 0.33);
                    break;
                case 6:
                    height = (int) (displayW * 0.66) + (int) (displayW * 0.33) - itemMargin / 2;
                    break;
                case 7:
                case 8:
                    height = (int) (displayW * 0.5) + 2 * itemMargin + (int) (displayW * 0.33) * 2;
                    break;
                case 9:
                    height = (int) ((displayW * 0.33) * 3 + 3 * itemMargin - itemMargin / 2);
                    break;
            }
            layoutParams.height = height;
            recyclerView.setLayoutParams(layoutParams);
            /*根据 数量和位置设置Span比例*/
            gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (num == 1) {
                        return 6;
                    } else if (num == 2) {
                        return 3;
                    } else if (num == 4) {
                        if (position == 0) {
                            return 6;
                        } else {
                            return 2;
                        }
                    } else if (num == 5) {
                        if (position == 0 || position == 3) {
                            return 3;
                        } else {
                            return 2;
                        }
                    } else if (num == 6) {
                        return 2;
                    } else if (num == 7) {
                        if (position == 0) {
                            return 6;
                        } else {
                            return 2;
                        }
                    } else if (num == 8) {
                        if (position == 0 || position == 1) {
                            return 3;
                        } else {
                            return 2;
                        }
                    } else {
                        return 2;
                    }
                }
            });
            helpers.clear();
            if (num == 3) {
                helpers.add(onePlusNLayoutHelper);
                gridLayoutHelper.setItemCount(0);
            } else {
                if (num == 6) {
                    helpers.add(onePlusNLayoutHelper);
                    gridLayoutHelper.setItemCount(3);
                } else {
                    gridLayoutHelper.setItemCount(num);
                }
                helpers.add(gridLayoutHelper);
            }
            virtualLayoutManager.setLayoutHelpers(helpers);
            //recyclerView.setAdapter();
        }
    }


}
