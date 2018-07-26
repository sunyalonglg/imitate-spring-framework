package com.iwork.core.dao.connectionpool.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

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
