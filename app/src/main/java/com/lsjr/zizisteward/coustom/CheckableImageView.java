package com.lsjr.zizisteward.coustom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class CheckableImageView extends ImageView implements Checkable {

    public CheckableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }
      
    private boolean mChecked = false;  
    @Override  
    public void setChecked(boolean checked) {  
        if (mChecked != checked) {
            mChecked = checked;  
            refreshDrawableState();  
        }  
    }  
  
    @Override  
    public boolean isChecked() {  
        return mChecked;
    }  
  
    @Override  
    public void toggle() {  
        setChecked(!mChecked);
    }  
  
}  