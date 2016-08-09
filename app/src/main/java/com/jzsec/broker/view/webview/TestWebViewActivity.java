package com.jzsec.broker.view.webview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jzsec.broker.base.BaseActivity;

/**
 * Created by zhaopan on 16/8/4.
 * e-mail: kangqiao610@gmail.com
 */
public class TestWebViewActivity extends BaseActivity {
    public static final java.lang.String HOMEPAGE = "http://www.baidu.com";

    WebView web;

    private Handler handler = new Handler() {


        public void handleMessage(Message msg) {
            if (msg.what == 404) {//主页不存在
                //载入本地 assets 文件夹下面的错误提示页面 404.html
                web.loadUrl("file:///android_asset/404.html");
            } else {
                web.loadUrl(HOMEPAGE);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return 0;
    }

    /**
     * 深入讲解WebView——下
     * http://my.oschina.net/kymjs/blog/412951
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {


        //最后利用 WebView 加载本地 html 文件的方法是:
        //myWebView.loadData(htmlText,"text/html", "utf-8");

        //优先使用缓存:
        web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存:
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        //判断 WebView 是否已经滚动到页面底端
        /**
         * 在View中有一个getScrollY()方法，可以返回当前可见区域的顶端距整个页面顶端的距离,也就是当前内容滚动的距离。
         *  还有getHeight()或者 getBottom()方法都返回当前 View 这个容器的高度
         *  在ViewView中还有getContentHeight() 方法可以返回整个 html 页面的高度,但并不等同于当前整个页面的高度 ,因为 WebView 有缩放功能。你可以通过如下代码来启动或关闭webview的缩放功能。
         */
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);


        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrl(WebView view, String url) {
                if (url.startsWith("http://") && getRespStatus(url) == 404) {
                    view.stopLoading();
                    //载入本地 assets 文件夹下面的错误提示页面 404.html
                    view.loadUrl("file:///android_asset/404.html");
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        new Thread(new Runnable() {
            public void run() {
                Message msg = new Message();
                //此处判断主页是否存在,因为主页是通过 loadUrl 加载的,
                //此时不会执行 shouldOverrideUrlLoading 进行页面是否存在的判断
                //进入主页后,点主页里面的链接,链接到其他页面就一定会执行shouldOverrideUrlLoading 方法了
                if (getRespStatus(HOMEPAGE) == 404) {
                    msg.what = 404;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }

    private int getRespStatus(String homepage) {
        return 0;
    }

}
