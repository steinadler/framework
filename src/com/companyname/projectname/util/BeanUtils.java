package com.companyname.projectname.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.Date;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:29:22
 */
public class BeanUtils {
	
	public static void Rs2Bean(ResultSet rs, Object obj) {
		if(rs != null && obj != null) {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for(Method m : methods) {
				
				String methodName = m.getName();
				if(methodName.startsWith("set")) {
					
					String field = methodName.substring(3);// 去掉get
					field = field.replace(field.charAt(0), Character.toLowerCase((char)(field.charAt(0))));// 首字母转小写
					
					Type returnType = m.getParameterTypes()[0];
					if(returnType.equals(String.class)) {
						try {
							m.invoke(obj, rs.getString(field));
						} catch (Exception e) {}// 当没有错误处理
					}
					else if(returnType.equals(Integer.TYPE)) {
						try{
							m.invoke(obj, rs.getInt(field));
						} catch (Exception e) {}// 当没有错误处理
					}
					else if(returnType.equals(Long.TYPE)) {
						try{
							m.invoke(obj, rs.getLong(field));
						} catch (Exception e) {}// 当没有错误处理
					}					
					else if(returnType.equals(Date.class)) {
						try{
							m.invoke(obj, rs.getTimestamp(field));
						} catch (Exception e) {}// 当没有错误处理
					}
				}
			}
		}
	}
}
