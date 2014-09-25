package com.santrong.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.santrong.base.BaseAction;
import com.santrong.demo.dao.DemoDao;
import com.santrong.demo.entry.DemoForm;

@Controller
public class Test1Action extends BaseAction{
	

	
	
	@RequestMapping("fn1")
	public String Fn1() {
		try{
			
			DemoForm form = new DemoForm();
			form.setId("hibernate");
			form.setField1("5");
			
//			ThreadUtil.beginTranx();
			DemoDao dao = new DemoDao();
			dao.jdbc();
//			dao.template();
			dao.ibatis();
//			dao.ibatis2();
//			dao.hibernate();
//			dao.insert(form);
			
		}finally {
//			ThreadUtil.rollbackTranx();
		}
		return "index";
	}
}
