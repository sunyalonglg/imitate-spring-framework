package com.iwork.core.dao.connectionpool.pool;


import com.iwork.core.dao.connectionpool.DBbean;
import com.iwork.core.dao.connectionpool.interfaces.ConnectionPoolInter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * 连接池
 *
 * @author sunyalong
 * @version 1.0, 2018-7-23 22:07:46
 */
public class ConnectionPool implements ConnectionPoolInter {

    private DBbean dbBean;
    private Boolean isActive = false;
    private Integer contActive = 0; //创建记录总数

    private List<Connection> freeConnections = new Vector<Connection>();    //空闲链接
    private List<Connection> activeConnections = new Vector<Connection>();  //活动连接
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    private ConnectionPool(DBbean dbBean) {
        this.dbBean = dbBean;
        init();
    }

    private static ConnectionPool pool;
    public static ConnectionPool getPool(){
        if(pool == null) {
            pool = new ConnectionPool(DBbean.getBean());
        }
        return pool;
    }
    public void init(){
        try{
            Class.forName(dbBean.getDriverName());
            for(int i = 0;i < dbBean.getInitConnection();i++){
                Connection conn = newConnection();
                if(conn!=null){
                    freeConnections.add(conn);
                    contActive++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public Connection newConnection() throws ClassNotFoundException, SQLException{
        Connection conn = null;
        if(dbBean != null){
            Class.forName(dbBean.getDriverName());
            conn = DriverManager.getConnection(dbBean.getUrl(),dbBean.getUserName(),dbBean.getPassword());
        }
        return conn;
    }

    public synchronized Connection getConnection() {
        Connection conn = null;
        try {
            if(contActive < this.dbBean.getMaxActiveConnections()){
                if(freeConnections.size() > 0){
                    conn = freeConnections.get(0);
                    if(conn!=null){
                        threadLocal.set(conn);
                    }
                    freeConnections.remove(0);
                }else{
                    conn = newConnection();
                }
            }else{
                wait(this.dbBean.getConnTimeOut());
                conn = getConnection();
            }
            if(isVaild(conn)){
                activeConnections.add(conn);
                contActive++;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return conn;
    }

    public Boolean isVaild(Connection conn){
        try{
            if(conn == null || conn.isClosed())
            {
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public  Connection getCurrentConnection() {
        // TODO Auto-generated method stub
        Connection conn = threadLocal.get();
        if(!isVaild(conn)){
            conn = getConnection();
        }
        return conn;
    }

    public synchronized void close(Connection conn) throws SQLException {
        // TODO Auto-generated method stub
        if(isVaild(conn) && !(freeConnections.size()> dbBean.getMaxConnections())){
            freeConnections.add(conn);
            activeConnections.remove(conn);
            contActive--;
            threadLocal.remove();
            notifyAll();
        }
    }

    public void destroy() {
        // TODO Auto-generated method stub
        for (Connection conn : freeConnections) {
            try {
                if(isVaild(conn))conn.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        for(Connection conn : activeConnections){
            try {
                if(isVaild(conn))conn.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        isActive = false;
        contActive = 0;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void checkPool() {
        // TODO Auto-generated method stub
        if(dbBean.getIsCheckPool()){
            new Timer().schedule(new TimerTask() {
                public void run() {
                    System.out.println("空闲连接数"+freeConnections.size());
                    System.out.println("活动连接数"+activeConnections.size());
                    System.out.println("总连接数"+contActive);
                }
            }, dbBean.getLazyCheck(),dbBean.getPeriodCheck());
        }

    }

}
