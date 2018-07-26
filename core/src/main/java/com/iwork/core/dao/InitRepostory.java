package com.iwork.core.dao;

import java.lang.reflect.Proxy;

/**
 * 初始化所有的dao接口
 *
 * @author sunyalong
 * @version 1.0, 2018-7-23 22:27:59
 */
public class InitRepostory {


    /**
     * 初始化一个dao接口成为对象
     *
     * @param calzz 接口class
     */
    public static Object init(Class calzz){
        return getInstance(calzz);
    }

    /**
     * 获得一个dao的代理对象
     *
     * @param cls
     * @return
     */
    public static Object getInstance(Class<?> cls){
        MethodProxy invocationHandler = new MethodProxy();
        Object newProxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[] { cls },
                invocationHandler);
        return (Object)newProxyInstance;
    }

}
