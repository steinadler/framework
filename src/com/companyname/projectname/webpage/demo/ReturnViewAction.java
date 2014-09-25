package com.companyname.projectname.webpage.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.webpage.BaseAction;
import com.companyname.projectname.webpage.demo.entry.DemoView;
import com.google.gson.Gson;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午11:27:39
 */

//Controller代表能被spring识别的控制器
@Controller

//RequestMapping代表控制器url请求前缀
@RequestMapping("/demo")
public class ReturnViewAction extends BaseAction{
	
	/**
	 * 返回方式1，输出到returnview1.jsp页面
	 * @return
	 */
	@RequestMapping(value="/returnview1")
	public String returnView1() {
		Log.info("------DEMO:returnview1");
		return "demo/returnview1";
	}
	
	/**
	 * 返回方式2，重定向，不能重定向WEB-INF里面的文件,而且需要写上绝对路径
	 * @return
	 */
	@RequestMapping(value="/returnview2")
	public String returnView2() {
		Log.info("------DEMO:returnview2");
		return "redirect:/jsp/demo/returnview2.jsp";
	}
	
	/**
	 * 返回方式3，直接返回returnview3字符串，主要用于ajax
	 * @return
	 */
	@RequestMapping("/returnview3")
	@ResponseBody
	public String returnView3() {
		Log.info("------DEMO:returnview3");
		return "success";
	}
	
	/**
	 * 返回方式4，json
	 * @return
	 */
	@RequestMapping("/returnview4")
	@ResponseBody
	public String returnView4() {
		
		Log.info("------DEMO:returnview4");
		
		DemoView view = new DemoView();
		view.setId("123456");
		view.setField1("field1");
		
		Gson gson = new Gson();
		return gson.toJson(view);
	}
	
	/**
	 * 返回方式5，xml
	 * @return
	 */
	@RequestMapping("/returnview5")
	@ResponseBody
	public String returnView5() {
		
		Log.info("------DEMO:returnview5");
		
		DemoView view = new DemoView();
		view.setId("123456");
		view.setField1("field1");
		
		Gson gson = new Gson();
		return gson.toJson(view);
	}
}
