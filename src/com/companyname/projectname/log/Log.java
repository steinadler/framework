package com.companyname.projectname.log;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.companyname.projectname.util.MyUtils;

/**
 * @Author weinianjie
 * @Date 2014-7-5
 * @Time 下午1:16:28
 */
public class Log {
	
	private static final Logger logger = Logger.getLogger("");
	private static final Logger logger_request = Logger.getLogger("request");
	private static final Logger logger_mark = Logger.getLogger("mark");
	
	public static void debug(Object obj) {
		logger.debug(obj);
	}
	
	public static void info(Object obj) {
		logger.info(obj);
	}
	
	public static void warn(Object obj) {
		logger.warn(obj);
	}
	
	public static void error(Object obj) {
		logger.error(obj);
	}
	
	// 日志文件
	public static void logRequest(HttpServletRequest request) {
		logger_request.info(request.getRequestURI() + " --- "  + request.getQueryString() + " --- " + request.getMethod() + " --- " + MyUtils.getRequestAddrIp(request, "127.0.0.1"));
	}	
	
	public static void mark(Object obj) {
		logger_mark.info(obj);
	}
	
	
	public static void printStackTrace(Throwable e){
		if(e==null){return;}
		StackTraceElement[] stackTraceElement = e.getStackTrace();
		if(stackTraceElement==null){return;}
		logger.error(e.toString());
		for(int i=0 ; i < stackTraceElement.length ;i++){
			logger.error(stackTraceElement[i].toString());
	    }
	}
}
