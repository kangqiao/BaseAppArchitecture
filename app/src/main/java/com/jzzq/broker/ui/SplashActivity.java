package com.jzzq.broker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseActivity;
import com.jzzq.broker.utils.AnimationUtil;
import com.jzzq.broker.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.view)
    View view;

    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //全屏显示, 目前采用"@style/Theme.Splash"方式.
        //requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);//去掉标题栏
        //getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        StatusBarUtil.setTranslucentBackground(this);

        AlphaAnimation anim = new AlphaAnimation(0.8f, 0.1f);
        anim.setDuration(2000);
        view.startAnimation(anim);
        AnimationUtil.setAnimationListener(anim, () -> {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        });
    }
}
