package com.jzzq.broker.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseLazyFragment;
import com.thefinestartist.finestwebview.FinestWebView;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class MyFragment extends BaseLazyFragment {


    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        $(R.id.btn_open_h5).setOnClickListener((v) -> {
            new FinestWebView.Builder(_mActivity).show("http://www.baidu.com");
        });
    }
}
