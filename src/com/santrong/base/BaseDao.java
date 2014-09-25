package com.santrong.base;


import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.santrong.log.Log;
import com.santrong.opt.SimpleDataSource;
import com.santrong.opt.ThreadUtil;


/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午11:32:53
 */
public abstract class BaseDao {
	
	private org.apache.ibatis.session.SqlSession 				sqlSession;
	private org.springframework.jdbc.core.JdbcTemplate		 	jdbcTemplate;
	
	/**
	 * 获取Ibatis的Mapper工具
	 * @return
	 */
	protected <T> T getMapper(Class<T> T) {
		
		if(sqlSession == null) {
			try {
				
				sqlSession = ThreadUtil.currentSqlSession();
				
			} catch (SQLException e) {
				Log.error(e);
			}
		}
		
		return sqlSession.getMapper(T);
	}
	
	/**
	 * 获取jdbcTemplate的实例
	 * @return
	 */
	protected JdbcTemplate getJdbcTemplate() {
		
		if(jdbcTemplate == null) {
			
			jdbcTemplate = new JdbcTemplate(new SimpleDataSource());
			
		}
		
		return jdbcTemplate;
	}

}
