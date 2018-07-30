package com.iwork.core.common;


import com.iwork.core.service.InitService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理,主要用于给service业务层产生一个代理类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-30 11:04:33
 */
public class JdkProxy implements InvocationHandler {

    /**
     * 代理对象的原对象
     */
    private Object obj ;

    /**
     * 获得一个service的代理类对象
     *
     * @param clazz
     * @return
     */
    public Object getInstance(Object clazz){
        obj = clazz;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = new InitService().toStartTransactional(obj, method, args, null, true);
        return method.invoke(obj,args);
    }
}
