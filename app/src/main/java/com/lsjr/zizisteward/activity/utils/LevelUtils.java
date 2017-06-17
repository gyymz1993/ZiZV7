package com.lsjr.zizisteward.activity.utils;

import android.widget.ImageView;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.coustom.vip.IdentityImageView;

/**
 * Created by admin on 2017/6/5.
 */

public class LevelUtils {
    public static void  setLevelIcon(String levelId, IdentityImageView imageView){
        if ("0".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_zero);
        }
        if ("1".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_one);
        }
        if ("2".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_two);
        }
        if ("3".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_three);
        }
        if ("4".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_three);
        }
        if ("5".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_five);
        }
        if ("6".equals(levelId)) {
            imageView.getSmallCircleImageView().setImageResource(R.drawable.level_six);
        }
    }

    public static void  setLevelIcon(String levelId, ImageView imageView){
        if ("0".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_zero);
        }
        if ("1".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_one);
        }
        if ("2".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_two);
        }
        if ("3".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_three);
        }
        if ("4".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_three);
        }
        if ("5".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_five);
        }
        if ("6".equals(levelId)) {
            imageView.setImageResource(R.drawable.level_six);
        }
    }
}
