package com.santrong.tcp.client.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.santrong.log.Log;

public class TcpClientHanlder{
	
	private static final String Encoding = "UTF-8";
	
    Socket gsocket = null;
    private static final int TIME_OUT = 10000;		// 连接超时时间
    
	
	public void createSocketConn(String host, int port){
		//建立连接，并设定超时时间
		if(gsocket == null) {
			InetAddress inetAddress;
			try {
				inetAddress = InetAddress.getByName(host);
				InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port) ;
				gsocket = new Socket();
				gsocket.connect(socketAddress, TIME_OUT);
				gsocket.setSoTimeout(TIME_OUT);	
				gsocket.setSoLinger(true, 30);
				
			} catch (UnknownHostException e) {
				Log.printStackTrace(e);				
			} catch (Exception e) {
				Log.printStackTrace(e);			
			}		
		}
	}	
	
	
	public void closeSocketConn(){
		try {
			if (gsocket != null && gsocket.isConnected()) {
				gsocket.close();
				gsocket = null;
			}
		} catch (IOException e) {
			Log.printStackTrace(e);
		}
	}
	
	/**
	 * 发送消息，不能独立使用，必须配合createSocketConn()  closeSocketConn()  
	 * 用于同一个socket连接上发送多个消息 （多次调用该方法）
	 * @param header
	 * @param xmlMsg	 
	 * @return
	 * @throws Exception
	 */
	public String sendMsg(MsgHeader msgheader, String xmlMsg) throws Exception {
		String msgRsp = null;		
		if(gsocket==null){
			throw new Exception("Please call createSocket() first!");
		}
		
		try {
			Log.debug("sendXmlMsg : " + xmlMsg);
			byte[] xmlMsgb = xmlMsg.getBytes(Encoding);
			 
			//获取输出流
			OutputStream out_0 = gsocket.getOutputStream();
			DataOutputStream out = new DataOutputStream(out_0);            
			
			InputStream in_0 = gsocket.getInputStream();
			DataInputStream in = new DataInputStream(in_0);	
			
			/**  1, 发送消息***/
			//消息头			
			if(msgheader==null){			   
			   msgheader = new MsgHeader((short)(xmlMsgb.length + 8), (short)2012, (short)1); //固定消息码为2012	
			}
			msgheader.setsLen((short)(xmlMsgb.length + 8));			
			out.write(msgheader.getBytes());
			 
			//Msgbody			
            out.write(xmlMsgb);
			
            out.flush();
            
            /**  2, 读取返回消息***/
            //读取返回消息长度&消息头

            short msglen_rec = (short)(in.readShort() - 8);
            in.skipBytes(6);//跳过消息头的后六个字节

            //读取消息内容
            byte[] msgRsp_b = this.readBytesContent(in, msglen_rec);	
            msgRsp = new String(msgRsp_b, Encoding);
            Log.debug("getXmlMsg : " + msgRsp);
		 
		} catch (UnknownHostException e) {
			Log.printStackTrace(e);
			msgRsp = null;
		} catch (Exception e) {
			Log.printStackTrace(e);
			msgRsp = null;
		} 
		return msgRsp;

	}
	
	/**
	 * 发送消息，可独立使用
	 * 用于一个socket连接仅发送一个消息
	 * @param host
	 * @param port
	 * @param msgheader
	 * @param xmlMsg	
	 * @return
	 */
	public String sendMsgOnce(String host, int port, MsgHeader msgheader, String xmlMsg) {
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
			
			Log.debug("sendXmlMsg : " + xmlMsg);
			byte[] xmlMsgb = xmlMsg.getBytes(Encoding);
			 
			//获取输出流
			OutputStream out_0 = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(out_0);            
			
			InputStream in_0 = socket.getInputStream();
			DataInputStream in = new DataInputStream(in_0);	
			
			
			/**  1, 发送消息***/
			//消息头			
			if(msgheader==null){			   
			   msgheader = new MsgHeader((short)(xmlMsgb.length + 8), (short)2012, (short)1); //固定消息码为2012	
			}
			msgheader.setsLen((short)(xmlMsgb.length + 8));			
			out.write(msgheader.getBytes());
			
			//Msgbody			
            out.write(xmlMsgb);
			
            out.flush();
            
            /**  2, 读取返回消息***/
            //读取返回消息长度&消息头

            short msglen_rec = (short)(in.readShort() - 8);
            in.skipBytes(6);//跳过消息头的后六个字节

            //读取消息内容
            byte[] msgRsp_b = this.readBytesContent(in, msglen_rec);
            msgRsp = new String(msgRsp_b, Encoding);
            Log.debug("getXmlMsg : " + msgRsp);

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
	

	/**
	 * 将int转为低字节在前,高字节在后的byte数组
	 * @param n  int
	 * @return byte[]
	 */
	public static byte[] intToLH(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}
	
	 /**  
     * 将int转为高字节在前，低字节在后的byte数组  
     * @param n int  
     * @return byte[]  
     */   
   public static byte[] intToHH(int n) {   
     byte[] b = new byte[4];   
     b[3] = (byte) (n & 0xff);   
     b[2] = (byte) (n >> 8 & 0xff);   
     b[1] = (byte) (n >> 16 & 0xff);   
     b[0] = (byte) (n >> 24 & 0xff);   
     return b;   
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
	 * 将short转为低字节在前，高字节在后的byte数组
	 * @param n short
	 * @return byte[]
	 */
	public static  byte[] shortToLH(short n) {
	    byte[] b = new byte[2];
	    b[0] = (byte) (n & 0xff);
	    b[1] = (byte) (n >> 8 & 0xff);
	    return b;
	}
	
	/**
     * 将short转为高字节在前，低字节在后的byte数组
     * @param n short
     * @return byte[]
     */ 
   public static byte[] shortToHH(short n) { 
     byte[] b = new byte[2]; 
     b[1] = (byte) (n & 0xff); 
     b[0] = (byte) (n >> 8 & 0xff); 
     return b; 
   }  
    
	
	/**
	 * 低字节数组到short的转换

	 * @param b byte[]
	 * @return short
	 */
	public static short lBytesToShort(byte[] b) {
	    int s = 0;
	    if (b[1] >= 0) {
	        s = s + b[1];
	      } else {
	        s = s + 256 + b[1];
	      }
	      s = s * 256;
	    if (b[0] >= 0) {
	      s = s + b[0];
	    } else {
	      s = s + 256 + b[0];
	    }
	    short result = (short)s;
	    return result;
	}

}
