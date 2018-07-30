package com.iwork.core.web.readcfg;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取类路径下面的appplication.properties文件
 *
 * @author sunyalong
 * @version 1.0, 2018-7-30 11:30:57
 */
public class SystemCfg {

    /** 程序所有的配置 */
    private static Properties cfg = null;

    /**
     * 加载所有的配置到cfg map中
     */
    static{
        try {
            Properties pro = new Properties();
            InputStream reader = SystemCfg.class.getClassLoader().getResourceAsStream("application.properties");
            pro.load(reader);
            cfg = pro;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("配置文件加载失败!!");
        }
    }

    /**
     * 通过一个key获取这个配置文件的值
     *
     * @param key
     * @return
     */
    public static String getKey(String key){
        return cfg.getProperty(key);
    }

}
