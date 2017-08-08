package com.jzsec.broker;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.jzsec.broker.data.entity.CommonParam;
import com.jzsec.broker.okgo.InitOkGo;
import com.jzsec.broker.utils.SpUtil;
import com.jzsec.broker.weex.ImageAdapter;
import com.jzsec.leancloud.LeanCloudSetting;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

/**
 * Created by zhaopan on 16/7/27.
 */
public class App extends Application /*implements RouterCallbackProvider*/ {
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
        initWeex();
        initialize();
        InitOkGo.onCreate(this);
        //LeanCloudSetting.onCreate(this);
        //初始化VCamera => http://www.jianshu.com/p/5a173841a828
        cn.kq.wxrecord.WXRecordInitializer.onCreate(this);
    }

    private void initWeex() {
        InitConfig config=new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(this,config);
    }

    /**
     * 初始化操作.
     */
    private void initialize() {
        SpUtil.init(this);
        CommonParam.getInstance().init();
        GlobalConfig.DeviceParam.initScreenMetrics(this);
    }


    /*@Override*/
    public RouterCallback provideRouterCallback() {
        return new SimpleRouterCallback();
    }
}
