package com.santrong.demo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.santrong.base.BaseAction;
import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午11:26:00
 */

//Controller代表能被spring识别的控制器
@Controller

//RequestMapping代表控制器url请求前缀
@RequestMapping("/demo")
public class ReturnValueAction extends BaseAction{
	
	/**
	 * 返回数据方法1
	 * @param param1，这个是参数，可以没有
	 * @param map，使用Map，附加在参数后面，spring会自动初始化
	 * @return
	 */
	@RequestMapping(value="/returnvalue1")
	public String returnValue(String param1, Map<String, Object> map) {
		Log.info("------DEMO:returnvalue1");
		map.put("attr1", "123456");
		return "demo/returnvalue1";
	}
	
	/**
	 * 返回数据方法2
	 * @param param1，这个是参数，可以没有
	 * @param model，使用Model，附加在参数后面，spring会自动初始化
	 * @return
	 */
	@RequestMapping(value="/returnvalue2")
	public String returnValue2(String param1, Model model) {
		Log.info("------DEMO:returnvalue2");
		model.addAttribute("attr1", "123456");
		return "demo/returnvalue2";
	}
	
	/**
	 * 返回数据方法3
	 * @return
	 */
	@RequestMapping("/returnvalue3")
	public String returnValue3(){
		Log.info("------DEMO:returnvalue3");
		request.setAttribute("attr1", "789");
		return "demo/returnvalue3";
	}
}
