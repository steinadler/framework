package com.companyname.projectname.tcp.client.base;

import com.companyname.projectname.tcp.TcpDefine;

/**
 * @Author weinianjie
 * @Date 2014-7-12
 * @Time 下午12:50:42
 */
public abstract class LocalTcpBase implements TcpImpl{
	
	public int getPort(){
		return TcpDefine.Basic_Client_Port;
	}
	
	public String getHost() {
		return TcpDefine.Basic_Client_Addr;
	}
}
