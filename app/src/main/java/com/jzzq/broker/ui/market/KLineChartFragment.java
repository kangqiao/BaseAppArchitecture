package com.jzzq.broker.ui.market;

import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.App;
import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseFragment;
import com.jzzq.broker.utils.Zlog;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/8/1.
 * e-mail: kangqiao610@gmail.com
 */
public class KLineChartFragment extends BaseFragment {
    private static final String TAG = "KLineChartFragment";
    private static final boolean DEBUG = true;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        KLineChartFragment fragment = new KLineChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kline_chart, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        $(R.id.tv_change_orientation).setOnClickListener((v) -> {
            EventBus.getDefault().post(new StartMarketBrotherEvent(LandSpaceKLineChartFragment.newInstance()));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //TODO
    }

}
