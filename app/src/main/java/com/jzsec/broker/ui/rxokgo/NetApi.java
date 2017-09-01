package com.jzsec.broker.ui.rxokgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.jzsec.broker.App;
import com.jzsec.broker.net.URLs;
import com.jzsec.broker.utils.Zlog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by zhaopan on 2017/8/30.
 */

public class NetApi {

    public static final String URL = "NetApi";
    private static final int APP_KEY = 110;


    public AsyncJob<Response> getNewsList(final int page, final int itemCount) {
        return new AsyncJob<Response>() {
            @Override
            public void start(Callback<Response> callback) {
                OkGo.<String>get(URLs.LOGIN)
                        .tag(this)
                        .params("mobilephone", "18511611085")
                        .params("password", "qaz123")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Zlog.e(TAG, response.body());
                                response.setBody("News-1;News-2;News-3");
                                callback.onSuccess(response);
                            }

                            @Override
                            public void onError(Response<String> response) {
                                Zlog.e(TAG, response.getException().getMessage());
                                callback.onError(response.getException());
                            }
                        });
            }
        };
    }


    public String findLatestTitle(Response response) {
        String newsStr = (String) response.body();
        String[] newsArr = newsStr.split(";");
        String ret = "";
        for(String title: newsArr){
            Zlog.e(TAG, title);
            ret = title;
        }
        return ret;
    }

    public AsyncJob<Uri> save(String title) {
        return new AsyncJob<Uri>() {
            @Override
            public void start(Callback<Uri> callback) {
                try {
                    SharedPreferences sp = App.getAppContext().getSharedPreferences("test", Context.MODE_PRIVATE);
                    sp.edit().putString("title", title).apply();
                    callback.onSuccess(Uri.parse("zp://success"));
                } catch (Exception e){
                    callback.onError(e);
                }
            }
        };
    }
}