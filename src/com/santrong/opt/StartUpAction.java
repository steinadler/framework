package com.santrong.opt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.santrong.tcp.server.base.TcpServer;

/**
 * 系统启动的时候直接调用
 */
@SuppressWarnings("serial")
public class StartUpAction extends HttpServlet {
	

	@Override
	public void init() throws ServletException {
		
		// 启动TCP服务监听线程
		new Thread(new TcpServer(), "---TcpServer").start();
		
	}
	
}
