package com.jzsec.broker.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by zhaopan on 2017/8/17.
 * 基于Handler的定时器.
 */

public class HandlerTimer implements Runnable{
    public static int DEFAULT_TIMER_DELAY = 5000;
    public Handler mHandler;
    public Runnable mTask;
    public boolean mImmediately = true;
    public int mDelay = DEFAULT_TIMER_DELAY;

    public HandlerTimer(Handler handler){
        this.mHandler = handler;
    }

    public HandlerTimer(Looper looper) {
        this(new Handler(looper));
    }

    public HandlerTimer setDelay(int delay){
        this.mDelay = delay;
        return this;
    }

    public HandlerTimer setTask(Runnable task) {
        this.mTask = task;
        return this;
    }

    public void startTimer(){
        startTimer(mImmediately);
    }

    public void startTimer(boolean immediately){
        mImmediately = immediately;
        if(mImmediately){
            this.run();
        } else {
            mHandler.postDelayed(this, mDelay);
        }
    }

    public void startTimerDelay(){
        startTimer(false);
    }

    public void stopTimer(){
        if(null != mHandler) mHandler.removeCallbacks(mTask);
    }

    @Override
    public void run() {
        if(null != mTask) {
            mTask.run();
            mHandler.postDelayed(this, mDelay);
        }
    }
}