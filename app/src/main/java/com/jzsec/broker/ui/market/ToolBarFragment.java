package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.ui.my.CustomKeyBoardFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/8/25.
 * e-mail: kangqiao610@gmail.com
 */
public class ToolBarFragment extends BaseFragment {

    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_sell)
    TextView tvSell;

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
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_buy, R.id.tv_sell})
    public void clickOpt(View view){
        switch (view.getId()){
            case R.id.tv_buy:
                $(R.id.fl_opt_container).setVisibility(View.VISIBLE);
                loadRootFragment(R.id.fl_opt_container, CustomKeyBoardFragment.newInstance());
                break;
            case R.id.tv_sell:

                break;
        }
    }

    @Subscribe
    public void onEvent(CloseEvent event){
        $(R.id.fl_opt_container).setVisibility(View.GONE);
        $(R.id.ll_opt_container).setVisibility(View.VISIBLE);
        pop();
    }

    public static class CloseEvent {
    }
}
