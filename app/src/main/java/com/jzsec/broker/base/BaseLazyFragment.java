package com.jzsec.broker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jzsec.broker.R;

/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 * 懒加载Fragment, 适用于多个Fragment在同一界面中来回切换时, 超到切换时才去加载数据.
 *  注: 建议在initLazyView()中做数据的即时初始化操作.
 */
public abstract class BaseLazyFragment extends BaseFragment {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private boolean mInitialize = false;
    private Bundle mSavedInstanceState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        //懒加载Fragment时 大多适用于TabLayout中, 此是Swipe操作由TabLayout控制切换, 不需要Back操作.
        //setSwipeBackEnable(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            if (!isHidden()) {
                mInitialize = true;
                initLazyView(null);
            }
        } else {
            // isSupportHidden()仅在saveInstanceState!=null时有意义,是库帮助记录Fragment状态的方法
            if (!isSupportHidden()) {
                mInitialize = true;
                initLazyView(savedInstanceState);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!mInitialize && !hidden) {
            mInitialize = true;
            initLazyView(mSavedInstanceState);
        }
    }

    /**
     * 懒加载
     */
    protected abstract void initLazyView(@Nullable Bundle savedInstanceState);

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        /*if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
        }
        return true;*/
        return super.onBackPressedSupport();
    }
}
