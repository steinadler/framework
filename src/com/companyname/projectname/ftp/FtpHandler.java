package com.companyname.projectname.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.companyname.projectname.system.Global;
import com.companyname.projectname.util.FileUtils;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.net.ftp.WriteMode;


/**
 * @author weinianjie
 * @date 2014年8月11日
 * @time 下午3:50:27
 */
public class FtpHandler {
	
	public static void main(String[] args) {
		FtpHandler handler = FtpHandler.getInstance();
		handler.setIp("");
		handler.setPort(21);
		handler.setUsername("");// 不填使用匿名
		handler.setPassword("");
		
		DemoEventListener listener = new DemoEventListener();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("key", "this is val");
		handler.setMapper(map);
		handler.uploadDirectory("E:\\DO\\FTP", "SomeDir", listener);// FTP断点续传目录SomeDir示例
	}
	
	private static final Logger logger = Logger.getLogger(FtpHandler.class);
	
	private static FtpHandler instance;
	private HashMap<String, Object> mapper;
	private FileTransferClient ftp;
	private String ip;
	private int port;
	private String username;
	private String password;
	private WriteMode writeMode;
	private FTPConnectMode ftpConnectMode;
	
	private FtpHandler() {
		ftp = new FileTransferClient();
		writeMode = WriteMode.RESUME;// 首次连接默认设置为断点续传
		if(Global.FTPConnectMode == 0) {
			ftpConnectMode = FTPConnectMode.ACTIVE;
		}else if(Global.FTPConnectMode == 1) {
			ftpConnectMode = FTPConnectMode.PASV;
		}
		mapper = new HashMap<String, Object>();
	}
	
	public static FtpHandler getInstance() {
		if(instance == null) {
			instance = new FtpHandler();
		}
		return instance;
	}
	
	public void setMapper(HashMap<String, Object> mapper) {
		this.mapper = mapper;
	}
	
	public void forceStop() {
		if(ftp.isConnected()) {
			this.ftp.cancelAllTransfers();
			this.disconnect();
		}
	}
	
	public void uploadDirectory(String path, String dirname) {
		this.uploadDirectory(path, dirname, null);
	}
	
	/**
	 * 上传整个目录
	 * @param path
	 * @param dirname
	 * @param listener
	 */
	public void uploadDirectory(String path, String dirname, EventListener listener) {
		File father = new File(path);
		File son = new File(path + File.separator + dirname);
		if(!father.isDirectory() || !son.isDirectory()) {
			return;
		}
		
		this.connect();
        try {
        	
    		// 获取本地文件列表
    		List<File> localList = FileUtils.getAllFileAndDirectory(son.getPath());// 格式为:E:\workspace\data\other\f1\123.txt(windows)或者E:/workspace/data/other/f1/123.txt(linux)
    		
    		// 获取远程文件列表
    		List<FTPFile> remoteList = new ArrayList<FTPFile>();
    		if(dirExists(dirname)) {
    			remoteList = getAllFileAndDirectory(dirname);// 格式为:////other/456/789或者//////other/456/789/789.txt
    		}else {
    			createDirectoryLoop(dirname);// 创建主目录
    		}
    		
//			for(File f : localList) {System.out.println("local:" + f.getPath());}			
//			for(FTPFile f : remoteList) {System.out.println("remote:" + f.getPath() + "/" + f.getName());}
    		
			// 文件和目录对比，找出未上传或者未传完的
			String fatherPath = father.getPath();
			HashMap<String, File> map = new HashMap<String, File>();
			
			// 本地文件入栈
			for(File f : localList) {
				
				String key = f.getPath().substring(fatherPath.length());// 去除本地路径的前段
				key = key.replaceAll("\\\\", "\\/");// 把\替换成/（针对windows）
				map.put(key, f);// 最终得到的是/other/f1/123.txt格式
				
			}
			
			// 本地文件扣除
			for(FTPFile remoteFile : remoteList) {
				
				String key = remoteFile.getPath() + "/" + remoteFile.getName();
				key = key.replaceAll("(\\/)\\1+", "\\/");// 把连续两个或者两个以上/替换成一个/
				File localFile = map.get(key);
				if(localFile != null) {
					if(localFile.isDirectory()) {
						map.remove(key);
					}else {
						if(localFile.length() == remoteFile.size()) {// 文件大小一致才移除
							map.remove(key);
						}
					}
				}
				
			}
			
			// 先把目录建好
			for(Iterator<String> itor = map.keySet().iterator(); itor.hasNext();) {
				String key = itor.next();
				File file = map.get(key);
				if(file.isDirectory()) {
					createDirectoryLoop(key);
				}
			}
			
			// 再上传文件
			for(Iterator<String> itor = map.keySet().iterator(); itor.hasNext();) {
				String key = itor.next();
				File file = map.get(key);
				if(file.isFile()) {
					try{
						
						logger.info("begin upload file : " + file.getPath());
						ftp.uploadFile(file.getPath(), key, writeMode);
						logger.info("end   upload file : " + file.getPath());
						
					}catch(Exception e) {
						
						// 断点续传失败，可能是服务器不支持，使用覆盖模式再尝试一次
						if(this.writeMode == WriteMode.RESUME) {
							logger.info("FTP NOT SUPPORT RESUME, CHANGE TO OVERWRITE  ---  " + this.getIp());
							this.writeMode = WriteMode.OVERWRITE;// 更改模式再传一次，删掉上次的重新传
							
							logger.info("begin upload file : " + file.getPath());
							ftp.uploadFile(file.getPath(), key, writeMode);
							logger.info("end   upload file : " + file.getPath());
						}
						logger.error(e.getMessage(), e);
					}
				}
			}
			
			// 全部完成了，执行监听器
			if(listener != null) {
				listener.afterFinish(mapper);
			}
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
		}finally {
			this.disconnect();
		}
	}
	
	
	public void uploadFile(String filename) {
		File file = new File(filename);
		if(!file.isFile()) {
			return;
		}
          
		this.connect();
        try {
        	
			ftp.uploadFile(filename, file.getName(), writeMode);//WriteMode.RESUME表示断点续传
			
		} catch (Exception e) {
			
			// 断点续传失败，可能是服务器不支持，使用覆盖模式再尝试一次
			if(this.writeMode == WriteMode.RESUME) {
				logger.info("FTP NOT SUPPORT RESUME, CHANGE TO OVERWRITE  ---  " + this.getIp());
				this.writeMode = WriteMode.OVERWRITE;// 更改模式再传一次，删掉上次的重新传
				this.uploadFile(filename);
			}
			logger.error(e.getMessage(), e);
			
		}finally {
			this.disconnect();
		}
	}
	
