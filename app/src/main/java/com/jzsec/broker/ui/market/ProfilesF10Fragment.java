package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/30.
 * e-mail: kangqiao610@gmail.com
 */
public class ProfilesF10Fragment extends BaseLazyFragment {

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new ProfilesF10Fragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profiles_f10
                , container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        //TODO 对ButterKnife.bind的View 进行基础配置
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        //TODO 加载数据到View上.
    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onMarketTabSelectedEvent(MarketTabSelectedEvent event) {
        if (event.position != MainMarketInfoFragment.SECOND) return;

        //TODO
        /*if (mInAtTop) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //TODO
        //if(null != recyclerView) recyclerView.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }
}
