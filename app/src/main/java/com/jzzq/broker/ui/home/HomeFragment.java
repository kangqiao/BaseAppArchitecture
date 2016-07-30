package com.jzzq.broker.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.jzzq.broker.GlobalConfig;
import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseLazyFragment;
import com.jzzq.broker.ui.event.TabSelectedEvent;
import com.jzzq.broker.ui.main.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class HomeFragment extends BaseLazyFragment {

    @BindView(R.id.webview)
    WebView webview;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        webview.loadUrl(GlobalConfig.DEFAULT_HOME_PAGE);
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;

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
        EventBus.getDefault().unregister(this);
    }

}
