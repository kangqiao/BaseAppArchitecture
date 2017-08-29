package com.jzsec.broker.utils;

import android.widget.Toast;

import com.jzsec.broker.App;


/**
 * Description :
 * Author : AllenJuns
 * Date   : 2016-3-04
 */
public class CommonUtils {
    public static void showLongToast( String pMsg) {
        Toast.makeText(App.getAppContext(), pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String pMsg) {
        Toast.makeText(App.getAppContext(), pMsg, Toast.LENGTH_SHORT).show();
    }
}
