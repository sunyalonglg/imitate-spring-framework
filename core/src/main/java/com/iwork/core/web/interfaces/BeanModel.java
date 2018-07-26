package com.iwork.core.web.interfaces;

/**
 * 这个接口是为web层框架设置对象参数的一接口
 *
 * @author sunyalong
 * @version 1.0, 2018-7-22 18:44:20
 */
public interface BeanModel {

    /**
     * 获得参数对象的方法
     *
     * @return
     */
    public Class getModel();

    /**
     * 设置参数对象的方法
     *
     * @param obj
     */
    public void setModel(Object obj);
}
