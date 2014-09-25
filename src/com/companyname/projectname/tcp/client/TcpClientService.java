package com.companyname.projectname.tcp.client;

import com.companyname.projectname.tcp.client.base.AbstractTcpService;
import com.companyname.projectname.tcp.client.base.TcpImpl;
import com.companyname.projectname.util.XmlReader;


/**
 * @Author weinianjie
 * @Date 2014-7-8
 * @Time 上午10:44:52
 * @description 本机服务器（本机底层会有C++写的监听服务，JAVA发送TCP请求实现底层接口调用）
 * 向其他类型的服务器发送请求，请继承AbstractTcpService构建新类
 */
public class TcpClientService extends AbstractTcpService{
	
	private static TcpClientService instance;
	
	private TcpClientService(){
	}	
	
	public static TcpClientService getInstance(){
		if(instance == null){
			instance = new TcpClientService();
		}
		return instance;	
	}
	
	public void request(TcpImpl service) {
		
		// 发送TCP
		String msgRsp = tcpHanlder.sendMsgOnce(service.getHost(), service.getPort(), service.toXml().replaceAll(">((?i)null)<", "><"));
		
		// 解析返回XML
		XmlReader xml = new XmlReader();
		xml.parse(msgRsp);
		service.resolveXml(xml);
		
	}
}
