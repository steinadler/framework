package com.santrong.tcp.server.base;

import java.net.ServerSocket;
import java.net.Socket;

import com.santrong.log.Log;
import com.santrong.tcp.TcpDefine;


public class TcpServer implements Runnable {

	public static boolean isRun = false;
	
	@Override
	public void run() {
		if(!isRun) {
			
			isRun = true;
			
			try{
				ServerSocket ss = new ServerSocket(TcpDefine.BASIC_SERVER_PORT);
				while(true)
				{
					Socket s = ss.accept();
					Log.info("tcp监听到信息！");
					new Thread(new TcpServiceDispatcher(s), "---TcpServiceDispatcher").start();// 监听到请求开启新线程执行任务
					Log.info("处理完毕");
				}
				
			}catch (Exception e) {
				isRun = false;
				Log.printStackTrace(e);
			}
			
			isRun = false;
		}
	}

}
