package com.companyname.projectname.tcp.client.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:29:22
 */
public class TcpHeader {
	int normalDataLen;	// 标准xml数据长度，4字节
	int privateDataLen;// 私有二进制数据长度，4字节
	
	private ByteBuffer byteBuffer =  ByteBuffer.allocate(8);
	
	public TcpHeader(int normalDataLen , int privateDataLen){
		this.normalDataLen = normalDataLen;
		this.privateDataLen = privateDataLen;
	}
	
	public TcpHeader(int normalDataLen){
		this.normalDataLen = normalDataLen;
		this.privateDataLen = 0;
	}
	
	/**
	 * @return
	 */
	public byte[] getBytes(){		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		byteBuffer.putInt(normalDataLen);
		byteBuffer.putInt(privateDataLen);
		
		return byteBuffer.array();
	}
   
   public static void main(String args[]){
	   TcpHeader head= new TcpHeader(1025, 2);
	   byte[] b = head.getBytes();
	   
	   ByteBuffer bf =  ByteBuffer.allocate(8);
	   bf.put(b);
	   
	   System.out.println(bf.getInt(0));
	   System.out.println(bf.getInt(1));
	   
   }
	
}
