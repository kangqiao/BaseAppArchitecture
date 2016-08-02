package com.jzzq.broker.ui.market;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/8/1.
 * e-mail: kangqiao610@gmail.com
 */
public class LandSpaceKLineChartFragment extends BaseFragment {


    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        LandSpaceKLineChartFragment fragment = new LandSpaceKLineChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landspace_kline_chart, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        $(R.id.img_close).setOnClickListener((v) -> {
            _mActivity.onBackPressed();
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return super.onBackPressedSupport();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
