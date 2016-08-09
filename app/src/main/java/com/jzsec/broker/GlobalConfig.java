package com.jzsec.broker;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zhaopan on 16/7/27.
 */
public interface GlobalConfig {
    String DEFAULT_HOME_PAGE = "file:///android_asset/www/home/index.html";
    // web首页版本号
    String HOME_PAGE_VER = "home_page_ver";
    String HOME_PAGE_URL = "home_page_url";

    class DeviceParam{
        public static int screenWidth;
        public static int screenHeight;

        public static void initScreenMetrics(Context context){
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
        }
    }

    class VersionInfo{
        public static int versionCode;

        public static int getVersionCode(){
            //App.
            return 0;
        }
    }



    //base
    int PAGE_COUNT = 10;

    // intent
    String HEAD_DATA = "data";
    String COUNT = "count";
    String VH_CLASS = "vh";
    // RxBusEventName

    String EVENT_LOGIN = "login";
    String EVENT_DEL_ITEM = "delete_item";
    String EVENT_UPDATE_ITEM = "update_item";
    String EVENT_HEADDATA = "get_headdata";
    String EVENT_COUNT = "get_count";

    // TagName
    String TAG_EDITABLE = "editable";
    String TAG_HEADDATA = "with_headdata";
}
