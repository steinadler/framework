package com.santrong.opt;

import java.util.HashSet;
import java.util.Set;


/**
 * @Author weinianjie
 * @Date 2014-7-7
 * @Time 下午11:26:02
 */
@SuppressWarnings("serial")
public class MsgCode {
	
	/**
	 * Basic模块监听的TCP请求
	 */
	public static final Set<String> BASIC_TCP_SERVER_SIGN = new HashSet<String>(){{
		
		add("10000");//XXXX
		add("10001");//XXXX
		
	}};
	
	
	/**
	 * Basic模块发送的TCP请求
	 */
	public static final Set<String> BASIC_TCP_SENDER_SIGN = new HashSet<String>(){{
		
		add("10000");//XXXX
		add("10001");//XXXX
		
	}};
	
	
	/**
	 * Basic模块监听的HTTP请求
	 */
	public static final Set<String> BASIC_HTTP_SENDER_SIGN = new HashSet<String>(){{
		
		add("10000");//XXXX
		add("10001");//XXXX
		
	}};
	
	
	/**
	 * Basic模块发送的HTTP请求
	 */
	public static final Set<String> BASIC_HTTP_Client_SIGN = new HashSet<String>(){{
		
		add("10000");//XXXX
		add("10001");//XXXX
		
	}};
		
}
