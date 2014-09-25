package com.santrong.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SystemConfig {

	// 系统语言
	private String language;
	
	// 评论审核策略
	private String catapEnable;
	
	// 是否启用验证码	private boolean loginCode;

	// session超时时间
	private int sessionTimeOut;

	// 剩余空间警报阀值	private int warnSpaceSize;
	
	// 系统title
	private String systemTitle;
	
	// 打点时间提前或延后
	private int dottimetype;
	
	// 打点时间提前或延后的时间秒数
	private int dottime;
	
	// 根据dottimetype和dottime获取调整秒数
	private int adjustTime;
	
	/**是否公开直播<p>0:不开启;1:开启</P>*/
	private boolean openLive;
	
	private static Properties props;

	private static String configFile = "sysConfig.properties";

	static {
		props = new Properties();
		InputStream in = SystemConfig.class.getClassLoader().getResourceAsStream(configFile);
		try {
			if (in != null) {
				props.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public SystemConfig() {
		language = "zh_CN";
		catapEnable = "0";
		loginCode = false;
		sessionTimeOut = 30;
		warnSpaceSize = 1024;
		systemTitle = "";
		dottimetype = 1;
		dottime = 0;
		openLive = false;
	}
	
	public boolean saveLang() {
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put("language", language);
		return this.setSystemProperty(propMap);
	}
	
	public boolean systemTitle() {
		Map<String, String> propMap = new HashMap<String, String>();
		return this.setSystemProperty(propMap);
	}

	public void getLang() {
		this.getLanguage();
	}

	public boolean saveSysConfig() {
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put("loginCode", loginCode ? "1" : "0");
		propMap.put("sessionTimeOut", sessionTimeOut + "");
		propMap.put("warnSpaceSize", warnSpaceSize + "");
		propMap.put("systemTitle", getSystemTitle());
		propMap.put("dottimetype", getDottimetype() + "");
		propMap.put("dottime", getDottime() + "");
		propMap.put("openLive", openLive ? "1" : "0");
		return this.setSystemProperty(propMap);
	}

	public void getSysConfig() {
		loginCode = getBooleanSystemProperty("loginCode", loginCode);
		sessionTimeOut = getIntSystemProperty("sessionTimeOut", sessionTimeOut);
		warnSpaceSize = getIntSystemProperty("warnSpaceSize", warnSpaceSize);
		systemTitle = getStringSystemProperty("systemTitle", this.getSystemTitle());
		dottimetype = getIntSystemProperty("dottimetype", dottimetype);
		dottime = getIntSystemProperty("dottime", dottime);
		openLive = getBooleanSystemProperty("openLive", openLive);
	}

	public String getLanguage() {
		language = getStringSystemProperty("language", this.language);
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void getCatap() {
		this.getCatapEnable();
	}

	public boolean saveCatap() {
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put("catapEnable", catapEnable);
		return this.setSystemProperty(propMap);
	}
	
	public String getCatapEnable() {
		catapEnable = getStringSystemProperty("catapEnable", this.catapEnable);
		return catapEnable;
	}

	public void setCatapEnable(String catapEnable) {
		this.catapEnable = catapEnable;
	}
	
	public boolean isLoginCode() {
		return loginCode;
	}

	public void setLoginCode(boolean loginCode) {
		this.loginCode = loginCode;
	}

	public int getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public int getWarnSpaceSize() {
		return warnSpaceSize;
	}

	public void setWarnSpaceSize(int warnSpaceSize) {
		this.warnSpaceSize = warnSpaceSize;
	}

	public String getSysProperty(String key) {
		return props.getProperty(key);
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public int getDottimetype() {
		return dottimetype;
	}

	public void setDottimetype(int dottimetype) {
		this.dottimetype = dottimetype;
	}

	public int getDottime() {
		return dottime;
	}

	public void setDottime(int dottime) {
		this.dottime = dottime;
	}

	public int getAdjustTime() {
		this.getSysConfig();
		if(this.dottimetype == 1) {
			adjustTime = - dottime;
		} else {
			adjustTime = dottime;
		}
		return adjustTime;
	}

	public void setAdjustTime(int adjustTime) {
		this.adjustTime = adjustTime;
	}
	
	public void setOpenLive(boolean openLive) {
		this.openLive = openLive;
	}

	public boolean getOpenLive() {
		openLive = getBooleanSystemProperty("openLive", this.openLive);
		return openLive;
	}
	
	public boolean isOpenLive() {
		return openLive;
	}
	/**
	 * @param propMap
	 * @return
	 */
	public boolean setSystemProperty(Map<String, String> propMap) {
		boolean ret = false;
		String filePath = this.getClass().getClassLoader().getResource("") + configFile;
		if (filePath.startsWith("file:")) {
			filePath = filePath.substring("file:".length());
		}
		OutputStream os = null;
		
		//配置文件不存在，创建一个
		if(!new File(filePath).exists()) {
			try {
				new File(filePath).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			os = new FileOutputStream(new File(filePath));
			if (os != null) {
				Set<String> keySet = propMap.keySet();
				Iterator<String> keyIt = keySet.iterator();
				while (keyIt.hasNext()) {
					String key = keyIt.next();
					String value = propMap.get(key);
					// 加入所有的键值对
					props.put(key, value);
				}
				props.store(os, null);
				ret = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public Integer getIntSystemProperty(String key, int defaultValue) {
		Integer ret = defaultValue;
		String prop = this.getSysProperty(key);
		if (prop != null) {
			try {
				ret = Integer.parseInt(prop);
			} catch (Exception e) {
			}
		}
		return ret;
	}

	public Float getFloatSystemProperty(String key, float defaultValue) {
		Float ret = defaultValue;
		String prop = this.getSysProperty(key);
		if (prop != null) {
			try {
				ret = Float.parseFloat(prop);
			} catch (Exception e) {
			}
		}
		return ret;
	}

	public Boolean getBooleanSystemProperty(String key, boolean defaultValue) {
		Boolean ret = defaultValue;
		String prop = this.getSysProperty(key);
		if (prop != null) {
			if (prop.equals("1")) {
				ret = true;
			} else {
				ret = false;
			}
		}
		return ret;
	}

	public String getStringSystemProperty(String key, String defaultValue) {
		String ret = defaultValue;
		String prop = this.getSysProperty(key);
		if (prop != null) {
			ret = prop;
		}
		return ret;
	}
}
