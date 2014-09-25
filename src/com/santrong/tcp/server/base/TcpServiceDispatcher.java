package com.santrong.tcp.server.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.santrong.log.Log;
import com.santrong.opt.MsgCode;
import com.santrong.opt.ThreadUtil;
import com.santrong.util.XmlReader;

public class TcpServiceDispatcher implements Runnable{

	private static final String Encoding = "UTF-8";
	
	protected Socket clientSocket;
	
	public TcpServiceDispatcher(Socket s) {
		this.clientSocket = s;
	}

	public String dispatch(String getMsg) {
		
		// 第一种信令规范
		XmlReader xml = new XmlReader();
		xml.parse(getMsg);
		String msgCode = xml.find("/MsgHead/MsgCode").getText();
		if(MsgCode.BASIC_TCP_SERVER_SIGN.contains(msgCode)) {
			try {
				
				// 分发任务
				AbstractTcpService service = (AbstractTcpService) Class.forName("com.santrong.tcp.BasicTcpService" + msgCode).newInstance();
				return service.excute(getMsg);
				
			} catch (Exception e) {
				Log.printStackTrace(e);
			}
		}
		
		//第二种信令规范请实现AbstractTcpService接口构建XXXTcpService类
		
		return "";
		
	}
	

	public void run() {
		try {
			InputStream is = clientSocket.getInputStream();
			DataInputStream in = new DataInputStream(is);
			byte[] len_b = new byte[4];
			in.readFully(len_b);

			int msglen_rec = this.lBytesToInt(len_b);
			Log.info("msglen_rec:" + msglen_rec);
			
			int bytesRead = 0;
			int n = 0;
			int leftbytes = msglen_rec;
			byte[] in_b = new byte[msglen_rec];
			while (leftbytes > 0
					&& (n = in.read(in_b, bytesRead, leftbytes)) != -1) {
				bytesRead = bytesRead + n;
				leftbytes = msglen_rec - bytesRead;			
			}
			
			String str = new String(in_b, Encoding);
			Log.info("listen tcp server=======:" + str);
			String retMsg = dispatch(str);
			Log.info("return tcp server=======:" + retMsg);
			
			OutputStream os = clientSocket.getOutputStream();
			DataOutputStream out = new DataOutputStream(os);
			byte[] retMsgb = retMsg.getBytes(Encoding);
			int msglength = retMsgb.length;
			out.write(this.intTolBytes(msglength));// 先写入消息长度（前四个字节int型）				
			out.write(retMsgb);

			
			out.flush();
			
			is.close();
			in.close();
			os.close();
			out.close();
			clientSocket.close();
			ThreadUtil.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 将int转为低字节在前,高字节在后的byte数组
	 * @param n  int
	 * @return byte[]
	 */
	private byte[] intTolBytes(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	/**
	 * 将低字节在前的byte数组转换为int
	 * @param b     byte[]
	 * @return int
	 */
	private  int lBytesToInt(byte[] b) {
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

}
