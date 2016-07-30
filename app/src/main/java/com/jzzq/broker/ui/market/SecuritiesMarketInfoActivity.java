package com.jzzq.broker.ui.market;

import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.OrientationEventListener;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseActivity;
import com.jzzq.broker.utils.Zlog;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by zhaopan on 16/7/29.
 * e-mail: kangqiao610@gmail.com
 */
public class SecuritiesMarketInfoActivity extends BaseActivity {
    private static final String TAG = "SecuritiesMarketInfoActivity";
    private static final boolean DEBUG = true;

    public static void open(Context context) {
        Intent intent = new Intent(context, SecuritiesMarketInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_securities_market_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, MainMarketInfoFragment.newInstance());
        }
        //RootActivity不需要Back功能.
        setSwipeBackEnable(false);

        registerOrientationEventListener();
    }

    int mScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_90;
    private void registerOrientationEventListener() {
        OrientationEventListener mScreenOrientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int i) {
                // i的范围是0～359
                // 屏幕左边在顶部的时候 i = 90;
                // 屏幕顶部在底部的时候 i = 180;
                // 屏幕右边在底部的时候 i = 270;
                // 正常情况默认i = 0;

                if(45 <= i && i < 135) { //右边在下
                    if(mScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_180) {
                        mScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_180;
                        Zlog.e(TAG, "屏幕右边在底部");
                    }
                } else if(135 <= i && i < 225) {
                    if(mScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_270) {
                        mScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_270;
                        Zlog.e(TAG, "屏幕顶部在底部");
                    }
                } else if(225 <= i && i < 315) {
                    if(mScreenExifOrientation != ExifInterface.ORIENTATION_NORMAL) {
                        mScreenExifOrientation = ExifInterface.ORIENTATION_NORMAL;
                        Zlog.e(TAG, "屏幕左边在底部");
                    }
                } else {
                    if(mScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_90) {
                        mScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_90;
                        Zlog.e(TAG, "屏幕正常情况");
                    }
                }
            }
        };

        mScreenOrientationEventListener.enable();
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
