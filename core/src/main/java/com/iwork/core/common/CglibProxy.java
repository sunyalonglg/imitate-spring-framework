package com.iwork.core.common;

import com.iwork.core.service.InitService;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * cglib动态代理,主要用于给service业务层产生一个代理类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-30 11:04:33
 */
public class CglibProxy  implements MethodInterceptor{

    //通过Enhancer 创建代理对象
    private Enhancer enhancer = new Enhancer();


    /**
     * 获得一个service的代理类对象
     *
     * @param c
     * @return
     */
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
        return result;
    }


}
