package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/8/25.
 * e-mail: kangqiao610@gmail.com
 */
public class ToolBarFragment extends BaseFragment {

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new ToolBarFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_toolbar, container, false);

        return view;
    }

}
