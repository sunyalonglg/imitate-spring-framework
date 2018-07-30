package com.iwork.core.web.base;

import com.alibaba.fastjson.JSON;
import com.iwork.core.utils.ServletUtils;
import com.iwork.core.web.interfaces.WebApplicationContext;

/**
 * ModelActionSupport这个为Controller继承的一个父类,继承这个父类后可以获得最为基本的请求信息
 *
 * @param <T>
 * @author sunyalong
 * @version 1.0, 2018-7-30 11:23:33
 */
public abstract class ModelActionSupport<T> implements WebApplicationContext {

    /** 基本模型 */
    protected T model;

    /** 此次请求的request ,等基本类 */
    protected RequestContext context;

    @Override
    public void setApplicationContext(RequestContext requestContext) {
        this.context = requestContext;
    }

    @Override
    public Object getObject(Class clazz) {
        String json = ServletUtils.readJson(context.getRequest(),context.getResponse());
        return  JSON.parseObject(json,clazz);
    }
}
