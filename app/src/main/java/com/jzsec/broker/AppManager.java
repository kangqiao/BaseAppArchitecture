package com.jzsec.broker;

import android.app.ActivityManager;
import android.content.Context;

import com.jzsec.broker.base.BaseActivity;

import java.util.Stack;

/**
 * Created by zhaopan on 16/8/4.
 * e-mail: kangqiao610@gmail.com
 */
public class AppManager implements Cloneable{
    private static final String TAG = "AppManager";
    private static class AppManagerHolder {
        private static final AppManager INSTANCE = new AppManager();
    }

    public static AppManager getInstance(){
        return AppManagerHolder.INSTANCE;
    }

    private static Stack<BaseActivity> sActivityStack;

    private AppManager(){
        sActivityStack = new Stack<BaseActivity>();
    }


    /**
     * 添加Activity到栈
     */
    public void addActivity(BaseActivity activity) {
        sActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public BaseActivity currentActivity() {
        if (sActivityStack == null || sActivityStack.isEmpty()) {
            return null;
        }
        BaseActivity activity = sActivityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public BaseActivity findActivity(Class<?> cls) {
        BaseActivity activity = null;
        for (BaseActivity aty : sActivityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        BaseActivity activity = sActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(BaseActivity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (BaseActivity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (BaseActivity activity : sActivityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }

    /**
     * 应用程序退出
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
