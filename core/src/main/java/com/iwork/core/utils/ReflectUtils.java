package com.iwork.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class ReflectUtils {



    /**
     * 吧一个map集合装到po对象
     *
     * @param source
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    public static Object mapToPojo(Map<String,Object> source,Class clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Object obj = clazz.newInstance();
        for(String key : source.keySet()){
            ReflectUtils.setFiledValue(clazz,key,obj,source.get(key));
        }
        return obj;
    }

    /**
     * 获得一个普通类的泛型
     *
     * @param clazz
     * @param index 第几个泛型
     * @return
     */
    public static Class getClassGeneric(Class clazz,int index) throws ClassNotFoundException {
        Type actualTypeArguments = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
        return Class.forName(actualTypeArguments.getTypeName());
    }

    /**
     * 获得一个接口泛型
     *
     * @param proxy 需要获得的接口
     * @param interfaceIndex 需要获得这个接口的第几个接口类型
     * @param genericIndex 获得目标接口泛型的第几个泛型
     * @return
     */
    public static Class getInterfaceGeneric(Object proxy, int interfaceIndex, int genericIndex) throws ClassNotFoundException {
        Class<?> daoClazz = proxy.getClass().getInterfaces()[interfaceIndex];
        Type genericInterfaces = daoClazz.getGenericInterfaces()[0];
        if(genericInterfaces instanceof ParameterizedType){
            ParameterizedType p = (ParameterizedType) genericInterfaces;
            Type type = p.getActualTypeArguments()[genericIndex];
            String typeName = type.getTypeName();
            Class<?> aClass = Class.forName(typeName);
            return aClass;
        }
        return null;
    }

    /**
     * 给一个Filed赋值,使用的是暴力反射
     *
     * @param calzz
     * @param filedName
     * @param obj
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setFiledValue(Class calzz,String filedName,Object obj,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field[] declaredFields = calzz.getDeclaredFields();
        Field contextField = calzz.getDeclaredField(filedName);
        contextField.setAccessible(true);
        contextField.set(obj,value);
    }
}
