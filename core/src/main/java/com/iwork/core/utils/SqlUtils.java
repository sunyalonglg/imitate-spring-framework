package com.iwork.core.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分装所有的sql执行后的结果的工具类,也就是requestSet工具类.
 *
 * @author sunyalong
 * @version 1.0, 2018-7-25 17:05:33
 */
public class SqlUtils {

    /**
     * 获得ResultSet 里面所有的数据
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> getAllRowData(ResultSet rs) throws SQLException {
        List<Map<String, Object>> listData = new ArrayList<>();
        while (rs.next()){
            Map<String, Object> oneRowData = getOneRowData(rs,false);
            listData.add(oneRowData);
        }
        return listData;
    }

    /**
     * 获得ResultSet里面的一行字段
     *
     * @param rs
     * @param isNext true为自动next一下
     * @return
     * @throws SQLException
     */
    public static Map<String, Object> getOneRowData(ResultSet rs,boolean isNext) throws SQLException {
        if(isNext){
            rs.next();
        }
        Map<String, Object> dataMap = new HashMap<>();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCount = rsMeta.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dataMap.put(rsMeta.getColumnLabel(i), rs.getObject(i));
            }
        return dataMap;
    }
}
