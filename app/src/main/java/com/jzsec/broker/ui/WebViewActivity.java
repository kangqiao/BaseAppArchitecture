package com.jzsec.broker.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseActivity;
import com.jzsec.broker.ui.login.LoginActivity;
import com.jzsec.broker.ui.market.SecuritiesMarketInfoActivity;

import butterknife.BindView;

/**
 * Created by zhaopan on 2016/11/14.
 * e-mail: kangqiao610@gmail.com
 */

public class WebViewActivity extends BaseActivity{

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.addJavascriptInterface(this, "nativeMethod");
        webView.loadUrl("file:///android_asset/www/test_js_native.html");
        /*webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                String scheme = Uri.parse(url).getScheme();//还需要判断host
                if (TextUtils.equals("broker", scheme)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });*/


    }

    public void toActivity(String activityName) {
        //此处应该定义常量对应，同时提供给web页面编写者
        if(TextUtils.equals(activityName, "a")){
            startActivity(new Intent(this, SecuritiesMarketInfoActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
