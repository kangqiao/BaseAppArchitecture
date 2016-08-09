package com.jzsec.broker.ui;

import android.os.Bundle;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseActivity;
import com.jzsec.broker.ui.main.MainFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 */
public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
        //RootActivity不需要Back功能.
        setSwipeBackEnable(false);
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