	/*
	 * 循环创建目录
	 */
	private void createDirectoryLoop(String fulldir) {// 类似/1/2/3/4或者1/2/3/4或者/1/2/3/4/
		if(fulldir == null || fulldir.equals("")) {
			return;
		}
		
		StringBuilder sb = new StringBuilder("");
		String[] dirArr = fulldir.split("\\/");
		for(String dirname : dirArr) {
			if(!dirname.equals("")) {
				sb.append("/").append(dirname);
				try{
					ftp.createDirectory(sb.toString());
				}catch(Exception e) {}// ftp不提供目录存在判断，所以这里因为目录存在抛出异常，就当创建成功了
			}
		}
	}
	
	/*
	 * 获取远程目录下的文件和目录
	 */
	private List<FTPFile> getAllFileAndDirectory(String path) {
		List<FTPFile> list = new ArrayList<FTPFile>();
		try{
    		
			FTPFile[] fileArr = ftp.directoryList(path);
			if(fileArr.length > 0) {
				list.addAll(Arrays.asList(fileArr));
				
				for(FTPFile f : fileArr) {
					if(f.isDir()) {
						list.addAll(getAllFileAndDirectory(f.getPath() + "/" + f.getName()));// ftp服务器的分隔符都是/
					}
				}
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	/*
	 * 判断目录是否存在
	 */
	private boolean dirExists(String dirname) {
		try{
//			ftp.exists("xxx");// 这个方法无论对目录和文件都不准确，可能是bug
			ftp.directoryList(dirname);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	
	// TODO FTP下载
	
	
	private boolean connect() {
		try{
			if(ftp.isConnected()) {
				return true;
			}
			
	        ftp.setRemoteHost(ip);  
	        ftp.setRemotePort(port);
	        if(username == null || username.equals("")) {
	        	ftp.setUserName("anonymous");	
	        }else {
	        	ftp.setUserName(username);
	        }
	        ftp.setPassword(password);
	        ftp.setContentType(FTPTransferType.BINARY);//设置二进制方式上传   
	        ftp.connect();
	        logger.info("connect ftp : " + ip);
	        ftp.getAdvancedFTPSettings().setConnectMode(ftpConnectMode);
	        return true;
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	private boolean disconnect() {
		try{
			if(ftp.isConnected()) {
				ftp.disconnect();
				logger.info("disconnect ftp : " + ip);
			}
			return true;
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}