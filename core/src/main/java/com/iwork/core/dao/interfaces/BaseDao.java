package com.iwork.core.dao.interfaces;

import java.util.List;

/**
 * baseDao所有的dao都继承这个,dao接口继承这个类后就会有下面两个方法不需要写,自动生成
 *
 * @param <E> 需要查询的po类
 * @param <T> 这个类的主建类型
 */
public interface BaseDao<E,T> {

    /**
     * 根据主建查询数据库
     *
     * @param key 主建
     * @return
     */
    E findByPrimaryKey(T key);

    /**
     * 分页查询
     *
     * @param page 第几页
     * @param pageNum 每页记录数
     * @return
     */
    List<E> findByPage(int page,int pageNum);


}
