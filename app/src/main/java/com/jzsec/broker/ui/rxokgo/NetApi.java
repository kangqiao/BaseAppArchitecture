package com.jzsec.broker.ui.rxokgo;

import android.net.Uri;

import com.jzsec.broker.App;
import com.jzsec.broker.utils.ACache;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import com.lzy.okgo.request.base.Request;

/**
 * Created by zhaopan on 2017/8/1.
 */

public class NetApi {

    public AsyncJob<Response> getNewsList(int page, int itemCount){
        /*return new AsyncJob<Response>() {
            @Override
            public void start(final Callback<Response> callback) {
                OkGo.get("")
                        .tag(this)
                        .params("pno", page)
                        .params("ps", itemCount)
                        .params("key", "")
                        .execute(new StringCallback<String>(){

                            @Override
                            public void onSuccess(Response<String> response) {

                            }
                        });
            }
        };*/
        return null;
    }

    public Uri save(String title){
        /*return new AsyncJob<Uri>(){

            @Override
            public void start(Callback<Uri> callback) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ACache.get(App.getAppContext()).put("title", title);
                        callback.onSuccess(Uri.parse("lzy://success"));
                    }
                }).start();
            }
        };*/
        return null;
    }
}
