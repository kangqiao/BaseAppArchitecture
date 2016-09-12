package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.utils.ZPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/30.
 * e-mail: kangqiao610@gmail.com
 */
public class KLineFragment extends BaseLazyFragment {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];
    @BindView(R.id.rg_kline)
    RadioGroup radioGroup;
    @BindView(R.id.rb_day_line)
    RadioButton rbDayLine;
    @BindView(R.id.rb_week_line)
    RadioButton rbWeekLine;
    @BindView(R.id.rb_month_line)
    RadioButton rbMonthLine;
    @BindView(R.id.rb_minute_line)
    RadioButton rbMinuteLine;
    ZPopupWindow minutePopWin;

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new KLineFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kline_market_info, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        // TODO: 16/8/23 对ButterKnife.bind的View 进行基础配置
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_day_line:
                        //showHideFragment(mFragments[FIRST], null);
                        //EventBus.getDefault().post(new MarketTabSelectedEvent(/*tab.getPosition()*/0));
                        break;
                    case R.id.rb_week_line:

                        break;
                    case R.id.rb_month_line:

                        break;
                    case R.id.rb_minute_line:
                        break;
                }

                getMinutePopWin().dismiss();
            }
        });
        rbMinuteLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getMinutePopWin().isShowing()){
                    getMinutePopWin().dismiss();
                } else {
                    getMinutePopWin().showPopupWindowUp(rbMinuteLine);
                }
            }
        });
    }

    private View.OnClickListener minuteLineOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.tv_minute_60:
                case R.id.tv_minute_30:
                case R.id.tv_minute_15:
                case R.id.tv_minute_5:

                    break;
            }
            getMinutePopWin().dismiss();
        }
    };

    private ZPopupWindow getMinutePopWin() {
        if(null == minutePopWin){
            minutePopWin = new ZPopupWindow(_mActivity) {
                @Override
                protected View onCreatePopupView(LayoutInflater inflater) {
                    return inflater.inflate(R.layout.pop_minute_line, null, false);
                }

                @Override
                public void refreshView(Object o) {
                    // TODO: 16/8/23 处理分钟线
                    findView(R.id.tv_minute_60).setOnClickListener(minuteLineOnClickListener);
                    findView(R.id.tv_minute_30).setOnClickListener(minuteLineOnClickListener);
                    findView(R.id.tv_minute_15).setOnClickListener(minuteLineOnClickListener);
                    findView(R.id.tv_minute_5).setOnClickListener(minuteLineOnClickListener);
                }
            };
            minutePopWin.refreshView(null);
        }
        return minutePopWin;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        // TODO: 16/8/23 加载数据到View上.
    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onMarketTabSelectedEvent(MarketTabSelectedEvent event) {
        if (event.position != MainMarketInfoFragment.SECOND) return;

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
