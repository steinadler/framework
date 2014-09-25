package com.companyname.projectname.http.server;

import com.companyname.projectname.http.HttpDefine;
import com.companyname.projectname.http.server.base.AbstractHttpService;
import com.companyname.projectname.log.Log;
import com.companyname.projectname.util.XmlReader;

/**
 * @author weinianjie
 * @date 2014年7月24日
 * @time 下午8:35:02
 */
public class BasicHttpService30001 implements AbstractHttpService{

	@Override
	public String excute(XmlReader xml) {
		StringBuilder sb = new StringBuilder();
		
		try{
			String name1 = xml.find("/MsgHead/Name1").getText();
			sb.append(HttpDefine.Xml_Header);
			sb.append("<Return>");
			sb.append(name1 + ":" + HttpDefine.Basic_Server_Test);
			sb.append("</Return>");
		}catch(Exception e) {
			Log.printStackTrace(e);
		}

		return sb.toString();	
	}

}
