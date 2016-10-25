package com.jzsec.broker.base.mvp;

import android.os.Bundle;

import com.jzsec.broker.base.BaseActivity;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements IView {

    public P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = TUtil.getT(this, 0);
        if (this instanceof IView && null != mPresenter) mPresenter.setView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }
}
