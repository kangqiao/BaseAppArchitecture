package com.jzsec.broker.ui.rxokgo;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.utils.Zlog;
import com.lzy.okgo.model.Response;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import me.yokeyword.fragmentation.SupportFragment;
import rx.functions.Action1;

/**
 * Created by zhaopan on 2017/8/1.
 * Rxjava系列目录及说明
 * http://blog.csdn.net/jeasonlzy/article/details/74266807
 */

public class RxOkGoFragment extends BaseLazyFragment {

    private static final String TAG = "RxOkGoFragment";
    NetApi api = new NetApi();
    int[] args = {1, 2, 3, 4, 5, 6, 7};

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
            api.getNewsList(1, 20)
                    .map(new Func<Response, String>() {
                        @Override
                        public String call(Response response) {
                            return api.findLatestTitle(response);
                        }
                    })
                    .flatMap(new Func<String, AsyncJob<Uri>>() {
                        @Override
                        public AsyncJob<Uri> call(String s) {
                            return api.save(s);
                        }
                    })
                    .start(new Callback<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Zlog.e(TAG, "success:" + uri);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Zlog.e(TAG, "error:" + e.getMessage());
                        }
                    });
        });

        _click(R.id.tv_publish_subject, (Void) -> {
            PublishSubject<String> subject = PublishSubject.create();
            subject.subscribe(new Observer<String>() {
                @Override
                public void onError(Throwable e) {
                    Zlog.dt(TAG, "onError:" + e.getMessage());
                }

                @Override
                public void onComplete() {
                    Zlog.dt(TAG, "onComplete");
                }

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Zlog.dt(TAG, "onSubscribe:" + d);
                }

                @Override
                public void onNext(String s) {
                    Zlog.dt(TAG, "onNext:" + s);
                }
            });

            subject.onNext("hello zp");

            PublishSubject<Integer> source = PublishSubject.create();
            source.subscribe(new LogObserver("first"));
            source.onNext(1);
            source.onNext(2);
            source.onNext(3);
            source.subscribe(new LogObserver("second"));
            source.onNext(4);
            source.onComplete();
        });

        //当Observable完成时AsyncSubject只会发布最后一个数据给已经订阅的每一个观察者。
        _click(R.id.tv_behavior_subject, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                BehaviorSubject<Integer> source = BehaviorSubject.create();
                source.subscribe(new LogObserver("first"));
                source.onNext(1);
                source.onNext(2);
                source.onNext(3);
                source.subscribe(new LogObserver("second"));
                source.onNext(4);
                source.onComplete();
            }
        });

        //当Observable完成时AsyncSubject只会发布最后一个数据给已经订阅的每一个观察者。
        _click(R.id.tv_replay_subject, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ReplaySubject<Integer> source = ReplaySubject.create();
                source.subscribe(new LogObserver("first"));
                source.onNext(1);
                source.onNext(2);
                source.onNext(3);
                source.onNext(4);
                source.onComplete();
                source.subscribe(new LogObserver("second"));
            }
        });

        //当Observable完成时AsyncSubject只会发布最后一个数据给已经订阅的每一个观察者。
        _click(R.id.tv_async_subject, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                AsyncSubject<Integer> source = AsyncSubject.create();
                source.subscribe(new LogObserver("first"));
                source.onNext(1);
                source.onNext(2);
                source.onNext(3);
                source.subscribe(new LogObserver("second"));
                source.onNext(4);
                source.onComplete();
            }
        });

        //背压差异
        _click(R.id.tv_back_presssure, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //RxJava1.0
                rx.Observable.range(1, 20)
                        .onBackpressureBuffer()
                        .onBackpressureLatest()
                        .onBackpressureBuffer()
                        .subscribeOn(rx.schedulers.Schedulers.newThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Zlog.dt(TAG, "onBackpressure:" + integer);
                            }
                        });

                Flowable.create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                        Zlog.dt(TAG, "flowable->subscribe + onNext");
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                        e.onNext(4);
                        e.onComplete();
                    }
                }, BackpressureStrategy.DROP)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "flowable->accept:" + integer);
                            }
                        });
            }
        });

        //能避免背压问题的运算符 http://blog.csdn.net/jdsjlzx/article/details/52717636
        _click(R.id.tv_prevent_back_presssure, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                // Throttling节流
                Observable<Integer> bursty = Observable.range(1, 100);
                Observable<Integer> burstySampled = bursty.sample(500, TimeUnit.MILLISECONDS);
                burstySampled.subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.d(TAG, "sample: accept=" + integer);
                            }
                        });

                Flowable.range(1, 1000)
                        .onBackpressureDrop()
                        .onBackpressureBuffer()
                        .onBackpressureLatest()
                        .observeOn(io.reactivex.schedulers.Schedulers.newThread()/*AndroidSchedulers.mainThread()*/)
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "accept:" + integer);
                            }
                        });

            }
        });


        Person xm = new Person("one");
        _click(R.id.tv_opt_defer, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Zlog.dt(TAG, "defer=> click");
                io.reactivex.Observable.defer(new Callable<ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> call() throws Exception {
                        Zlog.dt(TAG, "defer=> call begin");
                        SystemClock.sleep(2000);
                        Zlog.dt(TAG, "defer=> call end");
                        return io.reactivex.Observable.just("1", "2", "3");
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String s) throws Exception {
                                Zlog.dt(TAG, "defer=> accept" + s);
                            }
                        });

                Zlog.dt(TAG, "defer2=> changeName one");
                Observable<Person> observable = Observable.defer(new Callable<ObservableSource<Person>>() {
                    @Override
                    public ObservableSource<Person> call() throws Exception {
                        Zlog.dt(TAG, "defer2=> call begin");
                        SystemClock.sleep(2000);
                        Zlog.dt(TAG, "defer2=> call end");
                        return Observable.just(xm);
                    }
                });

                Zlog.dt(TAG, "defer2=> changeName two");
                xm.name = "two";
                SystemClock.sleep(2000);

                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Person>() {
                            @Override
                            public void accept(@NonNull Person person) throws Exception {
                                Zlog.dt(TAG, "defer2=> accept" + person.name);
                            }
                        });
            }
        });

        _click(R.id.tv_opt_repeat, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Observable<int[]> observable = Observable.just(args);
                observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        Zlog.dt(TAG, "repeat=> apply");
                        return objectObservable;
                    }
                });
                SystemClock.sleep(2000);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<int[]>() {
                            @Override
                            public void accept(@NonNull int[] ints) throws Exception {
                                Zlog.dt(TAG, "repeat=>" + ints.toString());
                            }
                        });
            }
        });

        _click(R.id.tv_opt_interval, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                disposable = Observable.interval(0, 1000, TimeUnit.MICROSECONDS)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                Zlog.dt(TAG, "interval:" + aLong);
                            }
                        });
            }
        });

        _click2(R.id.tv_filter_filter, new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Observable.just(1, 2, 3, 4, 5, 6)
                        .filter(new Predicate<Integer>() {
                            @Override
                            public boolean test(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "filter: test:"+integer);
                                return integer % 2 == 0;
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "filter: accept:"+integer);
                            }
                        });
            }
        });

        _click2(R.id.tv_filter_take, new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Observable.just(1,2,3,4,5,6)
                        .take(3)
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "filter: take:"+integer);
                            }
                        });

                //// takeUntil ////
                /*Observable observable = Observable.
                BehaviorSubject subject = BehaviorSubject.create();
                disposable = Observable.interval(5, 1000, TimeUnit.MICROSECONDS)
                        .takeUntil(secondObservable)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                Zlog.dt(TAG, "filter: take::" + aLong);
                            }
                        });
                SystemClock.sleep(2000);
                secondObservable.*/
            }
        });

        _click2(R.id.tv_filter_skip, new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Observable.just(1,2,3,4,5)
                        .skip(3)
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "skip: consumer"+integer);
                            }
                        });
            }
        });

        _click2(R.id.tv_filter_timeout, new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Observable.just(1,2,3,4)
                        .timeout(1000, TimeUnit.MICROSECONDS)
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                Zlog.dt(TAG, "filter:tiemout:consumer:"+integer);
                            }
                        });
            }
        });
    }

    //由于周期性不停止的emit数据，所以需要在界面销毁的时候，停止订阅和发送，否则会发生内存泄露。
    Disposable disposable;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }

    public static class Person {
        String name;

        public Person(String name) {
            this.name = name;
        }
    }

    public static class LogObserver<T> implements Observer<T> {
        public String TAG;

        public LogObserver(String tag) {
            this.TAG = tag;
        }

        @Override
        public void onError(Throwable e) {
            Zlog.dt(TAG, "onError:" + e.getMessage());
        }

        @Override
        public void onComplete() {
            Zlog.dt(TAG, "onCompleted");
        }

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Zlog.dt(TAG, "onSubscribe");
        }

        @Override
        public void onNext(T s) {
            Zlog.dt(TAG, "onNext:" + s);
        }
    }

}
