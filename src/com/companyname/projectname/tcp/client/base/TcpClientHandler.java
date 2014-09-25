package com.companyname.projectname.tcp.client.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.system.Global;
import com.companyname.projectname.util.MyUtils;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:29:22
 */
public class TcpClientHandler{
	
	private static final Logger logger = Logger.getLogger(TcpClientHandler.class);
	
    Socket gsocket = null;
    private static final int TIME_OUT = 10000;		// 连接超时时间
    

	/**
	 * 发送消息，可独立使用
	 * 用于一个socket连接仅发送一个消息
	 * @param host
	 * @param port
	 * @param msgheader
	 * @param xmlMsg	
	 * @return
	 */
	public String sendMsgOnce(String host, int port, String xmlMsg, TcpHeader msgheader) {
		String msgRsp = null;		
		
		Socket socket = null;
		try {
			//建立连接，并设定超时时间
			InetAddress inetAddress = InetAddress.getByName(host);
			InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port) ;
			socket = new Socket();
			socket.connect(socketAddress, TIME_OUT);
			socket.setSoTimeout(TIME_OUT);
			socket.setSoLinger(true, 30);
			
			String uuid = MyUtils.getGUID();
			
			logger.info("sendXmlMsg [TCP_BEGIN (" + uuid + ")] : " + xmlMsg);
			byte[] xmlMsgb = xmlMsg.getBytes(Global.Default_Encoding);
			 
			//获取输出流
			OutputStream out_0 = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(out_0);            
			
			InputStream in_0 = socket.getInputStream();
			DataInputStream in = new DataInputStream(in_0);	
			
			
			/**  1, 发送消息***/
			if(msgheader == null) {
				msgheader = new TcpHeader(xmlMsgb.length);
			}
			//消息头
			out.write(msgheader.getBytes());
			
			//Msgbody			
            out.write(xmlMsgb);
			
            out.flush();
            
            /**  2, 读取返回消息***/
            //读取返回消息长度&消息头

//            int msglen_rec = in.readInt();
            byte[] b = new byte[4];
            in.read(b);
            int msglen_rec = lBytesToInt(b);
            in.skipBytes(4);//跳过消息头的后4个字节

            //读取消息内容
            byte[] msgRsp_b = this.readBytesContent(in, msglen_rec);
            msgRsp = new String(msgRsp_b, Global.Default_Encoding);
            logger.info("getXmlMsg  [TCP_END   (" + uuid + ")] : " + msgRsp);

		} catch (UnknownHostException e) {
			Log.printStackTrace(e);
			msgRsp = null;
		} catch (Exception e) {
			Log.printStackTrace(e);
			msgRsp = null;
		} finally {
			try {
				if (socket != null && socket.isConnected()) {
					socket.close();
				}
			} catch (IOException e) {
				Log.printStackTrace(e);
			}
		}

		return msgRsp;
	}
	
	public String sendMsgOnce(String host, int port, String xmlMsg) {
		return this.sendMsgOnce(host, port, xmlMsg, null);
	}
	
	
	/**
	 * 将低字节在前的byte数组转换为int
	 * @param b     byte[]
	 * @return int
	 */
	public static  int lBytesToInt(byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[3 - i] >= 0) {
				s = s + b[3 - i];
			} else {
				s = s + 256 + b[3 - i];
			}
			s = s * 256;
		}
		if (b[0] >= 0) {
			s = s + b[0];
		} else {
			s = s + 256 + b[0];
		}
		return s;
	}	
	
	
	/**
	 * 输入流的字节数据读取
	 * @param in
	 * @param msglen_rec
	 * @return
	 * @throws IOException
	 */
	private byte[] readBytesContent(DataInputStream input,int msglen_rec) throws IOException {
		int bytesRead = 0;
		int n = 0;
		int leftbytes = msglen_rec;
		byte[] in_b = new byte[msglen_rec];
		while (leftbytes > 0
				&& (n = input.read(in_b, bytesRead, leftbytes)) != -1) {
			bytesRead = bytesRead + n;
			leftbytes = msglen_rec - bytesRead;			
		}
		return in_b;
	} 
}
