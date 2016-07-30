package com.jzzq.broker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 * 带切换动画的Fragment, 适用于Fragment间替换切换时使用
 *  注: 区别于BaseLazyFragment
 *      建议在onEnterAnimationEnd()中做数据的即时初始化操作.
 */
public class BaseFragment extends SwipeBackFragment {
    private static final String TAG = "Fragmentation";

    protected Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置此Fragment默认接受SwipeBack操作.
        //setSwipeBackEnable(false); //默认值true, 无需设置.
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        onBindView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null != mUnBinder) mUnBinder.unbind();
    }

    /**
     * ButterKnife已经绑定Fragment, 可以做些初始化View的操作, 例如Toolbar, 控件基础设置.
     * @param savedInstanceState
     */
    protected void onBindView(Bundle savedInstanceState) {}

    @Deprecated
    protected <T extends View> T $(int id) {
        if(null != getView()) {
            return (T) getView().findViewById(id);
        } else {
            return null;
        }
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        //initToolbarMenu(toolbar);
    }

    /*protected void initToolbarMenu(Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.hierarchy);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_hierarchy:
                        _mActivity.showFragmentStackHierarchyView();
                        _mActivity.logFragmentStackHierarchy(TAG);
                        break;
                }
                return true;
            }
        });
    }*/
}
