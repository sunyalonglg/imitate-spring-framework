package com.iwork.core.utils;

/**
 * 向控制台输出的工具类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-25 09:51:51
 */
public class LogUtils {

    /**
     * 输出到控制台红色字体的日志
     *
     * @param str
     */
    public static void printErr(Object str){
        System.out.println(str);
    }

    /**
     * 输出控制台白色字体的日志
     *
     * @param str
     */
    public static void print(String str){
        System.out.println(str);
    }
}
