package com.companyname.projectname.tcp.client;

import com.companyname.projectname.tcp.TcpDefine;
import com.companyname.projectname.tcp.client.base.LocalTcpBase;
import com.companyname.projectname.util.XmlReader;

/**
 * @author weinianjie
 * @date 2014年7月11日
 * @time 下午5:37:06
 */
public class LocalTcp10000 extends LocalTcpBase{
	
	private String addr;
	private int port;
	private int heartbeat;
	
	private int resultCode;
	private int isReboot;

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getIsReboot() {
		return isReboot;
	}

	@Override
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append(TcpDefine.Xml_Header);
		sb.append("<ReqMsg>");
			sb.append("<MsgHead>");
				sb.append("<MsgCode>").append(TcpDefine.Basic_Client_Test).append("</MsgCode>");
				sb.append("<SessionId>1</SessionId>");
			sb.append("</MsgHead>");
			sb.append("<MsgBody>");
				sb.append("<LoginReq>");
					sb.append("<Addr type=\"string\">").append(this.addr).append("</Addr>");
					sb.append("<Port type=\"int\">").append(this.port).append("</Port>");
					sb.append("<Heartbeat type=\"int\">").append(this.heartbeat).append("</Heartbeat>");
				sb.append("</LoginReq>");
			sb.append("</MsgBody>");
		sb.append("</ReqMsg>");
		return sb.toString();
	}

	@Override
	public void resolveXml(XmlReader xml) {

		this.resultCode = Integer.parseInt(xml.find("/MsgBody/LoginResp/ResultCode").getText());
		this.isReboot = Integer.parseInt(xml.find("/MsgBody/LoginResp/IsReboot").getText());
	}

}
