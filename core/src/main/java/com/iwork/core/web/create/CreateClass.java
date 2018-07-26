package com.iwork.core.web.create;

import com.iwork.core.annotation.Controller;
import com.iwork.core.annotation.Repostory;
import com.iwork.core.annotation.RequestMapping;
import com.iwork.core.annotation.Service;
import com.iwork.core.dao.InitRepostory;
import com.iwork.core.service.InitService;
import com.iwork.core.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 创建所有的java类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-18 17:02:02
 */
public class CreateClass {

    /** 程序中装在所有java class对象的容器 */
    public static List<Map<String,Map<Class,Method>>> webApplicationBean = new ArrayList<Map<String, Map<Class, Method>>>();

    /** service 的bean容器 */
    public static List<Map<String,Object>> serviceApplicationBean = new ArrayList<Map<String, Object>>();

    /** dao的bean容器 */
    public static List<Map<String,Object>> daoApplicationBean = new ArrayList<Map<String, Object>>();

    /**
     * 创建所有的java类
     *
     * @param classPath
     */
    public static void create(Set<String> classPath){
        for(String pack : classPath){
            addApplicationBean(pack);
        }
    }

    /**
     * 判断是否为容器对象添加到容器中
     *
     * @param pack
     */
    public static void addApplicationBean(String pack){
        try {
            Class<?> clazz = Class.forName(pack);
            Annotation[] annotations = clazz.getAnnotations();
            for(Annotation annotation : annotations) {
                if (annotation instanceof Controller) {
                    addToController(clazz, (Controller) annotation);
                } else if (annotation instanceof Service) {
                    addToService(clazz, (Service) annotation);
                } else if (annotation instanceof Repostory) {
                    addToRepostory(clazz, (Repostory) annotation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加到service容器中
     *
     * @param clazz
     * @param annotation
     */
    private static void addToService(Class<?> clazz, Service annotation) throws IllegalAccessException, InstantiationException {
        Object obj = InitService.getProxy(clazz);
        String value = annotation.value();
        if(value.length() == 0) {
            // service注解有指定的类名称
            value = StringUtils.getSimpleClassName(clazz);
        }

        HashMap<String, Object> objectObjectHashMap = new HashMap<String, Object>();
        objectObjectHashMap.put(value, obj);
        serviceApplicationBean.add(objectObjectHashMap);

    }

    /**
     * 添加到repostory容器中
     *
     * @param clazz
     * @param annotation
     */
    private static void addToRepostory(Class<?> clazz, Repostory annotation) {
        Object init = InitRepostory.init(clazz);
        String value = annotation.value();
        if(value.length() == 0 ){
            value = StringUtils.getSimpleClassName(clazz);
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(value,init);
        daoApplicationBean.add(map);
    }

    /**
     * 添加到应用程序的容器
     *
     * @param clazz
     * @param con
     */
    public static void addToController(Class<?> clazz,Controller con){
        Method[] methods = clazz.getMethods();
        for(Method m : methods){
            RequestMapping annotation = m.getAnnotation(RequestMapping.class);
            if(annotation != null){
                HashMap<Class,Method> classAndMethod = new HashMap<Class, Method>();
                classAndMethod.put(clazz,m);
                HashMap<String,Map<Class,Method>> url = new HashMap<String, Map<Class, Method>>();
                url.put(con.value()+"/"+annotation.value(),classAndMethod);
                webApplicationBean.add(url);
            }
        }
    }

    /**
     * 通过请求参数查询到，需要 执行的类和Method
     *
     * @param url
     * @return
     */
    public static Map<Class, Method> findAction(String url){

        for(int i = 0; i < webApplicationBean.size(); i++){
           Map<String,Map<Class, Method>> classMethodMap = webApplicationBean.get(i);
           Map<Class, Method> classMethodMap1 = classMethodMap.get(url);
           Map<Class, Method> classMethodMap2 = classMethodMap.get(url.substring(1));
           if(classMethodMap1 == null && classMethodMap2 == null){
               continue;
           }

            if(classMethodMap1 != null){
                return classMethodMap1;
            }

            if(classMethodMap2 != null){
                return classMethodMap2;
            }

        }
        return null;
    }



}
