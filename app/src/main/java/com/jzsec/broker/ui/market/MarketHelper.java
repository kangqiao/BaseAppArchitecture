package com.jzsec.broker.ui.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by zhaopan on 16/8/24.
 * e-mail: kangqiao610@gmail.com
 */
public class MarketHelper {

    public static final String MARKET_NAME = "stockName";
    public static final String MARKET_CODE = "stockCode";
    public static final String MARKET_MARKET = "stockMarket";
    public static final String MARKET_TYPE = "stockType";

    public static final int TYPE_MINUTE_KCHART_5 = 9;
    public static final int TYPE_MINUTE_KCHART_15 = 10;
    public static final int TYPE_MINUTE_KCHART_30 = 11;
    public static final int TYPE_MINUTE_KCHART_60 = 12;
    public static final int TYPE_MINUTE_KCHART_NONE = -1000;

    public static final int TYPE_COMPLEX_RiGHT_NONE = ComplexRightChangeListener.TYPE_COMPLEX_RiGHT_NONE;
    public static final int TYPE_COMPLEX_EX_RiGHT = ComplexRightChangeListener.TYPE_COMPLEX_EX_RiGHT;
    public static final int TYPE_COMPLEX_BEFORE_RIGHT = ComplexRightChangeListener.TYPE_COMPLEX_BEFORE_RIGHT;
    public static final int TYPE_COMPLEX_AFTER_RiGHT = ComplexRightChangeListener.TYPE_COMPLEX_AFTER_RiGHT;

    public static void transferMarketParam(Fragment to, Fragment from){
        if( null != to && null != from){
           transferMarketParam(to.getArguments(), from.getArguments());
        }
    }

    public static void transferMarketParam(Bundle to, Bundle from){
        if( null != to && null != from){
            to.putString(MARKET_NAME, from.getString(MARKET_NAME));
            to.putString(MARKET_CODE, from.getString(MARKET_CODE));
            to.putString(MARKET_MARKET, from.getString(MARKET_MARKET));
            to.putString(MARKET_TYPE, from.getString(MARKET_TYPE));
        }
    }

    public static void setMarketParamTo(Intent intent, MarketParamBean bean){
        if(null != intent && null != bean) {
            intent.putExtra(MARKET_NAME, bean.getName());
            intent.putExtra(MARKET_CODE, bean.getCode());
            intent.putExtra(MARKET_MARKET, bean.getMarket());
            intent.putExtra(MARKET_TYPE, bean.getType() + "");
        }
    }

    public static MarketParamBean parseMarketParam(Bundle bundle){
        MarketParamBean marketParamBean = null;
        if(null != bundle){
            marketParamBean = new MarketParamBean();
            marketParamBean.setName(bundle.getString(MARKET_NAME));
            marketParamBean.setCode(bundle.getString(MARKET_CODE));
            marketParamBean.setMarket(bundle.getString(MARKET_MARKET));
            marketParamBean.setType(bundle.getInt(MARKET_TYPE));
        }
        return marketParamBean;
    }
}
