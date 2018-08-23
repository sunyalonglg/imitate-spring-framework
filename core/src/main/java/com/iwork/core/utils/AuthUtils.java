package com.iwork.core.utils;


import com.iwork.core.annotation.Autowwired;
import com.iwork.core.annotation.Resource;
import com.iwork.core.web.create.CreateClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 自动注入的工具类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-24 22:27:31
 */
public class AuthUtils {

    /**为service注入属性
     *
     */
    public static void authAllService() throws IllegalAccessException {
        for (Map<String, Object> service : CreateClass.serviceApplicationBean) {
            for (String serviceKey : service.keySet()) {
                Object serviceObj = service.get(serviceKey);
                fieldAuthLoad(serviceObj, serviceObj.getClass(), CreateClass.daoApplicationBean);
            }
        }

    }

    /**
     * 自动装载属性的方法,就是spring的依赖注入
     *
     * @param serviceObj
     * @param clazz
     * @param applicationBean
     * @throws IllegalAccessException
     */
    public static void fieldAuthLoad(Object serviceObj, Class clazz, List<Map<String, Object>> applicationBean) throws IllegalAccessException {
        // 获得直接引用的对象属性
        Field[] declaredFields = clazz.getDeclaredFields();
        // 获得父类引用的属性
        Class superclass = clazz.getSuperclass();
        if(superclass != null){
            Field[] superclassDeclaredFields = superclass.getDeclaredFields();
            declaredFields =  ArrayUtils.copyOf(declaredFields,superclassDeclaredFields);
        }
        for (Field field : declaredFields) {
            // 获得属性上的所有注解
            Annotation[] annotations = field.getAnnotations();
            for (Annotation ann : annotations) {
                if (ann instanceof Autowwired) {
                    boolean isOk = autowwired(field, serviceObj, applicationBean);
                    if (!isOk) {
                        throw new RuntimeException("属性注入异常: " + field);
                    }
                } else if (ann instanceof Resource) {
                    Resource source = (Resource) ann;
                    if (source.value().length() == 0) {
                        boolean isOk = autowwired(field, serviceObj, applicationBean);
                        if (!isOk) {
                            throw new RuntimeException("属性注入异常: " + field);
                        }
                    } else {
                        // TODO 写在这里了,接下来需要写吧service注解加value的方式编写 完成
                        String value = source.value();
                        for (Map<String, Object> targetObj : applicationBean) {
                            for (String s : targetObj.keySet()) {
                                if (s.equals(value)) {
                                    field.setAccessible(true);
                                    field.set(serviceObj, targetObj.get(s));
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * autowwired这个注解的自动注入.
     *
     * @param field
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @Autowwired 注解的自动注入
     */
    public static boolean autowwired(Field field, Object obj, List<Map<String, Object>> applicationBean) throws IllegalAccessException {
        for (Map<String, Object> targetObjs : applicationBean) {
            for (String s : targetObjs.keySet()) {
                Class<?> type = field.getType();
                Object target = targetObjs.get(s);
                if (type == target.getClass()) {
                    // 判断两个对象相等设置属性值
                    field.setAccessible(true);
                    field.set(obj, target);
                    return true;
                } else {
                    // 判断该对象是一个接口
                    if (type.isInterface()) {
                        Class<?>[] targetObjInterface = target.getClass().getInterfaces();
                        for (Class c : targetObjInterface) {
                            if (c == type) {
                                field.setAccessible(true);
                                field.set(obj, target);
                                return true;
                            }
                        }
                    } else if (type.isAssignableFrom(target.getClass())) {
                        field.setAccessible(true);
                        field.set(obj, target);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
