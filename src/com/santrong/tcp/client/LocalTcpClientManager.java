package com.santrong.tcp.client;

import java.util.HashMap;

import com.santrong.tcp.TcpDefine;
import com.santrong.tcp.client.base.AbstractTcpClientManager;
import com.santrong.tcp.client.base.MsgHeader;


/**
 * @Author weinianjie
 * @Date 2014-7-8
 * @Time 上午10:44:52
 * @description 本机服务器（本机底层会有C++写的监听服务，JAVA发送TCP请求实现底层接口调用）
 * 向其他类型的服务器发送请求，请继承AbstractTcpClientManager构建新类
 */
public class LocalTcpClientManager extends AbstractTcpClientManager{
	
	/**
	 * 可向多个同类型的服务端发送TCP请求，每个服务端对应一个客户端发送实例，可以构建长连接
	 * key====host:port
	 */
	private static HashMap<String, LocalTcpClientManager> map = new HashMap<String, LocalTcpClientManager>();
	
	private LocalTcpClientManager(){
	}
	
	public LocalTcpClientManager getInstance(){
		return this.getInstance("127.0.0.1", TcpDefine.BASIC_CLIENT_PORT);
	}
	
	public LocalTcpClientManager getInstance(String host){
		return this.getInstance(host, TcpDefine.BASIC_CLIENT_PORT);
	}
	
	public LocalTcpClientManager getInstance(String host, int port){
		this.host = host;
		this.port = port;
		LocalTcpClientManager instance = map.get(getInstanceKey());
		if(instance == null){
			instance = new LocalTcpClientManager();
			map.put(getInstanceKey(), instance);
		}
		return instance;
	}
	

	public void tcpClient10000(int a) {
		//TODO 组装xmlMsg,head里面存放final的msgCode
		String msgRsp = tcpHanlder.sendMsgOnce(host, port, new MsgHeader((short)1, (short)1), "");
		//TODO 解析msgRsp
	}
	
	public int tcpClient10001() {
		return 1;
	}	
}
