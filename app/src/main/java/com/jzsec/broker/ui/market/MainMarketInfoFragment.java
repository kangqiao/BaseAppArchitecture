package com.jzsec.broker.ui.market;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.utils.Zlog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/29.
 * e-mail: kangqiao610@gmail.com
 */
public class MainMarketInfoFragment extends BaseFragment {
    private static final String TAG = "MainMarketInfoFragment";

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private SupportFragment panKouFragment;
    private SupportFragment toolbarFragment;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    Bundle mBundle;
    String[] mTabs = new String[]{"分时", "K线", "简况F10", "更多>"};

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new MainMarketInfoFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_market_info, container, false);
        mBundle = getArguments();

        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            mFragments[FIRST] = TimeKFragment.newInstance(mBundle);
            mFragments[SECOND] = KLineFragment.newInstance(mBundle);
            mFragments[THIRD] = ProfilesF10Fragment.newInstance(mBundle);
            mFragments[FOURTH] = MoreFragment.newInstance(mBundle);

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);

            panKouFragment = PanKouFragment.newInstance(mBundle);
            loadRootFragment(R.id.fl_pankou_container, panKouFragment);
            toolbarFragment = ToolBarFragment.newInstance(mBundle);
            loadRootFragment(R.id.fl_toolbar_container, toolbarFragment);
        } else {
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(TimeKFragment.class);
            mFragments[SECOND] = findFragment(KLineFragment.class);
            mFragments[THIRD] = findFragment(ProfilesF10Fragment.class);
            mFragments[FOURTH] = findFragment(MoreFragment.class);
            panKouFragment = findFragment(PanKouFragment.class);
        }

        if(!mBundle.isEmpty()) {
            MarketHelper.transferMarketParam(panKouFragment, this);
            //向子fragment传递参数.
            for (SupportFragment fragment : mFragments) {
                MarketHelper.transferMarketParam(fragment.getArguments(), mBundle);
            }

        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindView(Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText(mTabs[FIRST]));
        tabLayout.addTab(tabLayout.newTab().setText(mTabs[SECOND]));
        tabLayout.addTab(tabLayout.newTab().setText(mTabs[THIRD]));
        tabLayout.addTab(tabLayout.newTab().setText(mTabs[FOURTH]));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            private TabLayout.Tab preTab;

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showHideFragment(mFragments[tab.getPosition()], mFragments[preTab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                preTab = tab;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 这里推荐使用EventBus来实现 -> 解耦 //后续使用RxBus实现.
                // 在MessageFragment, HomeFragment...中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new MarketTabSelectedEvent(tab.getPosition()));
            }
        });

        // 启用监听屏幕方向改变, 若切换为横屏, 横屏打开K线图.
        /*enableScreenOrientationChangeFunction(new ScreenOrientationChangeListener() {
            @Override
            public void onChangeHorizontal(boolean isLeft) {
                //EventBus.getDefault().post(new StartMarketBrotherEvent(LandSpaceKLineChartFragment.newInstance()));
                int selectedPosition = tabLayout.getSelectedTabPosition();
                if (!isHidden() && (FIRST == selectedPosition || SECOND == selectedPosition)) {
                    startBrother(new StartMarketBrotherEvent(LandSpaceKLineChartFragment.newInstance()));
                }
            }

            @Override
            public void onChangeVertical(boolean isNormal) {
            }
        });*/

        //TODO

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //final int insetTop = mLastInsets != null ? mLastInsets.getSystemWindowInsetTop() : 0;
                final int expandRange = collapsingToolbarLayout.getHeight() - ViewCompat.getMinimumHeight(collapsingToolbarLayout)/* - insetTop*/;

                if(false) Zlog.e(TAG, "onOffsetChanged - appBarLayout.height="+appBarLayout.getHeight()
                        + ", collapsingToolbarLayout.height="+collapsingToolbarLayout.getHeight()
                        + ", expandRange="+expandRange
                        + ", ViewCompat.getMinimumHeight(collapsingToolbarLayout)="+ViewCompat.getMinimumHeight(collapsingToolbarLayout)
                        +", verticalOffset=" + verticalOffset);
            }
        });



        /*collapsingToolbarLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Zlog.e(TAG, "onScrollChange - View v.height="+v.getHeight()+", scrollX=" + scrollX + ", scrollY="+ scrollY+", oldScrollX="+oldScrollX+", oldScrollY="+oldScrollY);
            }
        });*/
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartMarketBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        disableScreenOrientationChangeFunction();
        super.onDestroyView();
    }
}
/**
 * 这是Activity的onCreate方法,有两处地方需要关注的
 * 1. setSupportActionBar() 方法,告诉AppCompatActivity哪一个是ActionBar(实际是Toolbar).
 * 不然的话,做透明Status Bar(电池,手机信号那一区域)效果时候,ActionBar会位置不正确.
 * <p>
 * 2. Palette, 调色板的意思,也是Android Support Library提供的.用来抓取Bitmap的颜色.在此处的用处是,
 * 当ActionBar被收折后,背景颜色能保持和Banner大图的色调一致,而Title文字的颜色保证和Banner大图的色调形成强对比.
 * <p>
 * final CollapsingToolbarLayout collapsingToolbar = $(R.id.collapsingToolbarLayout);
 * collapsingToolbar.setTitle(getString(R.string.app_name));
 * Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_splash);
 * Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
 *
 * @Override public void onGenerated(final Palette palette) {
 * int defaultColor = getResources().getColor(R.color.colorPrimary);
 * int defaultTitleColor = getResources().getColor(R.color.text_color_white);
 * int bgColor = palette.getDarkVibrantColor(defaultColor);
 * int titleColor = palette.getLightVibrantColor(defaultTitleColor);
 * collapsingToolbar.setContentScrimColor(bgColor);
 * collapsingToolbar.setCollapsedTitleTextColor(titleColor);
 * collapsingToolbar.setExpandedTitleColor(titleColor);
 * <p>
 * Palette这个类能提取以下突出的颜色：
 * Vibrant(充满活力的)
 * Vibrant dark (充满活力的黑)
 * Vibrant light(充满活力的亮)
 * Muted(柔和的)
 * Muted dark (柔和的黑)
 * Muted lighr(柔和的亮)
 * <p>
 * Palette.Swatch vibrant = palette.getVibrantSwatch();
 * if (swatch != null) {
 * // If we have a vibrant color
 * // update the title TextView
 * titleView.setBackgroundColor( vibrant.getRgb());
 * titleView.setTextColor(vibrant.getTitleTextColor());
 * }
 * }
 * });
 */