package com.santrong.opt;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * @Author weinianjie
 * @Date 2014-7-6
 * @Time 下午9:59:54
 */
public class SimpleDataSource implements DataSource {
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new RuntimeException("Unsupport Operation.");
	}

	
	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}


	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		throw new RuntimeException("Unsupport operation.");

	}


	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		throw new RuntimeException("Unsupport operation.");

	}


	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return DataSource.class.equals(iface);
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return (T)this;
	}


	@Override
	public Connection getConnection() throws SQLException {
		return ThreadUtil.currentConnection();
	}


	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return this.getConnection();
	}


	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		Logger log = Logger.getLogger("");
		return log;
	}

}
