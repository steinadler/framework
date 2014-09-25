package com.companyname.projectname.ftp;

import java.util.HashMap;


/**
 * @author weinianjie
 * @date 2014年8月13日
 * @time 上午10:05:08
 */
public interface EventListener {
	
	/*
	 * 上传完成后执行
	 */
	void afterFinish(HashMap<String, Object> mapper);
}
