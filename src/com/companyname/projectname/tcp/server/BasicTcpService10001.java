package com.companyname.projectname.tcp.server;

import com.companyname.projectname.tcp.server.base.AbstractTcpService;

/**
 * @Author weinianjie
 * @Date 2014-7-7
 * @Time 下午10:35:11
 */
public class BasicTcpService10001 implements AbstractTcpService{


	@Override
	public String excute(String s) {
		System.out.println("456");
		return null;
	}

}
