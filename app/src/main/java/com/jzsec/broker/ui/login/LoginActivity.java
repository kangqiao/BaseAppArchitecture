package com.jzsec.broker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.mvp.BaseMVPActivity;
import com.jzsec.broker.ui.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhaopan on 16/7/27.
 */
public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginPresenter.View{
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tl_name)
    TextInputLayout tlName;
    @BindView(R.id.tl_pass)
    TextInputLayout tlPass;
    @BindView(R.id.tv_sign)
    TextView tv_sign;
    @BindView(R.id.tv_title)
    TextView tv_title;
    boolean isLogin = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fab.setOnClickListener(v -> {
            String name = tlName.getEditText().getText().toString();
            String passwd = tlPass.getEditText().getText().toString();
            String msg = TextUtils.isEmpty(name)? "用户名不能为空": TextUtils.isEmpty(passwd)? "密码不能为空": "";
            if(!TextUtils.isEmpty(msg)){
                showMsg(msg);
            }
            else if(isLogin){
                mPresenter.login(name, passwd);
            }
            else {
                mPresenter.sign(name, passwd);
            }
        });

        tv_sign.setOnClickListener(v -> swich());
    }

    private void swich() {
        if (isLogin) {
            isLogin = false;
            tv_title.setText("注册");
            tv_sign.setText("去登录");
        } else {
            isLogin = true;
            tv_title.setText("登录");
            tv_sign.setText("去注册");
        }
    }

    @OnClick(R.id.tv_go_login)
    public void clickGoLogin(){
        loginSuccess();
    }

    /*
    @Override
    public void initPresenter() {
        mPresenter.setView(this, mModel);
    }*/

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void signSuccess() {
        swich();
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }
}
