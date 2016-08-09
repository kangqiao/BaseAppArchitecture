package com.jzsec.broker.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jzsec.broker.R;
import com.jzsec.broker.AppManager;
import com.jzsec.broker.utils.SpUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by zhaopan on 16/7/19
 * e-mail: kangqiao610@gmail.com
 */
public abstract class BaseActivity extends SwipeBackActivity {

    protected boolean isNight;
    protected Unbinder mUnBinder;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNight = SpUtil.isNight();
        setTheme(isNight ? R.style.AppThemeNight : R.style.AppThemeDay);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //?不需要再加setSupportActionBar(toolbar);
        setContentView(getLayoutId());
        //这句必须在setContentView()调用之后,否则抛 android.util.SuperNotCalledException, 莫名其妙啊?
        AppManager.getInstance().addActivity(this);
        //绑定View控件.
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        //设置此Activity默认接受SwipeBack操作.
        setSwipeBackEnable(true);
        //初始化操作.
        initTitle(savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
        if (isNight != SpUtil.isNight()) reload();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Deprecated
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 设置是否启用SwipeBack操作.
     * @param isEnable
     */
    public void setSwipeBackEnable(boolean isEnable) {
        getSwipeBackLayout().setEnableGesture(isEnable);
    }

    protected abstract int getLayoutId();

    public void initTitle(Bundle savedInstanceState) {}

    protected abstract void initView(Bundle savedInstanceState);

}

/*************************************************************/
/**********************废弃代码********************************/
/*************************************************************/
    /* //已经使用SwipeBackActivity来实现.
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == R.layout.act_main || layoutResID == R.layout.act_main2 || layoutResID == R.layout.act_splash) {
            super.setContentView(layoutResID);
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.bg_color));
            swipeBackLayout.addView(view);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }*/