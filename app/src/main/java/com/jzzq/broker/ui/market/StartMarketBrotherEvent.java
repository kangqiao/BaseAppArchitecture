package com.jzzq.broker.ui.market;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/8/1.
 * e-mail: kangqiao610@gmail.com
 */
public class StartMarketBrotherEvent {
    public SupportFragment targetFragment;

    public StartMarketBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
