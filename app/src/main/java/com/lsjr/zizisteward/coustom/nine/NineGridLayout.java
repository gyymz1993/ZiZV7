package com.lsjr.zizisteward.coustom.nine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yangshao.image.ImageLoader;

import java.util.List;

public class NineGridLayout extends AbsNineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridLayout(Context context) {
        super(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
//
//        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                int w = bitmap.getWidth();
//                int h = bitmap.getHeight();
//                int newW;
//                int newH;
//                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
//                    newW = parentWidth / 2;
//                    newH = newW * 5 / 3;
//                } else if (h < w) {//h:w = 2:3
//                    newW = parentWidth * 2 / 3;
//                    newH = newW * 2 / 3;
//                } else {//newH:h = newW :w
//                    newW = parentWidth / 2;
//                    newH = h * newW / w;
//                }
//                setOneImageLayoutParams(imageView, newW, newH);
//                imageView.setImageBitmap(bitmap);
//            }
//        }); //方法中设置asBitmap可以设置回调类型
//
//        return false;
//    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, final String url, final int parentWidth) {
        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
                imageView.setImageBitmap(bitmap);
                if (displayImageSucceedListener!=null){
                    BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(),bitmap);
                    displayImageSucceedListener.onDisplaysucceed(url,drawable);
                }

            }
        }); //方法中设置asBitmap可以设置回调类型
        return false;
    }

    @Override
    protected void displayImage(final RatioImageView imageView, final String url) {
        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                imageView.setImageBitmap(bitmap);
                if (displayImageSucceedListener!=null){
                    BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(),bitmap);
                    displayImageSucceedListener.onDisplaysucceed(url,drawable);
                }

            }
        });
    }

//    @Override
//    protected void displayImage(RatioImageView imageView, String url) {
//       ImageLoader.with().url(url).into(imageView);
//    }

    public void setDisplayImageSucceedListener(DisplayImageSucceedListener displayImageSucceedListener) {
        this.displayImageSucceedListener = displayImageSucceedListener;
    }

    DisplayImageSucceedListener displayImageSucceedListener;

    public interface DisplayImageSucceedListener{
        void onDisplaysucceed(String url,BitmapDrawable drawable) ;
    }

    @Override
    protected void onClickImage(int i, String url, List<String> urlList) {
        Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
    }


}
