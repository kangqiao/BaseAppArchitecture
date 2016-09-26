package com.jzsec.broker.ui.market;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.utils.ZPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaopan on 16/9/3.
 * e-mail: kangqiao610@gmail.com
 */
public class SelectMinuteKlinePopupWin extends ZPopupWindow<Integer> {

    @BindView(R.id.tv_minute_60)
    TextView tvMinute60;
    @BindView(R.id.tv_minute_30)
    TextView tvMinute30;
    @BindView(R.id.tv_minute_15)
    TextView tvMinute15;
    @BindView(R.id.tv_minute_5)
    TextView tvMinute5;

    int lastSelectType = MarketHelper.TYPE_MINUTE_KCHART_NONE;
    SelectMinuteKlineListener listener;

    public interface SelectMinuteKlineListener {
        void changeMinuteKline(int type);
    }

    public SelectMinuteKlinePopupWin(Activity context, SelectMinuteKlineListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected View onCreatePopupView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.pop_minute_line, null, false);
    }

    @Override
    protected void initView() {
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        ButterKnife.bind(this, contentView);
    }

    @OnClick({R.id.tv_minute_60, R.id.tv_minute_30, R.id.tv_minute_15, R.id.tv_minute_5})
    public void clickKlineMinuteTab(View view) {
        int selectMinuteType = MarketHelper.TYPE_MINUTE_KCHART_NONE;
        switch (view.getId()) {
            case R.id.tv_minute_60:
                selectMinuteType = MarketHelper.TYPE_MINUTE_KCHART_60;
                break;
            case R.id.tv_minute_30:
                selectMinuteType = MarketHelper.TYPE_MINUTE_KCHART_30;
                break;
            case R.id.tv_minute_15:
                selectMinuteType = MarketHelper.TYPE_MINUTE_KCHART_15;
                break;
            case R.id.tv_minute_5:
                selectMinuteType = MarketHelper.TYPE_MINUTE_KCHART_5;
                break;
        }
        if (null != listener && MarketHelper.TYPE_MINUTE_KCHART_NONE != selectMinuteType){
            listener.changeMinuteKline(selectMinuteType);
            lastSelectType = selectMinuteType;
        }
        dismiss();
    }

    @Override
    public void refreshView(Integer val) {
        // TODO: 16/8/23 处理分钟线
    }

    public int getLastSelectType() {
        return lastSelectType;
    }

    public static String getMinuteKlineText(int type) {
        switch (type) {
            case MarketHelper.TYPE_MINUTE_KCHART_5:
                return "5分钟";
            case MarketHelper.TYPE_MINUTE_KCHART_15:
                return "15分钟";
            case MarketHelper.TYPE_MINUTE_KCHART_30:
                return "30分钟";
            case MarketHelper.TYPE_MINUTE_KCHART_60:
                return "60分钟";
            default:
                return "分钟";
        }
    }
}
