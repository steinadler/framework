package com.santrong.log;

import org.apache.log4j.Logger;

/**
 * @Author weinianjie
 * @Date 2014-7-5
 * @Time 下午1:16:28
 */
public class Log {
	
	private static final Logger logger = Logger.getLogger(Log.class);
	
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
	
	public static void printStackTrace(Throwable Ex){
		if(Ex==null){return;}
		StackTraceElement[] stackTraceElement = Ex.getStackTrace();
		if(stackTraceElement==null){return;}
		logger.error(Ex.toString());
		for(int i=0 ; i < stackTraceElement.length ;i++){
			logger.error(stackTraceElement[i].toString());
	    }
	}
}
