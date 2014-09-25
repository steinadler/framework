package com.santrong.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.santrong.base.BaseAction;
import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-10
 * @Time 下午10:05:54
 */
@Controller
public class HomeAction extends BaseAction{
	
	@RequestMapping("/index")
	public String index() {
		Log.debug("--------------1212121");
		return "index";
	}
	
	@RequestMapping("/404")
	public String page404() {
		return "404";
	}
	
	@RequestMapping("/500")
	public String page500() {
		return "404";
	}
}
