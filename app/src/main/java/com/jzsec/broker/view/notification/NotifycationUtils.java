package com.jzsec.broker.view.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

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
    private static final int TRADE_117_NOTIFY_ID = 1;

    public static void notifyWithBigText(Context context, String title, CharSequence content, String bigTitle, CharSequence bigContent, Intent intent) {

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(bigContent)
                .setBigContentTitle(bigTitle);
                //.setSummaryText("末尾只一行的文字内容"); //SummaryText没什么用 可以不设置

        PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setStyle(style)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(TRADE_117_NOTIFY_ID, notification);
    }

    public static void notify2(Context context, String title, CharSequence content, String sound, Intent intent){
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle(title);
        style.bigText(content);

        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(context.getApplicationInfo().icon)
                        .setContentTitle(title).setAutoCancel(true).setContentIntent(contentIntent)
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                        .setContentText(content)
                        .setStyle(style);
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        if (sound != null && sound.trim().length() > 0) {
            notification.sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + sound);
        }
        manager.notify(TRADE_117_NOTIFY_ID, notification);
    }

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
