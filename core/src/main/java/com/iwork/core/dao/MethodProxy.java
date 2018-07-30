package com.iwork.core.dao;

import com.iwork.core.annotation.ID;
import com.iwork.core.annotation.Repostory;
import com.iwork.core.annotation.SQL;
import com.iwork.core.dao.connectionpool.pool.ConnectionPool;
import com.iwork.core.utils.ReflectUtils;
import com.iwork.core.utils.SqlUtils;
import com.iwork.core.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * dao所有的方法都会执行以下的Invoke方法.
 *
 * @author sunyalong
 * @version 1.0, 2018-7-23 22:27:59
 */
public class MethodProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获得表名称
        String tableName = proxy.getClass().getInterfaces()[0].getAnnotation(Repostory.class).tableName();
        // 获得执行sql
        String sql = joinSql(proxy, method, tableName);
        // 获得当前线程的connection
        Connection conn = ConnectionPool.getPool().getCurrentConnection();
        // 执行sql
        return executer(sql, conn, args, method, proxy);
    }

    /**
     * 执行sql的方法
     *
     * @param sql
     * @param conn
     * @param args
     * @param proxy
     * @return
     */
    private Object executer(String sql, Connection conn, Object[] args, Method method, Object proxy) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            // 调用查询方法
            if (method.getName().equals("findByPage") || method.getName().equals("findByPrimaryKey")) {
                // 系统生成的方法
                return findSystemGenerate(sql, conn, args, method, proxy);
            }
            return executeSelect(sql, conn, args, method, proxy);
        } else {
            // 调用增加,删除,修改方法
            return executeUpdate(sql, conn, args);
        }

    }

    /**
     * 系统生成的方法
     *
     * @param sql
     * @param conn
     * @param args
     * @param method
     * @param proxy
     * @return
     */
    private Object findSystemGenerate(String sql, Connection conn, Object[] args, Method method, Object proxy) throws SQLException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InstantiationException {
        Type methodReturnType = ReflectUtils.getInterfaceGeneric(proxy, 0, 0);
        PreparedStatement pstmt = getPreparedStatement(sql, conn, args);
        ResultSet resultSet = pstmt.executeQuery();
        if (method.getName().equals("findByPrimaryKey")) {
            Map<String, Object> oneRowData = SqlUtils.getOneRowData(resultSet, true);
            return ReflectUtils.mapToPojo(oneRowData, Class.forName(methodReturnType.getTypeName()));
        } else if (method.getName().equals("findByPage")) {
            List<Object> lists = new ArrayList<>();
            List<Map<String, Object>> allRowData = SqlUtils.getAllRowData(resultSet);
            for (Map<String, Object> map : allRowData) {
                Object pojo = ReflectUtils.mapToPojo(map, Class.forName(methodReturnType.getTypeName()));
                lists.add(pojo);
            }
            return lists;

        }
        return null;
    }

    /**
     * 执行查询的方法
     *
     * @param sql
     * @param conn
     * @param args
     * @param method
     * @param proxy
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private Object executeSelect(String sql, Connection conn, Object[] args, Method method, Object proxy) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        String methodReturnType = method.getGenericReturnType().getTypeName();
        PreparedStatement pstmt = getPreparedStatement(sql, conn, args);
        // 执行查询方法
        ResultSet resultSet = pstmt.executeQuery();

        // 判断当前返回值类型是不是集合类型
        if (methodReturnType.startsWith("java.util.List") || methodReturnType.startsWith("java.util.Set")) {
            String genericType = methodReturnType.split("<")[1].replace(">", "");
            Collection con = null;
            if (methodReturnType.startsWith("java.util.List")) {
                con = new ArrayList();
            } else {
                con = new HashSet();
            }
            Class methodGenericReturnType = Class.forName(genericType);
            List<Map<String, Object>> allRowData = SqlUtils.getAllRowData(resultSet);
            for (Map<String, Object> row : allRowData) {
                Object pojo = ReflectUtils.mapToPojo(row, methodGenericReturnType);
                con.add(pojo);
            }
            return con;
        } else {
            // 返回值为 map或者po对象
            if (methodReturnType.equals("java.util.Map")) {
                return SqlUtils.getOneRowData(resultSet, true);
            } else if (methodReturnType.startsWith("java")) {
                Map<String, Object> oneRowData = SqlUtils.getOneRowData(resultSet, true);
                for (String key : oneRowData.keySet()) {
                    return oneRowData.get(key);
                }
            } else {
                // 确认当前对象为po类
                Map<String, Object> oneRowData = SqlUtils.getOneRowData(resultSet, true);
                return ReflectUtils.mapToPojo(oneRowData, Class.forName(methodReturnType));
            }

        }
        return null;
    }

    /**
     * 增加删除修改的方法
     *
     * @param sql
     * @param conn
     * @param args
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private Object executeUpdate(String sql, Connection conn, Object[] args) throws SQLException, IllegalAccessException {
        PreparedStatement pstmt = getPreparedStatement(sql, conn, args);
        return pstmt.executeUpdate();
    }

    /**
     * 封装 Statement
     *
     * @param sql
     * @param conn
     * @param arg
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private PreparedStatement getPreparedStatement(String sql, Connection conn, Object[] arg) throws SQLException, IllegalAccessException {
        String compileSql = StringUtils.format(sql);
        PreparedStatement pstmt = conn.prepareStatement(compileSql);
        if (arg == null || arg.length == 0) {
            return pstmt;
        }
        Object param = arg[0];
        int index = 1;
        if (param instanceof Map) {
            // 如果方法的第一个参数为Map
            Map<Object, Object> map = (Map<Object, Object>) param;
            List<String> field = StringUtils.getField(sql);
            for (String f : field) {
                Object value = map.get(f);
                pstmt.setObject(index, value);
                index++;
            }
        } else if (param.getClass().getName().startsWith("java.")) {
            // 如果参数的第一个方法为String
            pstmt.setObject(index, param);
        } else if (param instanceof List) {
            // 如果方法的第一个参数为List
            List list = (List) param;
            for (Object l : list) {
                pstmt.setObject(index, l);
                index++;
            }
        } else {
            // 剩下所有的都按照op来进行处理
            Field[] declaredFields = param.getClass().getDeclaredFields();
            List<String> sqlField = StringUtils.getField(sql);
            for (String sqlF : sqlField) {
                for (Field field : declaredFields) {
                    if (sqlF.equals(field.getName())) {
                        field.setAccessible(true);
                        Object value = field.get(param);
                        pstmt.setObject(index, value);
                        index++;
                    }
                }
            }
        }
        return pstmt;
    }


    /**
     * 编译执行sql语句
     *
     * @param proxy
     * @param method
     * @param tableName
     * @return
     * @throws ClassNotFoundException
     */
    private String joinSql(Object proxy, Method method, String tableName) throws ClassNotFoundException {
        if (method.getName().equals("findByPrimaryKey")) {
            String key = getKey(proxy);
            return "SELECT * FROM " + tableName + " WHERE " + key + "= #{" + key + "}";
        } else if (method.getName().equals("findByPage")) {
            return "SELECT * FROM " + tableName + "limit #{startNum},{endNum}";
        } else {
            SQL annotation = method.getAnnotation(SQL.class);
            if (annotation != null) {
                return annotation.value().replace("#{tableName}", tableName);
            }
        }
        return null;
    }

    /**
     * 获得dao对象的主建字段名称
     *
     * @param proxy
     * @return
     */
    public String getKey(Object proxy) throws ClassNotFoundException {
        Class clazz = ReflectUtils.getInterfaceGeneric(proxy, 0, 0);
        for (Field field : clazz.getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(ID.class);
            if (annotation != null) {
                return field.getName();
            }
        }
        return null;
    }


}
