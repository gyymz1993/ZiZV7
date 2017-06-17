package com.lsjr.zizisteward.activity.scope.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.activity.utils.LevelUtils;
import com.lsjr.zizisteward.bean.ShiJieBean;
import com.lsjr.zizisteward.coustom.CheckableImageView;
import com.lsjr.zizisteward.coustom.nine.NineGridLayout;
import com.lsjr.zizisteward.http.AppUrl;
import com.yangshao.image.ImageLoader;
import com.yangshao.utils.L_;

import org.xutils.image.AsyncDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */

public class ShiJieAdapter extends BaseRecyclerAdapter<ShiJieAdapter.ViewHolder> {

    private Context mContext;
    private List<ShiJieBean.ShiJieListDetail> mData;
    private Bitmap mBitmap;
    private LruCache<String, BitmapDrawable> mMemoryCache;

    public ShiJieAdapter(Context mContext, List<ShiJieBean.ShiJieListDetail> data) {
        this.mContext = mContext;
        this.mData = data;

        //默认显示的图片
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher);
        //计算内存，并且给Lrucache 设置缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/6;
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }


    public void notifyDataSetChanged(List<ShiJieBean.ShiJieListDetail> data) {
        this.mData=data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i, boolean isItem) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_shijie, null);
        return new ViewHolder(view,true);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view,false);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position, boolean isItem) {
        String[] mSize_imgs = mData.get(position).getShareImg().split(",");
        List<String> listImages = new ArrayList();
        List<Bitmap> bitmapList=new ArrayList<>();
        for (int i = 0; i < mSize_imgs.length; i++) {
            BitmapDrawable drawable = getBitmapDrawableFromMemoryCache(AppUrl.Http + mSize_imgs[i]);
            L_.e("缓存数据 BitmapDrawable drawable  "+drawable);
            if ( drawable!=null){
                bitmapList.add(drawable.getBitmap());
            }else {
                listImages.add(AppUrl.Http + mSize_imgs[i]);
            }
        }
        /*测速每次显示一张*/
        ImageLoader.with().url(AppUrl.Http + mData.get(position).getSpicfirst()).asCircle().into(viewHolder.mYouliao_yuantu);
        viewHolder.nineGrid.setIsShowAll(true);
        if (bitmapList.size()!=0){
            L_.e("直接从缓存设置图片");
            viewHolder.nineGrid.setBitmaps(bitmapList);
        }else {
            viewHolder.nineGrid.setUrlList(listImages);
        }
        viewHolder.nineGrid.setDisplayImageSucceedListener(new NineGridLayout.DisplayImageSucceedListener() {
            @Override
            public void onDisplaysucceed(String url,BitmapDrawable drawable) {
                L_.e("添加到缓存文件"+url+drawable);
               // addBitmapDrawableToMemoryCache(url,drawable);
            }
        });
        viewHolder.mTv_name.setText(mData.get(position).getUser_name());
        viewHolder.mTv_content.setText(mData.get(position).getContent());
        LevelUtils.setLevelIcon(mData.get(position).getCredit_level_id(), viewHolder.mIv_level);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String share_time = formatter.format(Long.valueOf(mData.get(position).getShare_time().getTime()));
        viewHolder.mTv_time.setText(share_time);
        // viewHolder.imageCollect.setEnabled(shiJieListDetail.isCollect());
        viewHolder.imageCollect.setChecked(mData.get(position).isIscollect());
        /*点赞接口*/
        viewHolder.imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserOperationListener != null) {
                    onUserOperationListener.onLike(v, position);
                }
            }
        });

        /*收藏接口*/
        viewHolder.imageCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserOperationListener != null) {
                    onUserOperationListener.onCollect(v, position);
                }
            }
        });
    }



    /**
     * 添加图片到缓存中
     * @param imageUrl
     * @param drawable
     */
    private void addBitmapDrawableToMemoryCache(String imageUrl,BitmapDrawable drawable){
        if (getBitmapDrawableFromMemoryCache(imageUrl) == null ){
            mMemoryCache.put(imageUrl, drawable);
        }
    }


    /**
     * 從缓存中获取已存在的图片
     * @param imageUrl
     * @return
     */
    private BitmapDrawable getBitmapDrawableFromMemoryCache(String imageUrl) {
        return mMemoryCache.get(imageUrl);
    }


    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mYouliao_yuantu;// 用户头像
        private TextView mTv_key;// 身份类型
        private TextView mTv_name;// 用户名
        private TextView mTv_time;// 时间
        private ImageView mIv_level;// 会员等级图标
        private TextView mTv_content;// 内容
        private CheckableImageView imageLike;
        private CheckBox imageCollect;

        private NineGridLayout nineGrid;
        public ViewHolder(View view,boolean isItem) {
            super(view);
            if (isItem){
                mYouliao_yuantu = (ImageView) view.findViewById(R.id.youliao_yuantu);
                mTv_name = (TextView) view.findViewById(R.id.tv_name);
                mIv_level = (ImageView) view.findViewById(R.id.iv_level);
                mTv_time = (TextView) view.findViewById(R.id.tv_time);
                mTv_content = (TextView) view.findViewById(R.id.tv_content);
                nineGrid = (NineGridLayout) view.findViewById(R.id.layout_nine_grid);
                imageLike = (CheckableImageView) view.findViewById(R.id.image_shijie_zan);
                imageCollect = (CheckBox) view.findViewById(R.id.image_shijie_collect);
            }
            //mTv_count = (TextView) view.findViewById(R.id.tv_count);
        }
    }

    public void setOnUserOperationListener(ShiJieAdapter.onUserOperationListener onUserOperationListener) {
        this.onUserOperationListener = onUserOperationListener;
    }

    private onUserOperationListener onUserOperationListener;

    public interface onUserOperationListener {
        void onLike(View view, int position);

        void onCollect(View view, int position);
    }
}
