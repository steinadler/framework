package com.companyname.projectname.opt;


import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.companyname.projectname.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午4:46:00
 */
public class SessionManager {
    private static final String resource = "/ibatis-config.xml";
    private static SqlSessionFactory sqlMapper;

    
    static {
    	
    	try{
    		
	        Reader reader = Resources.getResourceAsReader(SessionManager.resource);
	        sqlMapper = new SqlSessionFactoryBuilder().build(reader);
	        
    	}catch(Exception e) {
    		
    		Log.error(e);
    		
    	}
    	
    }
    
    public static SqlSession openSession() {
    	
        try {
        	
            Connection conn = ThreadUtils.currentConnection();
            return sqlMapper.openSession(conn);

        } catch (Exception e) {
        	
            Log.error(e);
            
        }

        return null;
    }

}

