package com.companyname.projectname.opt;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.companyname.projectname.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午4:39:59
 */
public class ThreadUtils{
	
	// 绑定线程的数据库连接对象
    private static final ThreadLocal<Connection> _connection = new ThreadLocal<Connection>();
    
    // 绑定线程的ibatis会话
    private static final ThreadLocal<SqlSession> _sqlsession = new ThreadLocal<SqlSession>();
    
    // 绑定线程的http的request
    private static final ThreadLocal<HttpServletRequest> _hrequest = new ThreadLocal<HttpServletRequest>();
    
    // 绑定线程的http的response
    private static final ThreadLocal<HttpServletResponse> _hresponse = new ThreadLocal<HttpServletResponse>();
    

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
	
	public static HttpServletRequest currentHttpRequest() {
		return _hrequest.get();
	}
	
	public static HttpServletResponse currentHttpResponse() {
		return _hresponse.get();
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
	
	public static void setHttpRequest(HttpServletRequest hreq) {
		_hrequest.set(hreq);
	}
	
	public static void setHttpResponse(HttpServletResponse hresp) {
		_hresponse.set(hresp);
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
	
	public static void clearHttpRequest() {
		_hrequest.set(null);
	}
	
	public static void clearHttpResponse() {
		_hresponse.set(null);
	}

    public static void closeAll(){
        closeSqlSession();
        clearHttpRequest();
        clearHttpResponse();
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
