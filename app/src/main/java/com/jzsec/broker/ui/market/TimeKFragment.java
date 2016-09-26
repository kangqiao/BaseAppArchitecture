package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/30.
 * e-mail: kangqiao610@gmail.com
 */
public class TimeKFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new TimeKFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_market_info, container, false);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, KLineChartFragment.newInstance());
        }
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

    @Override
    public void onRefresh() {
        //TODO 刷新你的列表.
        /*refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2500);*/
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onMarketTabSelectedEvent(MarketTabSelectedEvent event) {
        //if (event.position != MainMarketInfoFragment.FIRST) return;
        if (event.fragment != this) return;
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
