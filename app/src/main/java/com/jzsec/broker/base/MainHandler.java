package com.jzsec.broker.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by zhaopan on 2017/3/8.
 * e-mail: kangqiao610@gmail.com
 * 使用静态内部类，弱引用外部类，防止内存泄露
 */

public class MainHandler<T extends MainHandler.MessageHandler> extends Handler {

    interface MessageHandler{
        void handleMainMessage(Message msg);
    }

    public WeakReference<T> weakHolder;

    public MainHandler(T obj) {
        super(Looper.getMainLooper());
        weakHolder = new WeakReference<>(obj);
    }

    @Override
    public void handleMessage(Message msg) {
        if (weakHolder != null) {
            T obj = weakHolder.get();
            if (obj != null) {
                obj.handleMainMessage(msg);
            }
        }
    }
}
