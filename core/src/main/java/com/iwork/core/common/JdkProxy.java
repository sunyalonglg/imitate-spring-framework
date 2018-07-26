package com.iwork.core.common;


import com.iwork.core.service.InitService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {
    /**
     * 代理对象的原对象
     */
    private Object obj ;

    public Object getInstance(Object clazz){
        obj = clazz;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = new InitService().toStartTransactional(obj, method, args, null, true);
        return method.invoke(obj,args);
    }
}
