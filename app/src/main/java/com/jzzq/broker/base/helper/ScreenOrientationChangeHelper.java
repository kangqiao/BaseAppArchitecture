package com.jzzq.broker.base.helper;

import android.media.ExifInterface;
import android.view.OrientationEventListener;

import com.jzzq.broker.App;
import com.jzzq.broker.utils.Zlog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopan on 16/8/1.
 * e-mail: kangqiao610@gmail.com
 */
public class ScreenOrientationChangeHelper {
    private static final String TAG = "ScreenOrientationChangeHelper";

    private enum ScreenOrientation{
        NORMAL,
        LANDSPACE_LEFT,
        LANDSPACE_RIGHT,
        REVERSE;

        public boolean isLand(){
            return this == LANDSPACE_LEFT || this == LANDSPACE_RIGHT;
        }
    }

    public interface ScreenOrientationChangeListener {
        void onScreenOrientationChange(ScreenOrientation orientation);
    }

    /*private List<ScreenOrientationChangeListener> listeners = new ArrayList<>();

    private ScreenOrientationChangeHelper() {}

    private static class SingletonInstance {
        private static final ScreenOrientationChangeHelper INSTANCE = new ScreenOrientationChangeHelper();
    }

    public static ScreenOrientationChangeHelper getInstance() {
        return SingletonInstance.INSTANCE;
    }*/

    private static final int SCREEN_ORIENTATION_CHANGE_SENSITIVITY = 50; // 1-100
    private OrientationEventListener mScreenOrientationEventListener;

    public void enable(){
        mScreenOrientationEventListener = new OrientationEventListener(App.getAppContext()) {
            private int mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_90;

            @Override
            public void onOrientationChanged(int i) {
                // i的范围是0～359
                // 屏幕左边在顶部的时候 i = 90;
                // 屏幕顶部在底部的时候 i = 180;
                // 屏幕右边在底部的时候 i = 270;
                // 正常情况默认i = 0;

                float sensitivity = 45 * (SCREEN_ORIENTATION_CHANGE_SENSITIVITY / 100);

                if(45 + sensitivity <= i && i < 135 - sensitivity) { //右边在下
                    if(mCurScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_180) {
                        mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_180;
                        Zlog.e(TAG, "屏幕右边在底部");
                        notifyOrientationChange(ScreenOrientation.LANDSPACE_RIGHT);
                    }
                } else if(135 + sensitivity <= i && i < 225 - sensitivity) {
                    if(mCurScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_270) {
                        mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_270;
                        Zlog.e(TAG, "屏幕顶部在底部");
                        notifyOrientationChange(ScreenOrientation.REVERSE);
                    }
                } else if(225 + sensitivity <= i && i < 315 - sensitivity) {
                    if(mCurScreenExifOrientation != ExifInterface.ORIENTATION_NORMAL) {
                        mCurScreenExifOrientation = ExifInterface.ORIENTATION_NORMAL;
                        Zlog.e(TAG, "屏幕左边在底部");
                        notifyOrientationChange(ScreenOrientation.REVERSE);
                    }
                } else {
                    if(mCurScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_90) {
                        mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_90;
                        Zlog.e(TAG, "屏幕正常情况");
                        notifyOrientationChange(ScreenOrientation.NORMAL);
                    }
                }
            }
        };

        mScreenOrientationEventListener.enable();
    }

    private void notifyOrientationChange(ScreenOrientation orientation) {
        switch (orientation){
            case LANDSPACE_LEFT:
            case LANDSPACE_RIGHT:

                break;
            case NORMAL:
            case REVERSE:
            default:

                break;
        }
    }

    public void disable(){
        mScreenOrientationEventListener.disable();
    }


}
