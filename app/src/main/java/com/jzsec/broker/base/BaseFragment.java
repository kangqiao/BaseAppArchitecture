package com.jzsec.broker.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.OrientationEventListener;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.jzsec.broker.R;
import com.jzsec.broker.ui.event.StartBrotherEvent;
import com.jzsec.broker.ui.my.SwitchBtnTestFragment;
import com.jzsec.broker.ui.test.TestLayoutFragment;
import com.jzsec.broker.utils.Zlog;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 * 带切换动画的Fragment, 适用于Fragment间替换切换时使用
 * 注: 区别于BaseLazyFragment
 * 建议在onEnterAnimationEnd()中做数据的即时初始化操作.
 */
public class BaseFragment extends SwipeBackFragment implements MainHandler.MessageHandler, LifecycleRegistryOwner {
    private static final String TAG = "BaseFragment";
    private static final boolean DEBUG = true;

    protected Unbinder mUnBinder;

    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置此Fragment默认接受SwipeBack操作.
        //setSwipeBackEnable(false); //默认值true, 无需设置.
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        onBindView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnBinder) mUnBinder.unbind();
    }

    /**
     * ButterKnife已经绑定Fragment, 可以做些初始化View的操作, 例如Toolbar, 控件基础设置.
     *
     * @param savedInstanceState
     */
    protected void onBindView(Bundle savedInstanceState) {
    }

    @Deprecated
    protected <T extends View> T $(int id) {
        return (T) getView().findViewById(id);
    }

    protected BaseFragment _click(@IdRes int id, final Action1<? super Void> onNext){
        return _click($(id), onNext);
    }

    public BaseFragment _click(View view, final Action1<? super Void> onNext){
        RxView.clicks(view)
                .debounce(300, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext);
        return this;
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        //initToolbarMenu(toolbar);
    }

    protected void debounceClick(View view, int timeout, Action1<Void> action1){
        RxView.clicks(view).debounce(timeout, TimeUnit.MILLISECONDS).subscribe(action1);
    }

    protected void debounceClick(View view, Action1<Void> action1){
        debounceClick(view, 300, action1);
    }


    protected static void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }

    protected static void openMainFragment(SupportFragment fragment) {
        postEvent(new StartBrotherEvent(fragment));
    }

    /*protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.hierarchy);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch_btn (item.getItemId()) {
                    case R.id.action_hierarchy:
                        _mActivity.showFragmentStackHierarchyView();
                        _mActivity.logFragmentStackHierarchy(TAG);
                        break;
                }
                return true;
            }
        });
    }*/

    private Handler mHandler;
    public Handler getMainHandler() {
        if (null == mHandler) {
            mHandler = new MainHandler<BaseFragment>(this);
        }
        return mHandler;
    }

    @Override
    public void handleMainMessage(Message msg) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////监听屏幕方向改变功能实现/////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    private float SCREEN_ORIENTATION_CHANGE_SENSITIVITY = 0.05f; // 0.01~0.99 数值越小敏感度越低.
    private int DELAY_UPDATE_ORIENTATION_CHANGE_EVENT = 1000; // 延时上报屏幕方向改变时间, 必须保持手机不动且在这个时间范围内才能发生
    protected OrientationEventListener mOrientationEventListener;
    protected ScreenOrientationChangeListener mScreenOrientationChangeListener;

    public interface ScreenOrientationChangeListener {
        void onChangeHorizontal(boolean isLeft);

        void onChangeVertical(boolean isNormal);
    }

    protected void enableScreenOrientationChangeFunction(ScreenOrientationChangeListener listener) {
        mScreenOrientationChangeListener = listener;
        registerOrientationEventListener();
    }

    protected void setScreenOrientationChangeSensitivity(float sensitivity) {
        SCREEN_ORIENTATION_CHANGE_SENSITIVITY = sensitivity;
    }

    protected void setScreenOrientationChangeSensitivity(int delayTimeMillis) {
        DELAY_UPDATE_ORIENTATION_CHANGE_EVENT = delayTimeMillis;
    }

    protected void disableScreenOrientationChangeFunction() {
        unRegisterOrientationEventListener();
        mScreenOrientationChangeListener = null;
    }

    private void registerOrientationEventListener() {
        if (null == mOrientationEventListener) {
            mOrientationEventListener = new OrientationEventListener(_mActivity) {
                int mCurScreenExifOrientation = ExifInterface.ORIENTATION_NORMAL;
                long beginChangeTimeMillis = System.currentTimeMillis();

                @Override
                public void onOrientationChanged(int i) {
                    // i的范围是0～359
                    // 手机平放时 i = -1;
                    // 手机正常竖立时 i = 0 '度;
                    // 手机顺时针转动 右侧与地面水平时 i = 90 '度;
                    // 手机翻转过来 顶部与地面水平时 i = 180 '度;
                    // 手机逆时针转动 左侧与地面水平时 i = 270 '度;

                    if (-1 == i || isHidden() || null == mScreenOrientationChangeListener) {
                        if (false) Zlog.e(TAG, "isHidden()=" + isHidden() + ", i=" + i);
                        return; //如果当前fragment处于隐藏状态, 不处理.
                    }

                    float sensitivity = 45 * (1 - SCREEN_ORIENTATION_CHANGE_SENSITIVITY);

                    if (45 + sensitivity <= i && i < 135 - sensitivity) { //右侧与地面水平
                        if (mCurScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_90) {
                            long curTimeMillis = System.currentTimeMillis();
                            if (curTimeMillis - beginChangeTimeMillis > DELAY_UPDATE_ORIENTATION_CHANGE_EVENT) {
                                beginChangeTimeMillis = curTimeMillis;
                                mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_90;
                                if(DEBUG) Zlog.e(TAG, "右侧与地面水平");
                                mScreenOrientationChangeListener.onChangeHorizontal(false);
                            }
                        }
                    } else if (135 + sensitivity <= i && i < 225 - sensitivity) { //倒立
                        if (mCurScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_180) {
                            long curTimeMillis = System.currentTimeMillis();
                            if (curTimeMillis - beginChangeTimeMillis > DELAY_UPDATE_ORIENTATION_CHANGE_EVENT) {
                                beginChangeTimeMillis = curTimeMillis;
                                mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_180;
                                if(DEBUG) Zlog.e(TAG, "倒立");
                                mScreenOrientationChangeListener.onChangeVertical(false);
                            }
                        }
                    } else if (225 + sensitivity <= i && i < 315 - sensitivity) { //左侧与地面水平
                        if (mCurScreenExifOrientation != ExifInterface.ORIENTATION_ROTATE_270) {
                            long curTimeMillis = System.currentTimeMillis();
                            if (curTimeMillis - beginChangeTimeMillis > DELAY_UPDATE_ORIENTATION_CHANGE_EVENT) {
                                beginChangeTimeMillis = curTimeMillis;
                                mCurScreenExifOrientation = ExifInterface.ORIENTATION_ROTATE_270;
                                if(DEBUG) Zlog.e(TAG, "左侧与地面水平");
                                mScreenOrientationChangeListener.onChangeHorizontal(true);
                            }
                        }
                    } else if (315 + sensitivity <= i && i < 45 - sensitivity) { //正常竖立
                        if (mCurScreenExifOrientation != ExifInterface.ORIENTATION_NORMAL) {
                            long curTimeMillis = System.currentTimeMillis();
                            if (curTimeMillis - beginChangeTimeMillis > DELAY_UPDATE_ORIENTATION_CHANGE_EVENT) {
                                beginChangeTimeMillis = curTimeMillis;
                                mCurScreenExifOrientation = ExifInterface.ORIENTATION_NORMAL;
                                if(DEBUG) Zlog.e(TAG, "正常竖立");
                                mScreenOrientationChangeListener.onChangeVertical(true);
                            }
                        }
                    }
                }
            };
        }
        mOrientationEventListener.enable();
    }

    private void unRegisterOrientationEventListener() {
        if (null != mOrientationEventListener) mOrientationEventListener.disable();
    }

}
