package com.demo.controller.dao;

import com.demo.controller.bean.User;
import com.iwork.core.annotation.Repostory;
import com.iwork.core.annotation.SQL;
import com.iwork.core.dao.interfaces.BaseDao;

import java.util.List;
import java.util.Map;

@Repostory(tableName = "t_hehe")
public interface UserDao extends BaseDao<User,Integer> {

    @SQL("update #{tableName} set year = #{year} where tid = #{tid}")
    void updateUser(User user);

    @SQL("update #{tableName} set year = #{year} where tid = #{tid}")
    void updateMap(User user);

    @SQL("update #{tableName} set year = #{year}")
    void updateString(Integer user);

    @SQL("insert into #{tableName} (tid,year,month,avg) value(#{tid},#{year},#{month},#{avg})")
    void insert(User user);

    @SQL("select * from #{tableName} where tid = #{tid}")
    User getUserTid(User user);

    @SQL("select * from #{tableName} where tid = #{tid}")
    Map getUserMap(Integer tid);

    @SQL("select * from #{tableName}")
    List<User> getAll();
}
