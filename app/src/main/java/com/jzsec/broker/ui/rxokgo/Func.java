package com.jzsec.broker.ui.rxokgo;

/**
 * Created by zhaopan on 2017/8/1.
 */

public interface Func<T, R> {
    R call(T t);
}
