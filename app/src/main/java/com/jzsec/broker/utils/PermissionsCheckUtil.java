package com.jzsec.broker.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by zhaopan on 16/9/8.
 * e-mail: kangqiao610@gmail.com
 */
public class PermissionsCheckUtil {
    private static final String TAG = "PermissionsCheck";

    private static int MVersion = 23;

    public static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    public static String[] getPermissionList(Context context) {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return packInfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 判断权限集合
    public static boolean lacksPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= MVersion && null != context && null != permissions && permissions.length > 0) {
            for (String permission : permissions) {
                if (lacksPermission(context, permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> getLacksPermissions(Context context, String... permissions) {
        List<String> resultList = null;
        if (Build.VERSION.SDK_INT >= MVersion && null != context && null != permissions && permissions.length > 0) {
            resultList = new ArrayList<>();
            for (String permission : permissions) {
                if (lacksPermission(context, permission)) {
                    resultList.add(permission);
                }
            }
        }
        return resultList;
    }

    // 判断是否缺少权限
    private static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    // 启动应用的设置
    public static void startAppSettings(Context context) {
        if(null != context) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse(PermissionsCheckUtil.PACKAGE_URL_SCHEME + context.getPackageName()));
            context.startActivity(intent);
        }
    }

    public interface CheckPermissionCallback<T> {
        void onCheck(T result);
    }

    /**
     * 检查AndroidManifest.xml中所申请的所有权限是否被授予.
     * 当低于6.0的系统使用时, 总是返回true.
     * 注: 对于新加入到AndroidManifest.xml中权限, 请慎重测试后再使用此方法.
     * 可能会有始终返回为false的权限(例如: MOUNT_UNMOUNT_FILESYSTEMS ).
     *
     * @param context
     * @param callback
     */
    public static void checkAllPermission(Context context, final CheckPermissionCallback<Boolean> callback) {
        if (Build.VERSION.SDK_INT >= MVersion && null != context && null != callback) {
            RxPermissions.getInstance(context)
                    .request(getPermissionList(context))
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            log("checkAllPermission granted=" + granted);
                            callback.onCheck(granted);
                        }
                    });
        } else {
            callback.onCheck(true);
        }
    }

    /**
     * 检查指定权限是否被授予, 会遍历指定权限列表.
     *
     * @param context
     * @param callback
     * @param permissions
     */
    public static void checkPermission(final Context context, final CheckPermissionCallback<Permission> callback, String... permissions) {
        if (Build.VERSION.SDK_INT >= MVersion && null != context && null != callback && null != permissions && permissions.length > 0) {
            RxPermissions.getInstance(context)
                    .requestEach(permissions)
                    .subscribe(new Action1<Permission>() {
                        @Override
                        public void call(Permission permission) {
                            log(permission.toString());
                            callback.onCheck(permission);
                        }
                    });
        } else {
            callback.onCheck(new Permission("NO_CHECK_PRE-M", true));
        }
    }

    private static void log(String msg) {
        Log.w(TAG, "zp:::" + msg);
    }

}