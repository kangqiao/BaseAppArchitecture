package com.jzsec.broker.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.jzsec.broker.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */

public class CommonUtil {

    public static String createDeviceId() {
        String deviceId = "";
        String fileName = Environment.getExternalStorageDirectory() + "/config";
        //如果sd卡可以使用，读取sd中的信息
        if (TextUtils.isEmpty(deviceId) && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(fileName);
            if (file.exists()) {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(file);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes,0,fis.available());
                    deviceId = new String(bytes);
                    fis.close();
                } catch (Exception e) {

                }
            }
        }
        //如果读取到的devidceId为空，生成一个并存入SD卡和sharepreference
        if (TextUtils.isEmpty(deviceId)) {
            String uuid = StringUtil.generateID();
            deviceId = uuid;
            File file = new File(fileName);
            try {
                FileOutputStream os = new FileOutputStream(file);
                byte[] bytes = deviceId.getBytes();
                os.write(bytes, 0, bytes.length);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //UserInfoHelper.saveDeviceKey(deviceId);
        return deviceId;
    }


    public static int getAppVer() {
        int version = -1;

        try {
            PackageManager packageManager = App.getAppContext().getPackageManager();
            PackageInfo pi = packageManager.getPackageInfo(App.getAppContext().getPackageName(), 0);
            version = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
