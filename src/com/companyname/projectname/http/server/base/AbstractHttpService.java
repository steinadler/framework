package com.companyname.projectname.http.server.base;

import com.companyname.projectname.util.XmlReader;

/**
 * @Author weinianjie
 * @Date 2014-7-7
 * @Time 下午10:29:13
 */
public interface AbstractHttpService {
	
	String excute(XmlReader xml);
	
}
