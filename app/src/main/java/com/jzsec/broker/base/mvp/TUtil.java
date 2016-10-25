package com.jzsec.broker.base.mvp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhaopan on 16/4/30.
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            Type superclass = o.getClass().getGenericSuperclass();
            if(superclass instanceof ParameterizedType){
                Type[] typeArguments = ((ParameterizedType)superclass).getActualTypeArguments();
                if(null != typeArguments && null != typeArguments[i]){
                    return ((Class<T>)typeArguments[i]).newInstance();
                }
            }
            //return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
