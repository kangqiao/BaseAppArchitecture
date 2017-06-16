package com.jzsec.broker.ui.login;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jzsec.broker.base.mvp.BasePresenter;
import com.jzsec.broker.base.mvp.IModel;
import com.jzsec.broker.base.mvp.IView;
import com.jzsec.broker.data.CreatedResult;
import com.jzsec.broker.data.entity.CommonParam;
import com.jzsec.broker.data.entity.LoginInfo;
import com.jzsec.broker.net.URLs;
import com.jzsec.broker.okgo.DataResponse;
import com.jzsec.broker.okgo.callback.DialogCallback;
import com.jzsec.broker.utils.Arith;
import com.jzsec.broker.utils.SpUtil;
import com.jzsec.broker.utils.Zlog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import rx.Observable;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.Model, LoginActivity> {
    private static final String TAG = "LoginPresenter";

    interface View extends IView {
        void loginSuccess();

        void signSuccess();

        void showMsg(String msg);
    }

    interface Model extends IModel {
        Observable<LoginInfo> login(String name, String pass);

        Observable<CreatedResult> sign(String name, String pass);
    }

    public void login(String name, String passwd) {
        OkGo.<DataResponse<LoginInfo>>get(URLs.LOGIN)
                .tag(this)
                .params("mobilephone", name)
                .params("password", passwd)
                .execute(new DialogCallback<DataResponse<LoginInfo>>(mView) {
                    @Override
                    public void onSuccess(Response<DataResponse<LoginInfo>> response) {
                        if (response.body().code == 0 && null != response.body().data) {
                            Zlog.e(TAG, response.body().getDataString());
                            SpUtil.setLoginInfo(response.body().parseJsonObj());
                            CommonParam.getInstance().update(response.body().parseJsonObj());
                            mView.loginSuccess();
                        } else {
                            mView.showMsg(response.body().msg);
                        }
                    }

                    @Override
                    public void onError(Response<DataResponse<LoginInfo>> response) {
                        mView.showMsg(response.body().msg);
                    }
                });
    }

    public void sign(String name, String pass) {

    }

    public void getInvitationCode() {
        OkGo.<DataResponse<JSONObject>>get(URLs.GET_INVITATION_CODE)
                .tag(this)
                .execute(new DialogCallback<DataResponse<JSONObject>>(mView) {

                    @Override
                    public void onSuccess(Response<DataResponse<JSONObject>> response) {

                    }
                });
    }


}
