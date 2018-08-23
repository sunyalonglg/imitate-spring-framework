package com.iwork.core.dao.connectionpool;

import com.iwork.core.web.readcfg.SystemCfg;

/**
 * jdbc链接初始化连接池的属性bean
 *
 * @author sunyalong
 * @version 1.0, 2018-7-23 21:53:38
 */
public class DBbean {

    private String driverName = null;    //JDBC驱动
    private String url = null; //JDBC地址
    private String userName = null;   //数据库用户名
    private String password = null;   //数据库密码

    private String poolName = "testPool";   //连接池名字
    private Integer minConnections = 3; //空闲时最小连接数
    private Integer maxConnections = 10;    //空闲时最大连接数
    private Integer initConnection = 5; //初始化连接数量
    private Long connTimeOut = 1000L;   //重复获得连接的频率
    private Integer maxActiveConnections = 100; //最大允许的连接数
    private Long ConnectionTimeOut = 1000*60*20L;   //连接超时时间
    private Boolean isCurrentConnection = true;//是否获得当前连接
    private Boolean isCheckPool = true;//是否定时检查连接池
    private Long lazyCheck = 1000*60*60L;   //延迟多少时间后开始检查
    private Long periodCheck = 1000*60*60L; //检查频率

    private static DBbean db = null;

    private DBbean(){}

    public static DBbean getBean(){
        if(db == null){
            db = new DBbean();
            db.setDriverName(SystemCfg.getKey("db-driver"));
            db.setUrl(SystemCfg.getKey("db-url"));
            db.setUserName(SystemCfg.getKey("db-username"));
            db.setPassword(SystemCfg.getKey("db-password"));
            db.setInitConnection(Integer.parseInt(SystemCfg.getKey("db-init-connection")));
        }
        return db;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Integer getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(Integer minConnections) {
        this.minConnections = minConnections;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getInitConnection() {
        return initConnection;
    }

    public void setInitConnection(Integer initConnection) {
        this.initConnection = initConnection;
    }

    public Long getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(Long connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public Integer getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public void setMaxActiveConnections(Integer maxActiveConnections) {
        this.maxActiveConnections = maxActiveConnections;
    }

    public Long getConnectionTimeOut() {
        return ConnectionTimeOut;
    }

    public void setConnectionTimeOut(Long connectionTimeOut) {
        ConnectionTimeOut = connectionTimeOut;
    }

    public Boolean getCurrentConnection() {
        return isCurrentConnection;
    }

    public void setCurrentConnection(Boolean currentConnection) {
        isCurrentConnection = currentConnection;
    }

    public Boolean getIsCheckPool() {
        return isCheckPool;
    }

    public void setIsCheckPool(Boolean checkPool) {
        isCheckPool = checkPool;
    }

    public Long getLazyCheck() {
        return lazyCheck;
    }

    public void setLazyCheck(Long lazyCheck) {
        this.lazyCheck = lazyCheck;
    }

    public Long getPeriodCheck() {
        return periodCheck;
    }

    public void setPeriodCheck(Long periodCheck) {
        this.periodCheck = periodCheck;
    }


}
