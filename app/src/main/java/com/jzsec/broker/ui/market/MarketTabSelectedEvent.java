package com.jzsec.broker.ui.market;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/8/1.
 * e-mail: kangqiao610@gmail.com
 */
public class MarketTabSelectedEvent {
    public int position;
    public SupportFragment fragment;

    public MarketTabSelectedEvent(int position) {
        this.position = position;
    }

    public MarketTabSelectedEvent(SupportFragment fragment) {
        this.fragment = fragment;
    }
}
