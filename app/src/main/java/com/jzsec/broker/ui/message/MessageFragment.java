package com.jzsec.broker.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.ui.market.SecuritiesMarketInfoActivity;
import com.jzsec.broker.ui.rxokgo.RxOkGoFragment;
import com.thefinestartist.finestwebview.FinestWebView;

import rx.functions.Action1;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class MessageFragment extends BaseLazyFragment {


    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        $(R.id.btn_open_market).setOnClickListener((v) -> {
            SecuritiesMarketInfoActivity.open(_mActivity);
        });

        $(R.id.btn_open_h5).setOnClickListener((v) -> {
            new FinestWebView.Builder(_mActivity).show("http://www.baidu.com");
        });

        _click(R.id.tv_RxJava_OkGO, (Void) -> {
            openMainFragment(RxOkGoFragment.newInstance());
        });
    }
}
