package com.jzzq.broker.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
}
