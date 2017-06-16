package com.jzsec.broker.okgo;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String msg;
    public T data;

    public T getData(){
        return data;
    }

    public SimpleResponse toResponseData() {
        SimpleResponse response = new SimpleResponse();
        response.code = code;
        response.msg = msg;
        return response;
    }
}