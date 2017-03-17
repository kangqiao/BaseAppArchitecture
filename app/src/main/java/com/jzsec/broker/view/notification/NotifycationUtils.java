package com.jzsec.broker.view.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.jzsec.broker.R;

/**
 * Created by zhaopan on 2017/2/9.
 * e-mail: kangqiao610@gmail.com
 * Android 通知栏Notification的整合 全面学习 （一个DEMO让你完全了解它）
 * http://blog.csdn.net/vipzjyno1/article/details/25248021/
 * ANDROID中使用NOTIFICATION实现进度通知栏（NOTIFICATION示例三）
 * http://www.cnblogs.com/panhouye/p/6147332.html
 */

public class NotifycationUtils {
    //定义notification实用的ID
    private static final int NO_3 = 0x3;


    public void show3(Context context) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.app_logo);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NO_3, builder.build());
        builder.setProgress(100, 0, false);
        //下载以及安装线程模拟
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    builder.setProgress(100, i, false);
                    manager.notify(NO_3, builder.build());
                    //下载进度提示
                    builder.setContentText("下载" + i + "%");
                    try {
                        Thread.sleep(50);//演示休眠50毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //下载完成后更改标题以及提示信息
                builder.setContentTitle("开始安装");
                builder.setContentText("安装中...");
                //设置进度为不确定，用于模拟安装
                builder.setProgress(0, 0, true);
                manager.notify(NO_3, builder.build());
//                manager.cancel(NO_3);//设置关闭通知栏
            }
        }).start();
    }
}
