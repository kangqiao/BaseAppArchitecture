package com.jzsec.broker.view.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;

/**
 * Created by zhaopan on 16/9/20.
 * e-mail: kangqiao610@gmail.com
 */
public class KLineChartView extends LinearLayout {

    private static final int LONG_PRESS = 2;
    private static final int SCALE_BEGIN = 1;
    private static final long CUSTOM_LONG_PRESS_TIMEOUT = 300;
    private static final long CUSTOM_SCALE_BEGIN_TIMEOUT = 500;

    private boolean mInLongPress = false;
    private boolean mInScaleProgress = false;
    private MotionEvent mCurrentDownEvent;
    private GestureDetector mGestureDetector;//单击和双击事件手势识别
    private ScaleGestureDetector mScaleGestureDetector;//缩放事件手势识别
    private MyGestureListener mMyGestureListener;
    private MyScaleGestureListener mMyScaleGestureListener;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LONG_PRESS:
                    dispatchLongPress();
                    break;
                case SCALE_BEGIN:
                    dispatchScaleBegin();
                    break;
                default:
                    throw new RuntimeException("Unknown message " + msg); //never
            }
        }
    };

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
        mMyGestureListener = new MyGestureListener();
        mGestureDetector = new GestureDetector(context, mMyGestureListener);
        mMyScaleGestureListener = new MyScaleGestureListener();
        mScaleGestureDetector = new ScaleGestureDetector(context, mMyScaleGestureListener);

        mGestureDetector.setIsLongpressEnabled(false);
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
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mInLongPress = false;
                if (mCurrentDownEvent != null) {
                    mCurrentDownEvent.recycle();
                }
                mCurrentDownEvent = MotionEvent.obtain(event);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.e("onTouchEvent", "MotionEvent.action="+event.getAction());
                cancel();
                break;
        }
        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        //return super.onTouchEvent(event);//不管返回值是什么，都能接收down事件，都能触发onDown、onShowPress、onLongPress
        return true;//但只有返回true才能继续接收move,up等事件，也才能响应ScaleGestureDetector事件及GestureDetector中与move,up相关的事件
    }

    private void dispatchLongPress() {
        if(null != getParent()) getParent().requestDisallowInterceptTouchEvent(true);
        mInLongPress = true;
        mMyGestureListener.onLongPress(mCurrentDownEvent);
    }

    private void dispatchScaleBegin() {
        if(null != getParent()) getParent().requestDisallowInterceptTouchEvent(true);
        mInLongPress = false;
        mInScaleProgress = true;
    }

    private void dispatchScaleEnd() {
        if(null != getParent()) getParent().requestDisallowInterceptTouchEvent(false);
        mHandler.removeMessages(SCALE_BEGIN);
        mInScaleProgress = false;
    }

    //ACTION_CANCEL and ACTION_UP
    private void cancel() {
        if(null != getParent()) getParent().requestDisallowInterceptTouchEvent(false);
        mHandler.removeMessages(LONG_PRESS);
        mHandler.removeMessages(SCALE_BEGIN);
        mInScaleProgress = false;
        mInLongPress = false;
    }

    /**
     * 注意：
     * 1. onSingleTapConfirmed（单击）和onSingleTapUp都是在down后既没有滑动onScroll，又没有长按onLongPress时， up 时触发的
     * 2. 非常快的点击一下：onDown->onSingleTapUp->onSingleTapConfirmed
     * 3. 稍微慢点的点击一下：onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed（最后一个不一定会触发）
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        ////////OnGestureListener/////////////////////////////
        @Override
        //Touch down时触发
        /**按下（onDown）： 刚刚手指接触到触摸屏的那一刹那，就是触的那一下*/
        public boolean onDown(MotionEvent e) {
            Log.e("view-手势", "onDown event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            return super.onDown(e);
        }

        final int FLING_MIN_DISTANCE = 100;  //X轴的坐标位移大于FLING_MIN_DISTANCE
        final int FLING_MIN_VELOCITY = 150;//200;  //移动速度大于FLING_MIN_VELOCITY个像素/秒

        @Override
        //onScroll一点距离后，【抛掷时】触发（若是轻轻的、慢慢的停止活动，而非抛掷，则很可能不触发）
        //参数为手指接触屏幕、离开屏幕一瞬间的动作事件，及手指水平、垂直方向移动的速度，像素/秒
        /**抛掷（onFling）： 手指在触摸屏上迅速移动，并松开的动作，onDown---onScroll---onScroll---…onFling*/
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("view-手势", "onFling e1.getRawX()=" + e1.getRawX() + ", e2.getRawX()=" + e2.getRawX() + ", velocityX=" + velocityX + ", velocityY=" + velocityY);
            if ((e2.getRawX() - e1.getRawX()) > FLING_MIN_DISTANCE /*&& Math.abs(velocityX) > FLING_MIN_VELOCITY*/) {
                Log.e("view-手势", "onFling-从左往右滑");
                return true;
            } else if (e1.getRawX() - e2.getRawX() > FLING_MIN_DISTANCE /*&& Math.abs(velocityX) > FLING_MIN_VELOCITY*/) {
                Log.e("view-手势", "onFling-从右往左滑");
                return true;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        //Touch了不移动一直Touch down时触发
        /**长按（onLongPress）： 手指按在持续一段时间，并且没有松开*/
        public void onLongPress(MotionEvent e) {
            Log.e("view-手势", "onLongPress event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            super.onLongPress(e);
        }

        @Override
        //Touch了滑动时触发，e1代表触摸时的事件，是不变的，e2代表滑动过程中的事件，是时刻变化的
        //distance是当前event2与上次回调时的event2之间的距离，代表上次回调之后到这次回调之前移动的距离
        /**滚动（onScroll）： 手指在触摸屏上滑动，onDown---onScroll---onScroll---…onScroll*/
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(mInLongPress) {
                Log.e("view-手势", "长按>>> " + e2.getAction () + ", onScroll-X-" + (int) e1.getX() + "/" + (int) e2.getX() + "/" + (int) distanceX + ", >>> onScroll-Y-" + (int) e1.getY() + "/" + (int) e2.getY() + "/" + (int) distanceY);
            }
            else{
                Log.e("view-手势", "滑动>>> " + e2.getAction () + ",onScroll-X-" + (int) e1.getX() + "/" + (int) e2.getX() + "/" + (int) distanceX + ", >>> onScroll-Y-" + (int) e1.getY() + "/" + (int) e2.getY() + "/" + (int) distanceY);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        //Touch了还没有滑动时触发
        /**按住（onShowPress）： 手指按在触摸屏上，在按下起效，在长按前失效，onDown->onShowPress->onLongPress*/
        public void onShowPress(MotionEvent e) {
            Log.e("view-手势", "onShowPress event.getAction=" + e.getAction() + ",  event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            mHandler.sendEmptyMessageAtTime(LONG_PRESS, e.getDownTime() + CUSTOM_LONG_PRESS_TIMEOUT);
            super.onShowPress(e);
        }

        @Override
        //在touch down后又没有滑动（onScroll），又没有长按（onLongPress），然后Touchup时触发。
        /**抬起（onSingleTapUp）：手指离开触摸屏的那一刹那*/
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("view-手势", "onSingleTapUp event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            return super.onSingleTapUp(e);
        }

        ////////OnDoubleTapListener/////////////////////////////
        @Override
        //在touch down后又没有滑动（onScroll），又没有长按（onLongPress），然后Touchup时触发。
        /**单击确认，即很快的按下并抬起，但并不连续点击第二下*/
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("view-手势", "onSingleTapConfirmed event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            return super.onSingleTapConfirmed(e);
        }

        @Override
        /**双击的【第二下】Touch down时触发（只执行一次）*/
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("view-手势", "onDoubleTap event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            return super.onDoubleTap(e);
        }

        @Override
        /**双击的【第二下】Touch down和up都会触发（执行次数不确定）。*/
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e("view-手势", "onDoubleTapEvent event.getAction=" + e.getAction() + ", event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            return super.onDoubleTapEvent(e);
        }

        ////////OnContextClickListener/////////////////////////////
        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.e("view-手势", "onContextClick event.getX()=" + e.getX() + ", event.getY()=" + e.getY());
            return super.onContextClick(e);
        }
    }

    /**
     * http://www.cnblogs.com/baiqiantao/p/5630506.html
     * public float getCurrentSpan () 返回手势过程中，组成该手势的两个触点的当前距离。
     * public long getEventTime () 返回事件被捕捉时的时间。
     * public float getFocusX () 返回当前手势焦点的 X 坐标。 如果手势正在进行中，焦点位于组成手势的两个触点之间。 如果手势正在结束，焦点为仍留在屏幕上的触点的位置。若 isInProgress() 返回 false，该方法的返回值未定义。
     * public float getFocusY ()  返回当前手势焦点的 Y 坐标。
     * public float getPreviousSpan () 返回手势过程中，组成该手势的两个触点的前一次距离。
     * public float getScaleFactor () 返回从前一个伸缩事件至当前伸缩事件的伸缩比率。该值定义为  getCurrentSpan() / getPreviousSpan()。
     * public long getTimeDelta () 返回前一次接收到的伸缩事件距当前伸缩事件的时间差，以毫秒为单位。
     * public boolean isInProgress () 如果手势处于进行过程中，返回 true。否则返回 false。
     */
    private class MyScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private static final float MIN_SCALE_DISTANCE = 20;
        /**
         * This is the active focal point in terms of the viewport. Could be a local
         * variable but kept here to minimize per-frame allocations.
         */
        private PointF viewportFocus = new PointF();
        private float lastSpan;
        private float lastSpanX;
        private float lastSpanY;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if(mInScaleProgress) {
                float distance = detector.getCurrentSpan() - lastSpan;
                if(Math.abs(distance) > MIN_SCALE_DISTANCE) {
                    lastSpan = detector.getCurrentSpan();
                    if (detector.getScaleFactor() < 1) { //缩小
                        Log.e("view-缩放", "onScale，缩小" + detector.getScaleFactor() + ", curSpan="+detector.getCurrentSpan()+", preSpan="+detector.getPreviousSpan()+", curSpan-preSpan=>"+distance +", timeDelta="+detector.getTimeDelta());
                        //Log.e("view-缩放", "onScale，缩小");
                    } else { //放大
                        Log.e("view-缩放", "onScale，放大" + detector.getScaleFactor() + ", curSpan="+detector.getCurrentSpan()+", preSpan="+detector.getPreviousSpan()+",  curSpan-preSpan=>"+distance +", timeDelta="+detector.getTimeDelta());
                    }
                }
            }
            return super.onScale(detector);
        }

        @Override
        //缩放开始, 一次缩放仅执行一次
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.e("view-缩放", "onScaleBegin");
            mHandler.sendEmptyMessageAtTime(SCALE_BEGIN, detector.getEventTime() + CUSTOM_SCALE_BEGIN_TIMEOUT);
            return super.onScaleBegin(detector);
        }

        @Override
        //缩放结束, 一次缩放仅执行一次
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.e("view-缩放", "onScaleEnd");
            dispatchScaleEnd();
        }
    }

    /**
     * @see ScaleGestureDetector#getCurrentSpanX()
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static float getCurrentSpanX(ScaleGestureDetector scaleGestureDetector) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return scaleGestureDetector.getCurrentSpanX();
        } else {
            return scaleGestureDetector.getCurrentSpan();
        }
    }

    /**
     * @see ScaleGestureDetector#getCurrentSpanY()
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static float getCurrentSpanY(ScaleGestureDetector scaleGestureDetector) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return scaleGestureDetector.getCurrentSpanY();
        } else {
            return scaleGestureDetector.getCurrentSpan();
        }
    }
}
