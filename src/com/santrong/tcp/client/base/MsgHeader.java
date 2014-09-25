package com.santrong.tcp.client.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MsgHeader {
	short sLen;			//长度 unsigned short
	short sVer = 1;		//版本 unsigned short
	short sMsgType;		//消息类型 unsigned short
	short sData;	    //保留 unsigned short
	
	private ByteBuffer byteBuffer =  ByteBuffer.allocate(8);
	
	public MsgHeader(short slen, short sVer, short smsgtype){
		this.sLen = slen;
		this.sVer = sVer;
		this.sMsgType = smsgtype;
		this.sData = 0;
	}
	
	public MsgHeader(short slen,short smsgtype){
		this.sLen = slen;
		this.sVer = 1;
		this.sMsgType = smsgtype;
		this.sData = 0;		
	}
	
	public MsgHeader(short slen,short sver,short smsgtype,short sdata ){
		this.sLen = slen;
		this.sVer = sver;
		this.sMsgType = smsgtype;
		this.sData = sdata;
	}
	
	/**
	 * 旧版Beyonsys录播协议头
	 * @return
	 */
	public byte[] getBytes(){		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);		
		
		byteBuffer.put(shortToHH(sLen));
		
		if (this.sVer == 2012) {
			// 新版采集端协议头
			byteBuffer.put(shortToHH(sVer));
			byteBuffer.put(shortToHH(sMsgType));
			byteBuffer.put(shortToHH(sData));	
			
		} else {
			// 旧版Beyonsys录播协议头
			byteBuffer.putShort(sVer);
			byteBuffer.putShort(sMsgType);
			byteBuffer.putShort(sData);
		}
		return byteBuffer.array();
	}
	
	public void putBytes(byte[] bytes){		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		byteBuffer.put(bytes);
		this.sLen = byteBuffer.getShort(0);
		this.sVer = byteBuffer.getShort(2);
		this.sMsgType = byteBuffer.getShort(4);
		this.sData = byteBuffer.getShort(6);		 	
	}
	
	public short getsLen() {
		return sLen;
	}
	public void setsLen(short sLen) {
		this.sLen = sLen;
	}
	public short getsVer() {
		return sVer;
	}
	public void setsVer(short sVer) {
		this.sVer = sVer;
	}
	public short getsMsgType() {
		return sMsgType;
	}
	public void setsMsgType(short sMsgType) {
		this.sMsgType = sMsgType;
	}
	public short getsData() {
		return sData;
	}
	public void setsData(short sData) {
		this.sData = sData;
	}
	
	

//	
//   public int getUnsignedByte (int data){      //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
//         return data&0x0FFFF;
//   }    
   
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
   
   public static void main(String args[]){
	   MsgHeader head= new MsgHeader((short)1,(short)1);
	   head.setsLen((short)99);
	   head.setsVer((short)1);
	   head.setsMsgType((short)2);
	   head.setsData((short)3);
	   byte[] b = head.getBytes();
	   
	   ByteBuffer bf =  ByteBuffer.allocate(8);
	   bf.put(b);
	   //ByteBuffer bf2 = bf.slice();
	   System.out.println(bf.getShort(0));
	   System.out.println(bf.getShort(2));
	   System.out.println(bf.getShort(4));
	   System.out.println(bf.getShort(6));
	   
   }
	
}
