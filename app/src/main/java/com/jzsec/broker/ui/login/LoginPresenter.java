package com.jzsec.broker.ui.login;

import com.jzsec.broker.base.mvp.BasePresenter;
import com.jzsec.broker.base.mvp.IModel;
import com.jzsec.broker.base.mvp.IView;
import com.jzsec.broker.data.CreatedResult;
import com.jzsec.broker.data.entity.CommonParam;
import com.jzsec.broker.data.entity.LoginInfo;
import com.jzsec.broker.net.JsonCallback;
import com.jzsec.broker.net.ResponseData;
import com.jzsec.broker.net.URLs;
import com.jzsec.broker.utils.SpUtil;
import com.jzsec.broker.utils.Zlog;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;
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
        void showMsg(String  msg);
    }

    interface Model extends IModel {
        Observable<LoginInfo> login(String name, String pass);
        Observable<CreatedResult> sign(String name, String pass);
    }

    public void login(String name, String passwd) {
        OkGo.get(URLs.LOGIN)
                .tag(this)
                .params("mobilephone", name)
                .params("password", passwd)
                .execute(new JsonCallback<ResponseData<LoginInfo>>() {
                    @Override
                    public void onSuccess(ResponseData<LoginInfo> loginInfoResponseData, Call call, Response response) {
                        if(loginInfoResponseData.code  == 0  && null != loginInfoResponseData.data) {
                            Zlog.e(TAG, loginInfoResponseData.data.toString());
                            SpUtil.setLoginInfo(loginInfoResponseData.data);
                            CommonParam.getInstance().update(loginInfoResponseData.data);
                            mView.loginSuccess();
                        } else {
                            mView.showMsg(loginInfoResponseData.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        mView.showMsg(e.getMessage());
                    }
                });
    }

    public void sign(String name, String pass) {

    }

    public void getInvitationCode(){
        OkGo.get(URLs.GET_INVITATION_CODE)
                .tag(this)
                .execute(new JsonCallback<ResponseData<JSONObject>>() {
                    @Override
                    public void onSuccess(ResponseData<JSONObject> jsonObjectResponseData, Call call, Response response) {
                        if(jsonObjectResponseData.code == 0){
                            //jsonObjectResponseData.getData()
                        }
                    }
                });
    }
}
