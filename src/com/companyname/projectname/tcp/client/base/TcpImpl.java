package com.companyname.projectname.tcp.client.base;

import com.companyname.projectname.util.XmlReader;

/**
 * @Author weinianjie
 * @Date 2014-7-12
 * @Time 下午2:33:02
 */
public interface TcpImpl {
	String toXml();
	
	void resolveXml(XmlReader xml);
	
	String getHost();
	
	int getPort();
	
}
