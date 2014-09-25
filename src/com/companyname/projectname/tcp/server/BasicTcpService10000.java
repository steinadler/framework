package com.companyname.projectname.tcp.server;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.tcp.server.base.AbstractTcpService;
import com.companyname.projectname.util.XmlReader;


/**
 * @Author weinianjie
 * @Date 2014-7-7
 * @Time 下午10:35:11
 */
public class BasicTcpService10000 implements AbstractTcpService{

	@Override
	public String excute(String msgBody) {
		XmlReader xml = new XmlReader();
		xml.parse(msgBody);
		
		String username = xml.find("/UserName").getText();
		Log.info("server--get--username:" + username);
		
	
		
		StringBuffer buff = new StringBuffer();
		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buff.append("<ResponseMsg>");
		buff.append(   "<MsgHead>");
		buff.append(       "1111111111111"  );
		buff.append(   "</MsgHead>");
		
		buff.append(   "<MsgBody>");
		buff.append(       "<IsAdmin>yes</IsAdmin>"  );
		buff.append(   "</MsgBody>");		
		buff.append("</ResponseMsg>");
		
		
		return buff.toString();
		
	}
	
}
