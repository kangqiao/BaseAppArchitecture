package com.jzsec.broker.ui.my;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.ui.event.StartBrotherEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import lib.homhomlib.design.SlidingLayout;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class MyFragment extends BaseLazyFragment {
    private static final String TAG = "MyFragment";

    @BindView(R.id.slidingLayout)
    SlidingLayout mSlidingLayout;

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
    }
}
