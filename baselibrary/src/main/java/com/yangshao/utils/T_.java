package com.yangshao.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.yangshao.utils.UIUtils;
/**
  * @author: gyymz1993
  * 创建时间：2017/3/27 20:19
  * @version
  *
 **/
public class T_ {

		protected static final String TAG = "AppToast";
	/**
	 * 测试时使用
	 *
	 * @param obj
	 */
	@SuppressLint("ShowToast")
	public static void showToastWhendebug(final Object obj) {
		UIUtils.runInMainThread(new Runnable() {

			@Override
			public void run() {
				if (obj == null)
					Toast.makeText(UIUtils.getContext(), "对象为空", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(UIUtils.getContext(), obj.toString(), Toast.LENGTH_LONG)
							.show();
			}
		});

	}

	/**
	 * 上线运行时使用
	 *
	 * @param obj
	 */
	@SuppressLint("ShowToast")
	public static void showToastReal(final Object obj) {
		UIUtils.runInMainThread(new Runnable() {

			@Override
			public void run() {
				if (obj == null)
					Toast.makeText(UIUtils.getContext(), "对象为空", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(UIUtils.getContext(), obj.toString(), Toast.LENGTH_SHORT)
							.show();
			}
		});

	}
	}


