package com.jzsec.broker;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.didi.virtualapk.PluginManager;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.jzsec.broker.data.entity.CommonParam;
import com.jzsec.broker.okgo.InitOkGo;
import com.jzsec.broker.utils.SpUtil;
import com.jzsec.broker.utils.Zlog;
import com.jzsec.broker.weex.ImageAdapter;
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
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initSophix();
        initWeex();
        initialize();
        InitOkGo.onCreate(this);
        //com.jzsec.leancloud.LeanCloudSetting.onCreate(this);
        //初始化VCamera => http://www.jianshu.com/p/5a173841a828
        //cn.kq.wxrecord.WXRecordInitializer.onCreate(this);
    }

    private void initSophix() {
        com.taobao.sophix.SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new com.taobao.sophix.listener.PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Zlog.i("code","mode = "+mode+"info = "+ info);
                        // 补丁加载回调通知
                        if (code == com.taobao.sophix.PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Zlog.i("code","表明补丁加载成功");
                        } else if (code == com.taobao.sophix.PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Zlog.i("code","用户可以监听进入后台事件, 然后应用自杀");
                        } else if (code == com.taobao.sophix.PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            // SophixManager.getInstance().cleanPatches();
                            Zlog.i("code","内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Zlog.i("code"," 其它错误信息, 查看PatchStatus类说明2222");
                        }
                    }
                }).initialize();
        com.taobao.sophix.SophixManager.getInstance().queryAndLoadNewPatch();
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
