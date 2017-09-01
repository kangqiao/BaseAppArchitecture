package com.jzsec.broker.ui.rxokgo;

/**
 * Created by zhaopan on 2017/8/30.
 */

public interface Callback<T> {
    void onSuccess(T t);
    void onError(Throwable e);
}
