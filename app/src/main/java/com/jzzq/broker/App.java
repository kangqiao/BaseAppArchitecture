package com.jzzq.broker;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.jzzq.broker.utils.SpUtil;

/**
 * Created by zhaopan on 16/7/27.
 */
public class App extends Application{
    public static final String TAG = "App";

    private static App mApp;

    public static Context getAppContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initialize();
    }

    /**
     * 初始化操作.
     */
    private void initialize() {
        SpUtil.init(this);
        GlobalConfig.DeviceParam.initScreenMetrics(this);
    }



}
