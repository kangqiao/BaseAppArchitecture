package com.jzsec.broker.okgo;

import android.util.JsonReader;

import com.alibaba.fastjson.JSONReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by zhaopan on 2017/6/14.
 * e-mail: kangqiao610@gmail.com
 */

public abstract class BaseResponse<TT> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;

    public abstract String getDataString();

    public Object generateJSONData(){
        try {
            return new JSONTokener(getDataString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject dataToJSONObject() {
        Object data = generateJSONData();
        if (data instanceof JSONObject) {
            return (JSONObject) data;
        }
        return null;
    }

    public JSONArray dataToJSONArray() {
        Object data = generateJSONData();
        if (data instanceof JSONArray) {
            return (JSONArray) data;
        }
        return null;
    }

    public TT parseJsonObj(){
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        return parseJsonObj(getDataString(), type);
    }

    public <T> T parseJsonObj(Class<T> type) {
        return parseJsonObj(getDataString(), type);
    }

    public <T> List<T> parseJsonArr(Class<T> type) {
        return parseJsonArr(dataToJSONArray(), type);
    }

    public static <T> T parseJsonObj(String jsonStr, Type type) {
        return Convert.fromJson(jsonStr, type);
    }

    public static <T> T parseJsonObj(String jsonStr, Class<T> type) {
        return Convert.fromJson(jsonStr, type);
    }

    public static <T> List<T> parseJsonArr(JSONArray jarr, Class<T> type) {
        if (null != jarr && jarr.length() > 0) {
            List<T> retList = new ArrayList<>();
            for (int i = 0; i < jarr.length(); i++) {
                T bean = parseJsonObj(jarr.optJSONObject(i).toString(), type);
                retList.add(bean);
            }
            return retList;
        }
        return null;
    }
}
