package com.yangshao.page;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yangsho.baselib.R;

import java.util.Map;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/8 9:06
 */

public class ErrorPageView implements View.OnClickListener {

    public final static int Success = 0;
    public final static int NetError = 1;
    public final static int NoContext = 2;

    @IntDef({Success, NetError, NoContext})
    public @interface Flavour {

    }

    private Context context;

    private FrameLayout contentLayout;
    private FrameLayout mTargetLayout;
    private View mTagetView;


    private LayoutInflater inflate;
    private Activity parentActivity;


    private View loadingView;
    private View tipsView;
    private View targetView;

    private ImageView mIcon;
    private TextView mTips;

    public ErrorPageView(Context context, int parentLayoutId) {
        initErroPage(context, parentLayoutId, null);
    }

    public ErrorPageView(Context context, View view) {
        initErroPage(context, 0, view);
    }

    private void initErroPage(Context context, int parentLayoutId, View view) {
        parentActivity = (Activity) context;
        inflate = LayoutInflater.from(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTargetLayout = new FrameLayout(context);
        if (parentLayoutId != 0) {
            mTagetView = inflate.inflate(parentLayoutId, null);
        }
        if (view != null) {
            mTagetView = view;
        }
        if (mTagetView == null) {
            return;
        }
        /*包含  target的layout*/
        mTargetLayout.addView(mTagetView);

        /*包含 正文 的layout*/
        contentLayout = new FrameLayout(context);
        contentLayout.addView(mTargetLayout);

        /*设置一个异常页面  view,rootViewId=R.id.view_tips*/
        tipsView = inflate.inflate(R.layout.error_tip_layout, null);
        tipsView.setLayoutParams(params);
        tipsView.setId(R.id.view_tips);
        mIcon = (ImageView) tipsView.findViewById(R.id.id_base_icon);
        mTips = (TextView) tipsView.findViewById(R.id.id_base_tips);
        TextView reset = (TextView) tipsView.findViewById(R.id.id_base_reset);
        reset.setOnClickListener(this);

        /*设置一个Loading页面  view,rootViewId=R.id.loading_view*/
        loadingView = inflate.inflate(R.layout.error_loading_view, null);
        loadingView.setId(R.id.loading_view);

    }

    /**
     * 显示一个进度条，此进度条会屏蔽当前界面，此时用户无法操作界面
     */
    private void showLoadingView() {
        hideErrorView();
        if (parentActivity.findViewById(R.id.loading_view) == null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            parentActivity.addContentView(loadingView, params);
        }
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示异常视图（tipsView）
     *
     * @param target 想替换掉的部分，想在RecyclerView的部分显示异常，就传RecyclerView
     * @param status 服务器返回的状态码，根据不同状态码显示的错误信息不同
     */
    public void showResultView(View target, @Flavour int status) {
        if (status == Success) {
            target.setVisibility(View.VISIBLE);
        } else {
            showResultView(target, status, null);
        }

    }

    /**
     * 显示异常视图（tipsView）
     *
     * @param target 想替换掉的部分，想在RecyclerView的部分显示异常，就传RecyclerView
     * @param status 服务器返回的状态码，根据不同状态码显示的错误信息不同
     * @param msgs   自定义的错误信息，比如有的信息为空异常要显示成“该XX下没有XX”，有的要显示“您还没有XX”，自定义更方便
     */
    public void showResultView(View target, @Flavour int status, String msgs) {
        hideLoadingView();
        if (status == Success) {
            showResultView(target, status);
        } else {
            if (targetView == null) {
                targetView = target;
                targetView.setId(R.id.target_view);
            }
           /*找到targetView的父布局*/
            ViewGroup parent = (ViewGroup) targetView.getParent();
            View tempTips = parent.findViewById(R.id.view_tips);
            if (tempTips == null) {
            /*给父布局上加一个tipsView，即异常显示视图*/
                parent.addView(tipsView);
            } else {
                tipsView.setVisibility(View.VISIBLE);
            }
            //mIcon.setBackgroundResource(status == TErrorCode.NULL_RESULTS ?R.mipmap.ico_no_content:R.mipmap.ico_no_network);
            //getErrorMsg(status,errorCode)根据状态码status和自定义的错误集，来得到错误信息
            //mTips.setText(msgs);
            //隐藏原布局，如果传进来的是RecyclerView，那么RecyclerView就会被隐藏，tipsView会被显示出来
            target.setVisibility(View.GONE);
        }


    }

    private void hideLoadingView() {
        if (parentActivity.findViewById(R.id.loading_view) != null) {
            parentActivity.findViewById(R.id.loading_view).setVisibility(View.GONE);
        }
        hideErrorView();
    }

    /*隐藏错误页面显示正常页面*/
    private void hideErrorView() {
        if (parentActivity.findViewById(R.id.view_tips) != null) {
            parentActivity.findViewById(R.id.view_tips).setVisibility(View.GONE);
        }

        if (parentActivity.findViewById(R.id.target_view) != null) {
            parentActivity.findViewById(R.id.target_view).setVisibility(View.VISIBLE);
        }
    }

    public FrameLayout getContentLayout() {
        return contentLayout;
    }

    private View getTargetView() {
        return mTagetView;
    }


    public interface OnReloadingListener {
        void onReloading();
    }

    private OnReloadingListener mOnReloadingListener;

    public void setOnReloadingListener(OnReloadingListener l) {
        this.mOnReloadingListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnReloadingListener != null) {
            mOnReloadingListener.onReloading();
        }
    }
}
