package com.iwork.core.service;

import com.iwork.core.annotation.Transactional;
import com.iwork.core.common.CglibProxy;
import com.iwork.core.common.JdkProxy;
import com.iwork.core.dao.connectionpool.pool.ConnectionPool;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * service代理类的产生
 *
 * @author sunyalong
 * @version 1.0, 2018-7-25 22:10:34
 */
public class InitService {

    /**
     * 获得service的动态代理类
     *
     * @param clazz
     * @return
     */
    public static Object getProxy(Class clazz){
        Object proxy = null;
        if(clazz.getInterfaces() != null && clazz.getInterfaces().length > 0){
            // 有接口使用jdk代理
            proxy = new JdkProxy().getInstance(clazz);
        }else{
            // 使用cglib代理
            proxy = new CglibProxy().getProxy(clazz);
        }
        return proxy;
    }

    public Object toStartTransactional(Object obj, Method method, Object[] args, MethodProxy proxy,boolean isJdkProxy) throws Throwable {
        boolean transactionalFlag = false;
        // 判断类上的注解
        Transactional annotation = proxy.getClass().getAnnotation(Transactional.class);
        if(annotation != null && annotation.readOnly()){
            transactionalFlag = true;
        }
        // 判断方法上的注解
        Transactional methodAnnotation = method.getAnnotation(Transactional.class);
        if(methodAnnotation != null && methodAnnotation.readOnly()){
            transactionalFlag = true;
        }

        Connection con = ConnectionPool.getPool().getConnection();

        // 设置方法
        if(!transactionalFlag){
            con.setAutoCommit(false);
        }else{
            con.setAutoCommit(true);
        }

        Object result = null;
        try {
            if(isJdkProxy) {
                result = method.invoke(obj,args);
            }else {
                result = proxy.invokeSuper(obj, args);
            }
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            con.rollback();
        }finally {
            con.close();
        }
        return result;
    }
}
