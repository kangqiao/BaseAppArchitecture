package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/9/20.
 * e-mail: kangqiao610@gmail.com
 */
public class DayKChartFragment extends BaseLazyFragment implements ComplexRightChangeListener {

    Bundle mBundle;

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new DayKChartFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kline_chart, container, false);
        mBundle = getArguments();
        return root;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void changeComplexRight(int complexRightType) {

    }

    @Override
    public int getComplexRightType() {
        return 0;
    }

}
