package com.iwork.core.utils;

import java.lang.reflect.Field;

/**
 * 数组工具类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-26 12:51:09
 */
public class ArrayUtils {

    /**
     * 拷贝数组
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static Field[] copyOf(Field[] arr1, Field[] arr2){
        Field[] result = new Field[arr1.length + arr2.length];
       int index = 0;
        for(Field r1 : arr1){
            result[index] = r1;
            index++;
        }
        for(Field r2 : arr2){
            result[index] = r2;
            index++;
        }
        return result;
    }
}
