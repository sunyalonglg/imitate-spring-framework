package com.iwork.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * String字符串处理的utils
 *
 * @author sunyalong
 * @version 1.0, 2018-7-22 19:26:03
 */
public class StringUtils {

    /**
     * 获得set方法的简单方法名称如  setAbc  获得  abc
     * @param name
     * @return
     */
    public static String getSetSimpleName(String name){
        String namePrefix = name.substring(3, 4).toLowerCase();
        return namePrefix + name.substring(4);
    }

    /**
     * 获得这个class的简单类名
     *
     * @param clazz
     * @return
     */
    public static String getSimpleClassName(Class clazz){
        String name = clazz.getName();
        String s = name.substring(name.lastIndexOf(".") + 1 );
        String prefix = s.substring(0, 1).toLowerCase();
        return prefix + s.substring(1);
    }

    /**
     * 获得一条sql中的#{}中间的值按照顺序排列
     *
     * @param sql
     * @return
     */
    public static List<String> getField(String sql){
        List<String> list = new ArrayList<String>();
        while (true){
            int index = sql.indexOf("#{");
            if(index == -1){
                return list;
            }
            sql = sql.substring(index + 2);
            int lastIndex = sql.indexOf("}");
            String value = sql.substring(0, lastIndex);
            sql = sql.substring(lastIndex + 1);
            list.add(value);
        }
    }

    /**
     * 将一个带有 #{abc} 字符串修改为? 号
     * @param sql
     * @return
     */
    public static String format(String sql){
        List<String> field = getField(sql);
        for (String s : field){
            sql = sql.replace("#{"+s+"}","?");
        }
        return sql;
    }
}
