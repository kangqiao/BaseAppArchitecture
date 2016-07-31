package com.jzzq.broker.ui.market;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseFragment;
import com.jzzq.broker.ui.contacts.ContactsFragment;
import com.jzzq.broker.ui.home.HomeFragment;
import com.jzzq.broker.ui.my.MyFragment;
import com.jzzq.broker.view.BottomBar;

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


    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        MainMarketInfoFragment fragment = new MainMarketInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_market_info, container, false);

        if (savedInstanceState == null) {
            mFragments[FIRST] = TimeKFragment.newInstance();
            mFragments[SECOND] = KFragment.newInstance();
            mFragments[THIRD] = InfoFragment.newInstance();
            mFragments[FOURTH] = ProfilesF10Fragment.newInstance();

            /*loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);*/
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(TimeKFragment.class);
            mFragments[SECOND] = findFragment(KFragment.class);
            mFragments[THIRD] = findFragment(InfoFragment.class);
            mFragments[FOURTH] = findFragment(ProfilesF10Fragment.class);
        }
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
                /*这是Activity的onCreate方法,有两处地方需要关注的
                1. setSupportActionBar() 方法,告诉AppCompatActivity哪一个是ActionBar(实际是Toolbar).
                   不然的话,做透明Status Bar(电池,手机信号那一区域)效果时候,ActionBar会位置不正确.

                2. Palette, 调色板的意思,也是Android Support Library提供的.用来抓取Bitmap的颜色.在此处的用处是,
                   当ActionBar被收折后,背景颜色能保持和Banner大图的色调一致,而Title文字的颜色保证和Banner大图的色调形成强对比.
                */
        final CollapsingToolbarLayout collapsingToolbar = $(R.id.collapsingToolbarLayout);
        collapsingToolbar.setTitle(getString(R.string.app_name));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_splash);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
                int defaultColor = getResources().getColor(R.color.colorPrimary);
                int defaultTitleColor = getResources().getColor(R.color.text_color_white);
                int bgColor = palette.getDarkVibrantColor(defaultColor);
                int titleColor = palette.getLightVibrantColor(defaultTitleColor);
                collapsingToolbar.setContentScrimColor(bgColor);
                collapsingToolbar.setCollapsedTitleTextColor(titleColor);
                collapsingToolbar.setExpandedTitleColor(titleColor);

                /*Palette这个类能提取以下突出的颜色：
                Vibrant(充满活力的)
                Vibrant dark (充满活力的黑)
                Vibrant light(充满活力的亮)
                Muted(柔和的)
                Muted dark (柔和的黑)
                Muted lighr(柔和的亮)
                */
                /*Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (swatch != null) {
                    // If we have a vibrant color
                    // update the title TextView
                    titleView.setBackgroundColor( vibrant.getRgb());
                    titleView.setTextColor(vibrant.getTitleTextColor());
                }*/
            }
        });
    }
}
