package com.jzzq.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jzzq.broker.base.BaseLazyFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/30.
 * e-mail: kangqiao610@gmail.com
 */
public class TimeKFragment extends BaseLazyFragment {

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        TimeKFragment fragment = new TimeKFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }


}
