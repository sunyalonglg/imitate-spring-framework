package com.iwork.core.listener;

import com.iwork.core.dao.connectionpool.pool.ConnectionPool;
import com.iwork.core.utils.AuthUtils;
import com.iwork.core.utils.LogUtils;
import com.iwork.core.web.create.ClassScanner;
import com.iwork.core.web.create.CreateClass;
import com.iwork.core.web.readcfg.SystemCfg;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Set;

/**
 * Tomcat容器启动监听的Listener,系统的初始化,和创建所有的dao,service代理类,以及注入必要的属性,都是从容器启动开始的.
 *
 * @author sunyalong
 * @version 1.0, 2018-7-30 11:16:02
 */
public class ToncatStartListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        LogUtils.printErr("tomcat启动");
        // 加载容器的基础包
        String basePack = SystemCfg.getKey("base-package");
        // 得到所有基礎包下面的class
        Set<String> classNames = ClassScanner.getClassName(basePack, true);
        LogUtils.printErr("容器初始化加载类 (个):"+classNames.size());
        // 创建程序初始化类并加载到容器
        CreateClass.create(classNames);
        LogUtils.printErr("所有的类创建成功");
        // 将dao注入到service
        try {
            AuthUtils.authAllService();
            LogUtils.printErr("将dao的代理对象注入到了service");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 初始化数据源
        ConnectionPool.getPool();
        LogUtils.printErr("连接池初始化完成");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getPool().destroy();
        System.out.print("tomcat关闭");
    }
}
