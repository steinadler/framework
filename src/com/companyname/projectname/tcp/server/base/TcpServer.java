package com.companyname.projectname.tcp.server.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.tcp.TcpDefine;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:29:22
 */
public class TcpServer implements Runnable {

	public static boolean isRun = false;
	
	@Override
	public void run() {
		if(!isRun) {
			
			isRun = true;
			ServerSocket ss = null;
			
			try{
				Log.info("************Startup TcpServer Thread************");
			}catch(Exception e) {}			
			
			try{
				ss = new ServerSocket(TcpDefine.Basic_Server_port);
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
			}finally {
				if(ss != null && !ss.isClosed()) {
					try {
						ss.close();
					} catch (IOException e) {
						Log.printStackTrace(e);
					}
				}
			}
			
			isRun = false;
		}
	}

}
