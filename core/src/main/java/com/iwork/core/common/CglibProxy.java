package com.iwork.core.common;

import com.iwork.core.service.InitService;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;


public class CglibProxy  implements MethodInterceptor{

    //通过Enhancer 创建代理对象
    private Enhancer enhancer = new Enhancer();

    //通过Class对象获取代理对象
    public Object getProxy(Class c){
        //设置创建子类的类
        enhancer.setSuperclass(c);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method m, Object[] args, MethodProxy proxy) throws Throwable {
        Class declaredFields = obj.getClass().getSuperclass();
        Object result = new InitService().toStartTransactional(obj, m, args, proxy, false);
//        Object result = proxy.invokeSuper(obj, args);
        return result;
    }


}
