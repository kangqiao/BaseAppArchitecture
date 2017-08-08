package com.jzsec.broker.ui.rxokgo;

/**
 * Created by zhaopan on 2017/8/1.
 */

public class ApiHelper {
    private NetApi netApi;

    public ApiHelper() {
        netApi = new NetApi();
    }

    public NetApi getNetApi() {
        return netApi;
    }
}
