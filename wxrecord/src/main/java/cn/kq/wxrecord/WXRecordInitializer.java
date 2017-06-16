package cn.kq.wxrecord;

import android.app.Application;

import com.yixia.camera.VCamera;

import java.io.File;

/**
 * Created by zhaopan on 2017/4/11.
 * e-mail: kangqiao610@gmail.com
 */

public class WXRecordInitializer {
    public static String VIDEO_PATH =  "/sdcard/WXRecord";

    public static void onCreate(Application app) {

        VIDEO_PATH += String.valueOf(System.currentTimeMillis());
        File file = new File(VIDEO_PATH);
        if(!file.exists()) file.mkdirs();

        //设置视频缓存路径
        VCamera.setVideoCachePath(VIDEO_PATH);

        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);

        // 初始化拍摄SDK，必须
        VCamera.initialize(app);
    }
}
