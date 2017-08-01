package com.jzsec.broker.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.jzsec.broker.R;

import java.lang.reflect.Field;

public class ViewUtil {

	/**
	 * activity自动findview
	 */
	public static void autoFind(Activity activity) {
		try {
			Class<?> clazz = activity.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				if (field.getGenericType().toString().contains("widget")
						|| field.getGenericType().toString().contains("view")
						|| field.getGenericType().toString()
								.contains("WebView")) {// 找到所有的view和widget,WebView
					try {
						String name = field.getName();
						Field idfield = R.id.class.getField(name);
						int id = idfield.getInt(new R.id());// 获得view的id
						field.setAccessible(true);
						field.set(activity, activity.findViewById(id));// 给我们要找的字段设置值
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fragment以及ViewHolder等自动findview
	 */

	public static void autoFind(Object obj, View view) {
		try {
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				if (field.getGenericType().toString().contains("widget")
						|| field.getGenericType().toString().contains("view")
						|| field.getGenericType().toString()
								.contains("WebView")) {// 找到所有的view和widget
					try {
						String name = field.getName();
						Field idfield = R.id.class.getField(name);
						int id = idfield.getInt(new R.id());
						field.setAccessible(true);
						field.set(obj, view.findViewById(id));// 给我们要找的字段设置值
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideKeyboard(Activity c) {
		try {
			InputMethodManager imm = (InputMethodManager) c
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
		} catch (NullPointerException e) {
		}
	}

	public static void setUpIndicatorWidth(TabLayout tabLayout, int marginLeft, int marginRight) {
		Class<?> tabLayoutClass = tabLayout.getClass();
		Field tabStrip = null;
		try {
			tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
			tabStrip.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		LinearLayout layout = null;
		try {
			if (tabStrip != null) {
				layout = (LinearLayout) tabStrip.get(tabLayout);
			}
			for (int i = 0; i < layout.getChildCount(); i++) {
				View child = layout.getChildAt(i);
				child.setPadding(0, 0, 0, 0);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					params.setMarginStart(DensityUtil.dip2px(tabLayout.getContext(), marginLeft));
					params.setMarginEnd(DensityUtil.dip2px(tabLayout.getContext(), marginRight));
				}
				child.setLayoutParams(params);
				child.invalidate();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
