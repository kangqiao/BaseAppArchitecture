package com.jzsec.broker.ui.rxokgo;

public interface Func<T, R> {
    R call(T t);
}