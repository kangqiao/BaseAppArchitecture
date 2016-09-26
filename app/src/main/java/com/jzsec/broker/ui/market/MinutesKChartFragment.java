package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jzsec.broker.base.BaseLazyFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/9/20.
 * e-mail: kangqiao610@gmail.com
 */
public class MinutesKChartFragment extends BaseLazyFragment {

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new MinutesKChartFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
    }


}