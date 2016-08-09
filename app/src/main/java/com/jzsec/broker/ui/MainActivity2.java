package com.jzsec.broker.ui;

import android.os.Bundle;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseActivity;
import com.jzsec.broker.ui.contacts.ContactsFragment;
import com.jzsec.broker.ui.event.TabSelectedEvent;
import com.jzsec.broker.ui.home.HomeFragment;
import com.jzsec.broker.ui.message.MessageFragment;
import com.jzsec.broker.ui.my.MyFragment;
import com.jzsec.broker.view.BottomBar;
import com.jzsec.broker.view.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 */
@Deprecated
public class MainActivity2 extends BaseActivity {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    public int getLayoutId() {
        return R.layout.act_main2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = ContactsFragment.newInstance();
            mFragments[THIRD] = MessageFragment.newInstance();
            mFragments[FOURTH] = MyFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(ContactsFragment.class);
            mFragments[THIRD] = findFragment(MessageFragment.class);
            mFragments[FOURTH] = findFragment(MyFragment.class);
        }

        mBottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_bar_home, "首页"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_bar_contacter, "联系人"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_bar_message, "消息"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_bar_my, "我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof HomeFragment) {
                        currentFragment.popToChild(HomeFragment.class, false);
                    } else if (currentFragment instanceof MessageFragment) {
                        currentFragment.popToChild(MessageFragment.class, false);
                    } else if (currentFragment instanceof MessageFragment) {
                        currentFragment.popToChild(MessageFragment.class, false);
                    } else if (currentFragment instanceof MyFragment) {
                        currentFragment.popToChild(MyFragment.class, false);
                    }
                    return;
                }

                // 这里推荐使用EventBus来实现 -> 解耦  //后续切换成RxBus
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    //@Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    /**
     * 这里暂没实现,忽略
     */
//    @Subscribe
//    public void onHiddenBottombarEvent(boolean hidden) {
//        if (hidden) {
//            mBottomBar.hide();
//        } else {
//            mBottomBar.show();
//        }
//    }
}
