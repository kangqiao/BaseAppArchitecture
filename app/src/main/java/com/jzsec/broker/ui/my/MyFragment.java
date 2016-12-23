package com.jzsec.broker.ui.my;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.ui.WebViewActivity;
import com.jzsec.broker.ui.event.StartBrotherEvent;
import com.jzsec.broker.view.notification.ZPNotification;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import lib.homhomlib.design.SlidingLayout;
import rx.android.MainThreadSubscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class MyFragment extends BaseLazyFragment {
    private static final String TAG = "MyFragment";

    @BindView(R.id.slidingLayout)
    SlidingLayout mSlidingLayout;
    @BindView(R.id.tv_switch_btn)
    TextView tvSwitchBtn;
    @BindView(R.id.tv_custom_keyboard)
    TextView tvCustomKeyBoard;
    @BindView(R.id.tv_js_native)
    TextView tvJsNative;
    @BindView(R.id.tv_notification_open)
    TextView tvOpenNotification;
    @BindView(R.id.tv_notification_close)
    TextView tvCloseNotification;

    ZPNotification mZPNotification;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        mSlidingLayout.setSlidingListener(new SlidingLayout.SlidingListener() {
            @Override
            public void onSlidingOffset(View view, float delta) {
                Log.i(TAG,"onSlidingOffset:" + delta);
            }

            @Override
            public void onSlidingStateChange(View view, int state) { //STATE_SLIDING, STATE_IDLE
                Log.i(TAG,"onSlidingStateChange:" + state);
            }

            @Override
            public void onSlidingChangePointer(View view, int pointerId) {

            }
        });

        mZPNotification = new ZPNotification.Builder().setContext(_mActivity)
                        .setTime(System.currentTimeMillis())
                        .setImgRes(R.mipmap.ic_notify)
                        .setTitle("你收到了一条消息")
                        .setContent("人丑就要多读书").build();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        $(R.id.tv_brokerage_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里我使用EventBus通知父MainFragment处理跳转(解耦),
                EventBus.getDefault().post(new StartBrotherEvent(AdjustBrokerageListFragment.newInstance()));
            }
        });

        RxView.clicks(tvSwitchBtn).debounce(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                EventBus.getDefault().post(new StartBrotherEvent(SwitchBtnTestFragment.newInstance()));
            }
        });

        RxView.clicks(tvCustomKeyBoard).debounce(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                EventBus.getDefault().post(new StartBrotherEvent(CustomKeyBoardFragment.newInstance()));
            }
        });

        RxView.clicks(tvJsNative).debounce(300, TimeUnit.MICROSECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getContext(), WebViewActivity.class));
            }
        });

        RxView.clicks(tvOpenNotification).debounce(300, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mZPNotification.show();
            }
        });

        RxView.clicks(tvCloseNotification).debounce(300, TimeUnit.MICROSECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mZPNotification.dismiss();
            }
        });
    }
}
