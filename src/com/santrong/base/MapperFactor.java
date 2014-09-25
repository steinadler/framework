package com.santrong.base;

import java.sql.SQLException;

import com.santrong.opt.ThreadUtil;

/**
 * @Author weinianjie
 * @Date 2014-7-7
 * @Time 下午10:54:14
 */
public class MapperFactor {
	public <T> T xx(Class<T> T) {
		try {
			return ThreadUtil.currentSqlSession().getMapper(T);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
