package com.jzsec.leancloud;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by zhaopan on 2017/8/8.
 */

public class LeanCloudSetting {

    public static void onCreate(Application app){
        // 启用北美节点, 需要在 initialize 之前调用
        // AVOSCloud.useAVCloudUS();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(app,"{{appid}}","{{appkey}}");
        // 开启调试日志
        AVOSCloud.setDebugLogEnabled(true);
    }
}
