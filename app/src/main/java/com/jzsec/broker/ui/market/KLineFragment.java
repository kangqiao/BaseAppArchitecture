package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/30.
 * e-mail: kangqiao610@gmail.com
 */
public class KLineFragment extends BaseLazyFragment
        implements SelectComplexRightPopupWin.SelectComplexRightListener
        , SelectMinuteKlinePopupWin.SelectMinuteKlineListener {

    SupportFragment dayKChartFragment;
    SupportFragment weekKChartFragment;
    SupportFragment monthKChartFragment;
    SupportFragment minutesKChartFragment;
    SupportFragment mCurrentChartFragment;

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
    @BindView(R.id.tv_minute_line)
    TextView tvMinuteLine;
    @BindView(R.id.tv_complex_right)
    TextView tvComplexRight;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    SelectMinuteKlinePopupWin selectKlineMinutePopWin;
    SelectComplexRightPopupWin selectComplexRightPopWin;
    Bundle mBundle;

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
        View root = inflater.inflate(R.layout.fragment_kline_market_info, container, false);
        mBundle = getArguments();
        EventBus.getDefault().register(this);
        return root;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        // TODO: 16/8/23 对ButterKnife.bind的View 进行基础配置
        if (savedInstanceState == null) {
            dayKChartFragment = DayKChartFragment.newInstance(mBundle);
            weekKChartFragment = DayKChartFragment.newInstance(mBundle);
            monthKChartFragment = DayKChartFragment.newInstance(mBundle);
            minutesKChartFragment = MinutesKChartFragment.newInstance(mBundle);

            loadMultipleRootFragment(R.id.fl_container, 0,
                    dayKChartFragment,
                    weekKChartFragment,
                    monthKChartFragment,
                    minutesKChartFragment);
            setCurrentChartFragment(dayKChartFragment);
        } else {
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            dayKChartFragment = findChildFragment(DayKChartFragment.class);
            weekKChartFragment = findChildFragment(DayKChartFragment.class);
            monthKChartFragment = findChildFragment(DayKChartFragment.class);
            minutesKChartFragment = findChildFragment(MinutesKChartFragment.class);
            if (!mBundle.isEmpty()) {
                MarketHelper.transferMarketParam(dayKChartFragment.getArguments(), mBundle);
                MarketHelper.transferMarketParam(weekKChartFragment.getArguments(), mBundle);
                MarketHelper.transferMarketParam(monthKChartFragment.getArguments(), mBundle);
                MarketHelper.transferMarketParam(minutesKChartFragment.getArguments(), mBundle);
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbDayLine.setTextColor(getResources().getColor(R.color.text_color_gray_3));
                rbWeekLine.setTextColor(getResources().getColor(R.color.text_color_gray_3));
                rbMonthLine.setTextColor(getResources().getColor(R.color.text_color_gray_3));
                rbMinuteLine.setTextColor(getResources().getColor(R.color.text_color_gray_3));
                switch (checkedId) {
                    case R.id.rb_day_line:
                        switchFragment(dayKChartFragment);
                        rbDayLine.setTextColor(getResources().getColor(R.color.text_color_blue));
                        break;
                    case R.id.rb_week_line:
                        switchFragment(weekKChartFragment);
                        rbWeekLine.setTextColor(getResources().getColor(R.color.text_color_blue));
                        break;
                    case R.id.rb_month_line:
                        switchFragment(monthKChartFragment);
                        rbMonthLine.setTextColor(getResources().getColor(R.color.text_color_blue));
                        break;
                    case R.id.rb_minute_line:
                        switchFragment(minutesKChartFragment);
                        rbMinuteLine.setTextColor(getResources().getColor(R.color.text_color_blue));
                        break;
                }

                getSelectKlineMinutePopWin().dismiss();
            }
        });
    }

    /**
     * 切换Fragment
     * @param fragment
     */
    private void switchFragment(SupportFragment fragment) {
        showHideFragment(fragment, mCurrentChartFragment);
        setCurrentChartFragment(fragment);
    }

    /**
     * 设置当前切换显示的Fragment, 如果是Day, Week, Month, 则显示ComplexRight按钮.
     *
     * @param fragment
     */
    private void setCurrentChartFragment(SupportFragment fragment) {
        mCurrentChartFragment = fragment;
        if (fragment instanceof ComplexRightChangeListener) {
            changeComplexRight(((ComplexRightChangeListener) fragment).getComplexRightType());
            tvComplexRight.setVisibility(View.VISIBLE);
        } else {
            tvComplexRight.setVisibility(View.GONE);
        }
    }

    //控制PopWindow的显示.
    @OnClick(R.id.tv_minute_line)
    public void clickMinuteLineRadioBtn(View view) {
        if (null != getSelectKlineMinutePopWin()) {
            if (!getSelectKlineMinutePopWin().isShowing()) {
                getSelectKlineMinutePopWin().showPopupWindowUp(rbMinuteLine);
            } else {
                getSelectKlineMinutePopWin().dismiss();
            }
        }
    }

    @Override
    public void changeMinuteKline(int type) {
        if (null == minutesKChartFragment) return;

        MinutesKChartFragment minutesKLineFragment = (MinutesKChartFragment) minutesKChartFragment;
        /*if (minutesKLineFragment.getKtype() != type) {
            minutesKLineFragment.setKtype(type);
            //将数据表示设置为false 参考StockChartFragment的selectMin方法设计.
            //if (getSelectKlineMinutePopWin().getLastSelectType() != type) {
            //将fragement标识置成-1
            minutesKLineFragment.setHaveShow(true);
            //}
            minutesKLineFragment.setHaveData(false);
            //如果切换了重新刷数据.
            minutesKLineFragment.onRefresh();
        }*/
        //因为分钟RadioButton被TextView遮住, 弹框后选分钟时, 要在这里手动切换到相应选择的分钟线.
        radioGroup.check(rbMinuteLine.getId());

        rbMinuteLine.setText(SelectMinuteKlinePopupWin.getMinuteKlineText(type));
        getSelectKlineMinutePopWin().refreshView(type);
    }

    private SelectMinuteKlinePopupWin getSelectKlineMinutePopWin() {
        if (null == selectKlineMinutePopWin) {
            selectKlineMinutePopWin = new SelectMinuteKlinePopupWin(_mActivity, this);
            selectKlineMinutePopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    rbMinuteLine.setText(SelectMinuteKlinePopupWin.getMinuteKlineText(selectKlineMinutePopWin.getLastSelectType()));
                }
            });
        }
        return selectKlineMinutePopWin;
    }

    @Override
    public void changeComplexRight(int type) {
        if (null == mCurrentChartFragment) return;
        if (mCurrentChartFragment instanceof ComplexRightChangeListener) {
            ComplexRightChangeListener fragment = (ComplexRightChangeListener) mCurrentChartFragment;
            if (ComplexRightChangeListener.TYPE_COMPLEX_RiGHT_NONE != type && fragment.getComplexRightType() != type) {
                fragment.changeComplexRight(type);
            }
            tvComplexRight.setText(SelectComplexRightPopupWin.getComplexRightText(type));
            getSelectComplexRightPopWin().refreshView(type);
        }
    }

    @OnClick(R.id.tv_complex_right)
    public void clickComplexRight(View v) {
        if (null != getSelectComplexRightPopWin()) {
            if (!getSelectComplexRightPopWin().isShowing()) {
                getSelectComplexRightPopWin().showPopupWindowUp(tvComplexRight);
            } else {
                getSelectComplexRightPopWin().dismiss();
            }
        }
    }

    private SelectComplexRightPopupWin getSelectComplexRightPopWin() {
        if (null == selectComplexRightPopWin) {
            selectComplexRightPopWin = new SelectComplexRightPopupWin(_mActivity, this);
        }
        return selectComplexRightPopWin;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        // TODO: 16/8/23 加载数据到View上.
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onEvent(MarketTabSelectedEvent event) {
        //if (event.position != MainMarketInfoFragment.SECOND) return;
        if (event.fragment != this) return;
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
