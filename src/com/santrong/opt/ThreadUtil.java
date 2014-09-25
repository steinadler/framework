package com.santrong.opt;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午4:39:59
 */
public class ThreadUtil{
	
	// 绑定线程的数据库连接对象
    private static final ThreadLocal<Connection> _connection = new ThreadLocal<Connection>();
    
    // 绑定线程的ibatis会话
    private static final ThreadLocal<SqlSession> _sqlsession = new ThreadLocal<SqlSession>();
    
    // 绑定线程的http会话
    private static final ThreadLocal<HttpSession> _hsession = new ThreadLocal<HttpSession>();
    

	public static Connection currentConnection() throws SQLException {
		Connection conn = _connection.get();
        if(conn == null || conn.isClosed()) {
            conn = DbConnector.getConnection();
            _connection.set(conn);            
        }
        if (conn == null) {
            throw new SQLException("Can't open Connection");
        }
		return conn;
	}

	public static HttpSession currentHttpSession() {
		return _hsession.get();
	}

    public static SqlSession currentSqlSession() throws SQLException {
        SqlSession session = _sqlsession.get();
        if (session == null) {
        	session = SessionManager.openSession();
            _sqlsession.set(session);
        }
        if (session == null) {
            throw new SQLException("Can't open ibatis session");
        }
        return session;
    }
    
    
	public static void setHttpSession(HttpSession hs) {
		_hsession.set(hs);
	}
    
	public static void closeConnection(){
		Connection conn = _connection.get();
		_connection.set(null);
		if (conn != null) {
			try{
	            if (!conn.isClosed()) {
	            	conn.close();
	            }
        	}catch(Exception e){
        		Log.error(e);
        	}
		}
	}

    public static void closeSqlSession() {
        SqlSession session = _sqlsession.get();
        _sqlsession.set(null);
        if (session != null) {
        	session.close();
        }
    }
    
	public static void clearHttpSession() {
		_hsession.set(null);
	}

    public static void closeAll(){
        closeSqlSession();
        clearHttpSession();        
        closeConnection();
    }
    
    public static boolean beginTranx() {
		try {
			Connection conn = currentConnection();
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public static boolean commitTranx(){
		try {
			Connection conn = currentConnection();
			if (conn.getAutoCommit()) {
				return false;
			}
			try {
				conn.commit();
				return true;
			} catch (Exception e) {
				conn.rollback();
				return false;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public static boolean rollbackTranx(){
        try {
            Connection conn = currentConnection();
            if (conn.getAutoCommit()) {
                return false;
            }

            try {
                conn.rollback();
                return true;
            } catch ( Exception e) {
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
