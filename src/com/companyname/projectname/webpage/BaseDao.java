package com.companyname.projectname.webpage;


import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.companyname.projectname.criteria.SqlOperator;
import com.companyname.projectname.log.Log;
import com.companyname.projectname.opt.SimpleDataSource;
import com.companyname.projectname.opt.ThreadUtils;


/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午11:32:53
 */
public abstract class BaseDao extends SqlOperator{
	
	private org.apache.ibatis.session.SqlSession 				sqlSession;
	private org.springframework.jdbc.core.JdbcTemplate		 	jdbcTemplate;
	
	/**
	 * 获取Ibatis的Mapper工具
	 * @return
	 */
	protected <T> T getMapper(Class<T> T) {
		
		if(sqlSession == null) {
			try {
				
				sqlSession = ThreadUtils.currentSqlSession();
				
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
