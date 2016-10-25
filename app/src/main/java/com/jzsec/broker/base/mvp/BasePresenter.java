package com.jzsec.broker.base.mvp;

import com.lzy.okgo.OkGo;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */
public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    public M mModel;
    public V mView;

    public BasePresenter() {
        //mModel = TUtil.getT(this, 0);
    }

    public void setView(V view) {
        mView = view;
        setModel();
        onStart();
    }

    protected void setModel() {
        mModel = TUtil.getT(this, 0);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
        OkGo.getInstance().cancelTag(this);
    }
}
