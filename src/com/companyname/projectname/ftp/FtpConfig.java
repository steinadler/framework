package com.companyname.projectname.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class FtpConfig {
	private static final Logger logger = Logger.getLogger(FtpConfig.class);

	private static Properties proplist;
	private final String conf_file = this.getClass().getResource("/").getPath() + "ftp.properties";

	private String ftpEnable;
	private String ftpIp; 
	private String ftpPort;
	private String username;
	private String password;
	private String beginTime;
	private String endTime;

	public FtpConfig() {
		ftpEnable 		= "0";
		ftpIp 			= "";
		ftpPort 		= "21";
		username		= "";
		password 		= "";
		beginTime 		= "";
		endTime 		= "";
		read();
	}

	// 地址处理，获取主机
	public String getHost() {

		String url = ftpIp;
		if (url == null || url.trim().equals("")) {
			return "";
		}
		String host = "";
		Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
		Matcher matcher = p.matcher(url);
		if (matcher.find()) {
			host = matcher.group();
		}
		return host;
	}
	
	/**
	 * 读取配置文件
	 * 
	 * @throws IOException
	 */
	public synchronized void read() {
		try {
			if (new File(conf_file).exists()) {
				FileInputStream fin = new FileInputStream(conf_file);
				proplist = new Properties();
				proplist.load(fin);
				fin.close();

				this.ftpEnable = proplist.getProperty("ftpEnable");
				this.ftpIp = proplist.getProperty("ftpIp");
				this.ftpPort = proplist.getProperty("ftpPort");
				this.username = proplist.getProperty("username");
				this.password = proplist.getProperty("password");
				this.beginTime = proplist.getProperty("beginTime");
				this.endTime = proplist.getProperty("endTime");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 更新配置文件
	 * 
	 * @throws IOException
	 * @throws TemplateException
	 */
	public synchronized boolean write(){
		try {
			FileOutputStream fos = new FileOutputStream(conf_file);
			if (proplist == null) {
				proplist = new Properties();
			}
			proplist.setProperty("ftpEnable", ftpEnable);
			proplist.setProperty("ftpIp", ftpIp);
			proplist.setProperty("ftpPort", ftpPort);
			proplist.setProperty("username", username);
			proplist.setProperty("password", password);
			proplist.setProperty("beginTime", beginTime);
			proplist.setProperty("endTime", endTime);
			proplist.store(fos, "Copyright (c) Santrong");
			fos.flush();
			fos.close();
			return true;
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	public String getFtpEnable() {
		return ftpEnable;
	}

	public void setFtpEnable(String ftpEnable) {
		this.ftpEnable = ftpEnable;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static void main(String[] args) {
		try {
			FtpConfig ftp = new FtpConfig();
			ftp.write();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
