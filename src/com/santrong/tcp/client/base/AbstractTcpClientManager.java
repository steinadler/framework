package com.santrong.tcp.client.base;


/**
 * @Author weinianjie
 * @Date 2014-7-9
 * @Time 上午1:35:19
 */
public abstract class AbstractTcpClientManager {
	
	
	protected String 					host;
	protected int 						port;
	protected TcpClientHanlder 			tcpHanlder	 	= new TcpClientHanlder();
	
	protected String getInstanceKey() {
		return host + ":" + port;
	}
	
}
