package com.jzsec.broker.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jzsec.broker.R;

/**
 * Created by zhaopan on 15/12/15 15:15
 * e-mail: kangqiao610@gmail.com
 */
public abstract class ZPopupWindow<Data> extends PopupWindow {
    private static final String TAG = "ZPopupWindow";

    protected View contentView;

    public ZPopupWindow(Activity context) {
        contentView = onCreatePopupView(LayoutInflater.from(context));
        setContentView(contentView);

        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置PopupWindow弹出窗体不可点击
        this.setTouchable(true);
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();

        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_win_anim);

        setDismissable();
    }

    public void setDismissable(){
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
    }

    @Override
    public View getContentView() {
        return contentView;
    }

    protected <T extends View> T findView(int id) {
        return (T) contentView.findViewById(id);
    }

    protected void setText(int resId, CharSequence content) {
        View view = findView(resId);
        if (null != view) {
            if (view instanceof TextView) {
                ((TextView) view).setText(content);
            } else if (view instanceof EditText) {
                ((EditText) view).setText(content);
            } else if (view instanceof Button) {
                ((Button) view).setText(content);
            }
        }
    }

    protected abstract View onCreatePopupView(LayoutInflater inflater);

    public abstract void refreshView(Data data);

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
        }
    }

    /**
     * 显示在控件上方.
     * @param parent
     */
    public void showPopupWindowUp(View parent) {
        if (!this.isShowing()) {
            //this.showAtLocation(parent, GR 0,0);this
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popWinHeight = contentView.getMeasuredHeight();
            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1]-popWinHeight);
        }
    }

    /**
     * 显示在控件左方.
     * @param parent
     */
    public void showPopupWindowLeft(View parent) {
        if (!this.isShowing()) {
            //this.showAtLocation(parent, GR 0,0);this
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popWinWidth = contentView.getMeasuredWidth();
            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]-popWinWidth, location[1]);
        }
    }

    /**
     * 显示在控件右方.
     * @param parent
     */
    public void showPopupWindowRight(View parent) {
        if (!this.isShowing()) {
            //this.showAtLocation(parent, GR 0,0);this
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popWinWidth = contentView.getMeasuredWidth();
            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]+popWinWidth, location[1]);
        }
    }
}
