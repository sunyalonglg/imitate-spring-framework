package com.iwork.core.dao.connectionpool.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 只定义的一个连接池接口
 *
 * @author sunyalong
 * @version 1.0, 2018-7-30 11:08:58
 */
public interface ConnectionPoolInter {

    /**
     * 获得连接池
     *
     * @return
     */
    public Connection getConnection();

    /**
     * 获得当前连接
     *
     * @return
     */
    public Connection getCurrentConnection();

    /**
     * 回收连接
     *
     * @param conn
     * @throws SQLException
     */
    public void close(Connection conn) throws SQLException;

    /**
     * 销毁连接池
     */
    public void destroy();

    /**
     * 获取连接池状态
     *
     * @return
     */
    public Boolean isActive();

    /**
     * 检查连接池状态
     */
    public void checkPool();
}
