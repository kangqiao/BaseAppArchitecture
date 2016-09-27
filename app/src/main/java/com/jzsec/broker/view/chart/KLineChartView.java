package com.jzsec.broker.view.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;

import com.jzsec.broker.view.chart.GestureDetectHandler.GestureOperateListener;

/**
 * Created by zhaopan on 16/9/20.
 * e-mail: kangqiao610@gmail.com
 */
public class KLineChartView extends LinearLayout {

    private GestureDetector mGestureDetector;//单击和双击事件手势识别
    private ScaleGestureDetector mScaleGestureDetector;//缩放事件手势识别
    private GestureDetectHandler mGestureDetectHandler;

    public KLineChartView(Context context) {
        super(context);
        init(context, null);
    }

    public KLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        //属性参数设置
        if (null != attrs) {

        } //默认参数设置
        else {

        }
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(attrs);
        //com.android.internal.R.dimen.config_viewConfigurationTouchSlop
        mGestureDetectHandler = new GestureDetectHandler(this, supportGestureOperate);
        //mGestureDetector = new GestureDetector(context, mGestureDetectHandler);
        //mScaleGestureDetector = new ScaleGestureDetector(context, mGestureDetectHandler);

        //mGestureDetector.setIsLongpressEnabled(false);
        mGestureDetectHandler.setDelayedCancelTimeoutAfterLongPress(2000);
        //mGestureDetectHandler.setIsLongpressEnabled(false);
    }

    /**
     * 默认情况下，GestureDetector是监听不到MotionEvent事件的，也即GestureDetector的onTouchEvent方法是不会被调用的，若要被调用，必须满足两个条件
     * ①在View的onTouchEvent()方法中，须将MotionEvent事件手动【传】给GestureDetector
     * ②若View设置了.setOnTouchListener()监听器，则其onTouch()的返回值还须是【false】，或者在onTouch()中将MotionEvent事件手动【传】给GestureDetector
     * 只有这样，GestureDetector 注册的OnGestureListener(单击)或OnDoubleTapListener(双击)才能获得完整的MotionEvent事件，进而根据该对象封装的的信息，做出合适的反馈。
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetectHandler.onTouchEvent(event);
    }


    private GestureOperateListener supportGestureOperate = new GestureOperateListener() {
        @Override
        public void onUpOrCancel() {
            loge("onUpOrCancel");
//            mainView.onUpOrCancel();
//            subView.onUpOrCancel();
        }

        @Override
        public void onZoom(ScaleGestureDetector detector) {
            loge("onZoom");
//            if(isSupportZoom) {
//                mainView.onZoom(detector);
//                subView.onZoom(detector);
//            }
        }

        @Override
        public void onMove(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
            loge("onMove");
//            mainView.onMove(downEvent, event, distanceX, distanceY);
//            subView.onMove(downEvent, event, distanceX, distanceY);
        }

        @Override
        public void onMoveAfterLongPress(MotionEvent event) {
            loge("onMoveAfterLongPress");
//            mainView.onMoveAfterLongPress(event);
//            subView.onMoveAfterLongPress(event);
        }

        @Override
        public void onLongPress(MotionEvent event) {
            loge("onLongPress");
//            mainView.onLongPress(event);
//            subView.onLongPress(event);
        }

        @Override
        public void onClick(MotionEvent event) {
            loge("onClick");
//            performClickChart();
        }

        @Override
        public void onDoubleClick(MotionEvent event) {
            loge("onDoubleClick");
            /*mainView.onDoubleClick(event);
            subView.onDoubleClick(event);*/
        }

        @Override
        public void onFling(MotionEvent downEvent, MotionEvent event, float velocityX, float velocityY) {
            loge("onFling");
            /*mainView.onFling(downEvent, event, velocityX, velocityY);
            subView.onFling(downEvent, event, velocityX, velocityY);*/
        }

        @Override
        public void postInvalidate() {
            loge("postInvalidate");
            KLineChartView.this.postInvalidate();
        }

        private void loge(String msg){
            Log.e("zp>>>OPT", msg);
        }
    };
}
