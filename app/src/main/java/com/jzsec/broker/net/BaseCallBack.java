package com.jzsec.broker.net;

import com.jzsec.broker.data.entity.CommonParam;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

/**
 * Created by zhaopan on 2016/10/19.
 * e-mail: kangqiao610@gmail.com
 */
public abstract class BaseCallBack<T> extends AbsCallback<T> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //主要用于在所有请求之前添加公共的请求头或请求参数，例如登录授权的 token,使用的设备信息等,可以随意添加,也可以什么都不传
        request.params(CommonParam.getInstance().convertMap());
    }

}
