package com.iwork.core.web.interfaces;


import com.iwork.core.web.base.RequestContext;

/**
 * 用来获得RequestContext对象的一个借口
 *
 * @author sunyalong
 * @version 1.0, 2018-7-22 18:44:20
 */
public interface WebApplicationContext {

    /**
     * 实现这个接口可以获得RequestContext类的对象
     *
     * @param requestContext
     */
    void setApplicationContext(RequestContext requestContext);

    /**
     * 讲request中的参数分装到指定的calss中
     *
     *@param clazz class的类型
     * @return
     */
    Object getObject(Class clazz);
}
