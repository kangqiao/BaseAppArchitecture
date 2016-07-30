package com.jzzq.broker.base;

import android.os.Bundle;

/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 */
public abstract class BaseMVPActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity {

    public T mPresenter;
    public E mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView && null != mPresenter) mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }
}
