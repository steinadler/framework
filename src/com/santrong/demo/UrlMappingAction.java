package com.santrong.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.santrong.base.BaseAction;
import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午11:22:57
 */

//Controller代表能被spring识别的控制器
@Controller

//RequestMapping代表控制器url请求前缀
@RequestMapping("/demo")
public class UrlMappingAction extends BaseAction{
	
	/**
	 * 基本路径匹配，路径总和是/demo/urlmapping1
	 * @return
	 */
	@RequestMapping("/urlmapping1")
	public String urlMapping1() {
		Log.info("------DEMO:urlmapping1");
		return "demo/urlmapping1";
	}
	
	/**
	 * 区分请求类型的路径匹配
	 * @return
	 */
	@RequestMapping(value="/urlmapping2/other", method=RequestMethod.GET)
	public String urlMapping2() {
		Log.info("------DEMO:urlmapping2");
		return "demo/urlmapping2";
	}
}
