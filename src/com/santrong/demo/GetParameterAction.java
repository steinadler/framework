package com.santrong.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.santrong.base.BaseAction;
import com.santrong.demo.entry.DemoForm;
import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午11:24:32
 */

//Controller代表能被spring识别的控制器
@Controller

//RequestMapping代表控制器url请求前缀
@RequestMapping("/demo")
public class GetParameterAction extends BaseAction{

	/**
	 * 获取参数方法1，只能用于GET方式
	 * @param param1
	 * @param param2
	 * @return
	 */
	@RequestMapping(value="/getparameter1/{param1}/{param2}", method=RequestMethod.GET)
	public String getParameter1(@PathVariable String param1, @PathVariable String param2) {
		Log.info("------DEMO:getparameter1:" + param1 + "|" + param2);
		return findJsp("demo/getparameter1");
	}
	
	/**
	 * 获取参数方法2，时候所数据整个表单的获取
	 * 请求参数a会自动映射到view里的a属性上
	 * @param view
	 * @return
	 */
	@RequestMapping(value="/getparameter2", method=RequestMethod.POST)
	public String getParameter2(@ModelAttribute("DemoView") DemoForm form) {
		Log.info("------DEMO:getparameter2:" + form.getId());
		return "demo/getparameter2";
	}
	
	/**
	 * 获取参数方法3
	 * @param param1，要求必须有参数，否则404
	 * @param param2，参数名转换
	 * @param param3，简洁方法，可以没有参数
	 * @return
	 */
	@RequestMapping(value="/getparameter3")
	public String getParameter3(@RequestParam String param1, @RequestParam("othername") String param2, String param3) {
		Log.info("------DEMO:getparameter3:" + param1 + "|" + param2 + "|" + param3);
		return "demo/getparameter3";
	}
	
	/**
	 * 获取参数方法4
	 * 方法参数附加request的时候spring会识别且提供
	 * 可以使用父类现成的request
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getparameter4")
	public String getParameter4(HttpServletRequest request, HttpServletResponse response) {
		
		String param1 = request.getParameter("param1");
		int param2 = ServletRequestUtils.getIntParameter(request, "param2", -1);
		String param3 = this.request.getParameter("param3");
		
		Log.info("------DEMO:getparameter4:" + param1 + "|" + param2 + "|" + param3);
		
		return "demo/getparameter4";
	}
}
