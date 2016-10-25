package com.jzsec.broker.data.entity;

import android.text.TextUtils;

import com.jzsec.broker.GlobalConfig;
import com.jzsec.broker.utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */

public class CommonParam {
    public String token;
    public String mobilephone;
    public String deviceCode;
    public String deviceType;
    public String appVer;
    public String userId;
    public String appType;
    public String envType;
    public String client_id;

    public static CommonParam getInstance(){
        return CommonParamHolder.commonParam;
    }

    private static class CommonParamHolder {
        private static CommonParam commonParam = new CommonParam();
    }

    private CommonParam(){}

    public void update(LoginInfo info){
        setToken(info.getToken());
        setMobilephone(info.getUserInfo().getMobilephone());
        setUserId(info.getUserId());
        setClient_id(info.getUserInfo().getClient_id());
    }

    public void init() {
        setDeviceCode(CommonUtil.createDeviceId());
        setDeviceType("Android");
        setAppVer(CommonUtil.getAppVer()+"");
        setAppType(GlobalConfig.APP_TYPE+"");
        setEnvType(GlobalConfig.ENV_TYPE+"");
    }

    public Map<String, String> convertMap() {
        Map<String, String> paramMaps = new HashMap<>();
        if(!TextUtils.isEmpty(token)) paramMaps.put("token", token);
        if(!TextUtils.isEmpty(mobilephone)) paramMaps.put("mobilephone", mobilephone);
        if(!TextUtils.isEmpty(deviceCode)) paramMaps.put("deviceCode", deviceCode);
        if(!TextUtils.isEmpty(deviceType)) paramMaps.put("deviceType", deviceType);
        if(!TextUtils.isEmpty(appVer)) paramMaps.put("appVer", appVer);
        if(!TextUtils.isEmpty(userId)) paramMaps.put("userId", userId);
        if(!TextUtils.isEmpty(appType)) paramMaps.put("appType", appType);
        if(!TextUtils.isEmpty(envType)) paramMaps.put("envType", envType);
        if(!TextUtils.isEmpty(client_id)) paramMaps.put("client_id", client_id);
        return paramMaps;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getEnvType() {
        return envType;
    }

    public void setEnvType(String envType) {
        this.envType = envType;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
