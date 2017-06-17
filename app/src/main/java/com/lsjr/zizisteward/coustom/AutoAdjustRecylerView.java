package com.lsjr.zizisteward.coustom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.yangshao.utils.L_;

public class AutoAdjustRecylerView extends RecyclerView {
	private Scroller mScroller = null;
	private int mLastx = 0;
	//用于设置自动平移时候的速度
	private float mPxPerMillsec = 0;

	public AutoAdjustRecylerView(Context context) {
		this(context,null);
	}

	public AutoAdjustRecylerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public AutoAdjustRecylerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData(context);
	}

	private void initData(Context context){
		mScroller = new Scroller(context, new Interpolator() {
			public float getInterpolation(float t) {
			      return t;
			  }
		});
	}

	public void setScroller(Scroller scroller){
		if(mScroller != scroller){
			mScroller = scroller;
		}
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if(mScroller != null){
			if (mScroller.computeScrollOffset())//如果mScroller没有调用startScroll，这里将会返回false。
		    {
		        scrollBy(mLastx - mScroller.getCurrX(), 0);
		        mLastx = mScroller.getCurrX();
		        //继续让系统重绘
		        postInvalidate();
		     }
		}
	}

	public float getPxPerMillsec() {
		return mPxPerMillsec;
	}

	public void setPxPerMillsec(float pxPerMillsec) {
		this.mPxPerMillsec = pxPerMillsec;
	}


	/**/
	public void checkScrollTo(int position){
		int childcount = getChildCount();
		//获取可视范围内的选项的头尾位置
		int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
		int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

		//取整:Math.floor(2.9)=2   对一个数进行下取整
		//取整:Math.ceil(2.1)=3    对一个数进行上取整。
		if ((firstvisiableposition+lastvisiableposition)%2==0){
			int center= (firstvisiableposition+lastvisiableposition)/2;
			if(position==firstvisiableposition&&position>0||position<center&&position>0){
				//当前位置需要向左平移
				scrollToPosition(firstvisiableposition-1);
			}
			else if(position == lastvisiableposition&&position!=childcount||position > center&&position!=childcount){
				//当前位置需要向做平移
				scrollToPosition(lastvisiableposition+1);
			}else if (position== center){
				scrollToPosition(center);
			}
		}else {
			double center=(firstvisiableposition*1.0f+lastvisiableposition)/2;
			int centerleft= (int) Math.floor(center);
			int centerRight= (int) Math.ceil(center);
			if(position==firstvisiableposition&&position>0||position<centerleft&&position>0){
				//当前位置需要向左平移
				scrollToPosition(firstvisiableposition-1);
			}
			else if(position == lastvisiableposition&&position!=childcount||position > centerRight&&position!=childcount){
				//当前位置需要向做平移
				scrollToPosition(lastvisiableposition+1);
			}else if (position== centerleft){
				scrollToPosition(centerleft);
			}else if(position== centerRight){
				scrollToPosition(centerRight);
			}
		}
	}


	/**/
	public void checkAutoAdjust(int position){
		int childcount = getChildCount();
		//获取可视范围内的选项的头尾位置
		int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
		int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
		if(position == (firstvisiableposition + 1) || position == firstvisiableposition){
			//当前位置需要向右平移
			leftScrollBy(position, firstvisiableposition);
		}
		else if(position == (lastvisiableposition - 1) || position == lastvisiableposition){
			//当前位置需要向做平移
			rightScrollBy(position, lastvisiableposition);
		}
	}

	private void leftScrollBy(int position, int firstvisiableposition){
		View leftChild = getChildAt(0);
		if(leftChild != null){
			int leftx = leftChild.getLeft();
			int startleft = leftx;
			int endleft = position == firstvisiableposition?leftChild.getWidth():0;
			autoAdjustScroll(startleft, endleft);
		}
	}


	private void rightScrollBy(int position, int lastvisiableposition){
		int childcount = getChildCount();
		View rightChild = getChildAt(childcount - 1);
		if(rightChild != null){
			int rightx = rightChild.getRight();
			int dx = rightx - getWidth();
			int startright = dx;
			int endright = position == lastvisiableposition?-1 * rightChild.getWidth():0;
			autoAdjustScroll(startright, endright);
		}
	}

	/**
	 *
	 * @param start 滑动起始位置
	 * @param end 滑动结束位置
	 */
	private void autoAdjustScroll(int start, int end){
		int duration = 0;
        if(mPxPerMillsec != 0){
        	duration = (int)((Math.abs(end - start)/mPxPerMillsec));
        }
		mLastx = start;
		mScroller.startScroll(start, 0, end - start, 0, duration);
		postInvalidate();
	}

}
