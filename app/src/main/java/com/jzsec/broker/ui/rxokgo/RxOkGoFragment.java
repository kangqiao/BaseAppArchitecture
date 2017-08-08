package com.jzsec.broker.ui.rxokgo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.utils.Zlog;
import com.lzy.okgo.model.Response;

import me.yokeyword.fragmentation.SupportFragment;
import rx.Observer;
import rx.Single;
import rx.subjects.PublishSubject;

/**
 * Created by zhaopan on 2017/8/1.
 */

public class RxOkGoFragment extends BaseLazyFragment {

    private static final String TAG = "RxOkGoFragment";
    ApiHelper apiHelper = new ApiHelper();

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new RxOkGoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rx_okgo, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {


        _click(R.id.tv_test1, (Void) -> {
            AsyncJob<Response> newsList = apiHelper.getNetApi().getNewsList(1, 20);
            AsyncJob<String> stringAsyncJob = newsList.map(new Func<Response, String>(){

                @Override
                public String call(Response response) {
                    return findLatestTitle(response);
                }
            });
        });

        _click(R.id.tv_publish_subject, (Void) -> {
           testPublishSubject();
        });
    }

    private void testPublishSubject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Zlog.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Zlog.d(TAG, "onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Zlog.d(TAG, "onNext:" + s);
            }
        });
        subject.onNext("hello_zp");
        subject.onCompleted();

        //
        
    }

    private String findLatestTitle(Response response) {
        return null;
    }


}
