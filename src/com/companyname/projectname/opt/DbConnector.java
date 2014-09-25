package com.companyname.projectname.opt;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午4:44:52
 */
public class DbConnector {
	
	private static Logger log = Logger.getLogger(DbConnector.class);
	
    // 使用配置文件的池连接数据库
    public static Connection getConnection_pool() {
    	
    	Connection conn = null;
    	
        try {
        	conn =  DriverManager.getConnection("proxool.framework");
        } catch (Exception e) {
        	log.info(e);
        }
        
        return conn;
    }    

    // 获取默认的连接
    public static Connection getConnection(){
        Connection conn = getConnection_pool();
        return conn;
	}
}
