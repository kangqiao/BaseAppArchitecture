package com.jzsec.broker.view.chart;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by zhaopan on 16/9/24.
 * e-mail: kangqiao610@gmail.com
 */
public class GestureDetectorHandler extends GestureDetector.SimpleOnGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
    public static final boolean DEBUG = false;
    //最小缩放距离, 单位:像素
    public static final int MIN_SCALE_DISTANCE = 20;
    //最短缩放时间, 单位:毫秒
    public static final int MIN_SCALE_TIME_MILLI = 10;
    //最小移动距离
    public static final int MIN_MOVE_DISTANCE = 5;

    //X轴的坐标位移大于FLING_MIN_DISTANCE
    public static final int FLING_MIN_DISTANCE = 100;
    //移动速度大于FLING_MIN_VELOCITY个像素/秒
    public static final int FLING_MIN_VELOCITY = 150;

    public static final long CUSTOM_LONG_PRESS_TIMEOUT = 300;
    public static final long CUSTOM_SCALE_BEGIN_TIMEOUT = 0;
    public static final long CUSTOM_CANCEL_TIMEOUT = 10;

    private static final int LONG_PRESS = 1;
    private static final int SCALE_BEGIN = 2;
    private static final int CANCEL = 3;

    private long mLongPressTimeout = CUSTOM_LONG_PRESS_TIMEOUT;
    private long mScaleBeginTimeout = CUSTOM_SCALE_BEGIN_TIMEOUT;
    private boolean mInLongPress = false;
    private boolean mInScaleProgress = false;
    private boolean mIsCustomLongPressEnabled = true;
    private float lastSpan;

    private View mDetectedView;
    private GestureOperateListener mGestureOptListener;
    private Handler mHandler;
    private MotionEvent showPressEvent;


    public GestureDetectorHandler(View detectedView, GestureOperateListener operator) {
        mDetectedView = detectedView;
        mHandler = new GestureHandler(detectedView.getHandler().getLooper());
        mGestureOptListener = operator;
    }

    private class GestureHandler extends Handler {
        GestureHandler() {
            super();
        }

        GestureHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LONG_PRESS:
                    dispatchLongPress();
                    break;
                case SCALE_BEGIN:
                    dispatchScaleBegin();
                    break;
                case CANCEL:
                    dispatchCancel();
                    break;
                default:
                    throw new RuntimeException("Unknown message " + msg); //never
            }
        }
    }

    public void setCustomLongPressEnabled(boolean isCustomLongPressEnabled) {
        mIsCustomLongPressEnabled = isCustomLongPressEnabled;
    }

    private void dispatchLongPress() {
        mDetectedView.getParent().requestDisallowInterceptTouchEvent(true);
        mInLongPress = true;
        onLongPress(showPressEvent);
    }

    private void dispatchScaleBegin() {
        mDetectedView.getParent().requestDisallowInterceptTouchEvent(true);
        mInLongPress = false;
        mInScaleProgress = true;
    }

    //ACTION_CANCEL and ACTION_UP
    private void dispatchCancel() {
        mDetectedView.getParent().requestDisallowInterceptTouchEvent(false);
        mHandler.removeMessages(LONG_PRESS);
        mHandler.removeMessages(SCALE_BEGIN);
        mHandler.removeMessages(CANCEL);
        mInScaleProgress = false;
        mInLongPress = false;
        mGestureOptListener.onUpOrCancel();
    }

    public void setScaleBeginTimeout(long scaleBeginTimeout) {
        this.mScaleBeginTimeout = scaleBeginTimeout;
    }

    public void setLongPressTimeout(long longPressTimeout) {
        this.mLongPressTimeout = longPressTimeout;
    }

    ////////OnGestureListener/////////////////////////////
    @Override
    public boolean onDown(MotionEvent e) {
        if(DEBUG) Log.e("view-手势", "onDown event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
        mInLongPress = false;
        return super.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent event, float velocityX, float velocityY) {
        if(DEBUG) Log.e("view-手势", "onFling e1.getRawX()=" + downEvent.getRawX() + ", e2.getRawX()=" + event.getRawX() + ", velocityX=" + velocityX + ", velocityY=" + velocityY);
        mGestureOptListener.onFling(downEvent, event, velocityX, velocityY);
        dispatchCancel();
        if ((event.getRawX() - downEvent.getRawX()) > FLING_MIN_DISTANCE /*&& Math.abs(velocityX) > FLING_MIN_VELOCITY*/) {
            if(DEBUG) Log.e("view-手势", "onFling-从左往右滑");
            return true;
        } else if (downEvent.getRawX() - event.getRawX() > FLING_MIN_DISTANCE /*&& Math.abs(velocityX) > FLING_MIN_VELOCITY*/) {
            if(DEBUG) Log.e("view-手势", "onFling-从右往左滑");
            return true;
        }

        return super.onFling(downEvent, event, velocityX, velocityY);
    }

    @Override
    public void onLongPress(MotionEvent event) {
        if(DEBUG) Log.e("view-手势", "onLongPress event.getAction=" + event.getAction() + ", event.getX()=" + event.getX() + ", event.getY()=" + event.getY());
        mGestureOptListener.onLongPress(event);
        super.onLongPress(event);
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
        if(DEBUG) Log.e("view手势", ">>> onScroll-X-" + (int) downEvent.getX() + "-" + (int) event.getX() + "-" + (int) distanceX + "-" + ", >>> onScroll-Y-" + (int) downEvent.getY() + "-" + (int) event.getY() + "-" + (int) distanceY);
        if(mInLongPress){ //长按后的滑动.
            mGestureOptListener.onMoveAfterLongPress(event);
        }
        else{ //普通滑动
            mGestureOptListener.onMove(downEvent, event, distanceX, distanceY);
        }
        return super.onScroll(downEvent, event, distanceX, distanceY);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        if(DEBUG) Log.e("view-手势", "onShowPress event.getAction=" + e.getAction() + ",  event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
        if (mIsCustomLongPressEnabled) mHandler.sendEmptyMessageAtTime(LONG_PRESS, e.getDownTime() + mLongPressTimeout);
        showPressEvent = e;
        super.onShowPress(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(DEBUG) Log.e("view-手势", "onSingleTapUp event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
        dispatchCancel();
        return super.onSingleTapUp(e);
    }

    ////////OnDoubleTapListener/////////////////////////////
    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        if(DEBUG) Log.e("view-手势", "onSingleTapConfirmed event.getAction=" + event.getAction() + ", event.getX()=" + event.getX() + ", event.getY()=" + event.getY());
        if(!mInLongPress) mGestureOptListener.onSingleTap(event);
        return super.onSingleTapConfirmed(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if(DEBUG) Log.e("view-手势", "onDoubleTap event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        if(DEBUG) Log.e("view-手势", "onDoubleTapEvent event.getAction=" + event.getAction() + ", event.getX()=" + event.getX() + ", event.getY()=" + event.getY());
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            mGestureOptListener.onDoubleTap(event);
            dispatchCancel();
        }
        return super.onDoubleTapEvent(event);
    }

    ////////OnContextClickListener/////////////////////////////
    @Override
    public boolean onContextClick(MotionEvent e) {
        if(DEBUG) Log.e("view-手势", "onContextClick event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
        return super.onContextClick(e);
    }

    ////////OnScaleGestureListener/////////////////////////////
    /**
     * This is the active focal point in terms of the viewport. Could be a local
     * variable but kept here to minimize per-frame allocations.
     */
    //private PointF viewportFocus = new PointF();
    //private float lastSpanX;
    //private float lastSpanY;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if(mInScaleProgress) { //长按后的缩放
            float distance = detector.getCurrentSpan() - lastSpan;
            if(Math.abs(distance) > MIN_SCALE_DISTANCE) {
                lastSpan = detector.getCurrentSpan();
                mGestureOptListener.onZoom(detector);
            }
        }
        return false;
    }

    @Override
    //缩放开始, 一次缩放仅执行一次
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        if(DEBUG) Log.e("view-缩放", "onScaleBegin");
        mHandler.sendEmptyMessageAtTime(SCALE_BEGIN, detector.getEventTime() + mScaleBeginTimeout);
        return true;
    }

    @Override
    //缩放结束, 一次缩放仅执行一次
    public void onScaleEnd(ScaleGestureDetector detector) {
        if(DEBUG) Log.e("view-缩放", "onScaleEnd");
        dispatchCancel();
    }
}
