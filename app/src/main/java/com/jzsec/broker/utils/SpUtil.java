package com.jzsec.broker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.jzsec.broker.base.BaseActivity;
import com.jzsec.broker.data.entity.LoginInfo;

/**
 * Created by zhaopan on 2016/4/5.
 */
public class SpUtil {
    static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isNight() {
        return prefs.getBoolean("isNight", false);
    }

    public static void setNight(Context context, boolean isNight) {
        prefs.edit().putBoolean("isNight", isNight).commit();
        if (context instanceof BaseActivity)
            ((BaseActivity) context).reload();
    }

    public static LoginInfo getLoginInfo() {
        return new Gson().fromJson(prefs.getString("loginInfo", ""), LoginInfo.class);
    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        prefs.edit().putString("loginInfo", new Gson().toJson(loginInfo)).commit();
    }
}
