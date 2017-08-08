package com.jzsec.broker.ui.rxokgo;

/**
 * Created by zhaopan on 2017/8/1.
 */

public interface Callback<T> {
    void onSuccess(T t);

    void onError(Exception e);
}
