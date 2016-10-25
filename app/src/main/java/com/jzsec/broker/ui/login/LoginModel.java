package com.jzsec.broker.ui.login;

import com.jzsec.broker.data.CreatedResult;
import com.jzsec.broker.data.entity.LoginInfo;

import rx.Observable;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */

public class LoginModel implements LoginPresenter.Model{
    @Override
    public Observable<LoginInfo> login(String name, String pass) {
        return null;
    }

    @Override
    public Observable<CreatedResult> sign(String name, String pass) {
        return null;
    }
}
