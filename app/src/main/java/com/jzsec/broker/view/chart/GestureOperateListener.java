package com.jzsec.broker.view.chart;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by zhaopan on 16/9/22.
 * e-mail: kangqiao610@gmail.com
 */

public interface GestureOperateListener {

    /**
     * 只要有 ACTION_UP 或 ACTION_CANCEL
     */
    void onUpOrCancel();

    /**
     * 缩放
     * if true zoom in, else zoom out
     * @param detector
     */
    void onZoom(ScaleGestureDetector detector);

    /**
     * 滑动, 注意: 之前没有进行长按.
     * @param downEvent
     * @param event
     * @param distanceX
     * @param distanceY
     */
    void onMove(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY);

    /**
     * 长按后滑动, 回调多次
     * @param event
     */
    void onMoveAfterLongPress(MotionEvent event);

    /**
     * 长按 仅回调一次
     * @param event
     */
    void onLongPress(MotionEvent event);

    /**
     * 快速点击. 第一次 touch up时, 后续又没有任何操作(滑动, 不处理长按中, 缩放)时回调
     * @param event
     */
    void onSingleTap(MotionEvent event);

    /**
     * 双击. 第二次 touch up时, 回调
     * @param event
     */
    void onDoubleTap(MotionEvent event);

    /**
     * 快速滑动后的最后一个动作
     * @param downEvent 起点
     * @param event 终点
     * @param velocityX 水平方向移动的速度，像素/秒
     * @param velocityY 垂直方向移动的速度，像素/秒
     */
    void onFling(MotionEvent downEvent, MotionEvent event, float velocityX, float velocityY);

}
