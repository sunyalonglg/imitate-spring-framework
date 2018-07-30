package com.iwork.core.web.parambinding;

import com.alibaba.fastjson.JSON;
import com.iwork.core.utils.AuthUtils;
import com.iwork.core.utils.ReflectUtils;
import com.iwork.core.utils.ServletUtils;
import com.iwork.core.web.base.ModelActionSupport;
import com.iwork.core.web.base.RequestContext;
import com.iwork.core.web.create.CreateClass;
import com.iwork.core.web.interfaces.BeanModel;
import com.iwork.core.web.interfaces.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.*;

/**
 * 执行Controller,的方法,以及参数的绑定.
 *
 * @author sunyalong
 * @version 1.0, 2018-7-20 23:38:09
 */
public class BastParamBinding {

    /**
     * 执行web方法
     *
     * @param method
     * @param request
     * @param response
     * @param obj
     * @param clazz
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object executer(Method method, HttpServletRequest request, HttpServletResponse response, Object obj, Class clazz) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {

        Class[] interfaces = clazz.getInterfaces();
        for (Class inter : interfaces) {
            // 是否实现了对应的context接口
            if (inter.getName().equals("com.demo.web.core.interfaces.WebApplicationContext")) {
                WebApplicationContext context = (WebApplicationContext) obj;
                RequestContext requestContext = new RequestContext(request, response);
                context.setApplicationContext(requestContext);
            }

            // 判断对象有没有实现beanmodel方法
            if (inter.getName().equals("com.demo.web.core.interfaces.BeanModel")) {
                BeanModel model = (BeanModel) obj;
                Class modelClass = model.getModel();
                String contentType = request.getContentType();

                // 读取请求参数
                String json = ServletUtils.getRequestJson(request, response);
                // 判断是不是map集合
                Object modelObj = JSON.parseObject(json, modelClass);
                model.setModel(modelObj);
            }
        }
        // 判断有没有继承ModelActionSupport
        if(ModelActionSupport.class.isAssignableFrom(clazz)){
            // 设置context
            RequestContext context = new RequestContext(request,response);
            ReflectUtils.setFiledValue(ModelActionSupport.class,"context",obj,context);

            // 设置model的值
            Class beanClazz = ReflectUtils.getClassGeneric(obj.getClass(),0);
            // 读取请求参数
            String json = ServletUtils.getRequestJson(request, response);
            Object modelObj = JSON.parseObject(json, beanClazz);
            ReflectUtils.setFiledValue(ModelActionSupport.class,"model",obj,modelObj);
        }


        // 注入属性
        AuthUtils.fieldAuthLoad(obj, clazz, CreateClass.serviceApplicationBean);

        Object invoke = method.invoke(obj);
        return invoke;
    }






}