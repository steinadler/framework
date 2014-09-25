package com.companyname.projectname.webpage.home;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.system.Global;
import com.companyname.projectname.util.MyUtils;
import com.companyname.projectname.webpage.BaseAction;
import com.companyname.projectname.webpage.home.dao.MenuDao;
import com.companyname.projectname.webpage.home.entry.MenuItem;
import com.companyname.projectname.webpage.user.dao.UserDao;
import com.companyname.projectname.webpage.user.entry.UserItem;
import com.mysql.jdbc.StringUtils;

/**
 * @Author weinianjie
 * @Date 2014-7-10
 * @Time 下午10:05:54
 */
@Controller
public class HomeAction extends BaseAction{
	
	@RequestMapping("/index")
	public String index(){
			// 获取菜单
			List<MenuItem> navigator = null;
			MenuDao menuDao = new MenuDao();
			navigator = menuDao.selectByParentId("0");
			
			if(navigator == null) {
				navigator = new ArrayList<MenuItem>();
			}
			
			getRequest().setAttribute("navigator", navigator);

			return "index";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginGet() {
		
		return "login";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public String loginPOST(String username, String password) {
		if(StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)) {
			return "error_login_nullInput";
		}
		
		UserDao userDao = new UserDao();
		UserItem user = userDao.selectByUserName(username);
		
		if(user == null) {
			return "error_login_user_not_exists";
		}
		
		if(!user.getPassword().equals(MyUtils.getMD5(password))) {
			return "error_login_password_wrong";
		}
		
		getRequest().getSession().setAttribute(Global.SessionKey_LoginUser, user);
		
		return SUCCESS;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	@ResponseBody
	public String logout() {
		HttpServletRequest request = getRequest();

		UserItem user = (UserItem)request.getSession().getAttribute(Global.SessionKey_LoginUser);
		if(user == null) {
			return SUCCESS;
		}
		
		try{
			request.getSession().removeAttribute(Global.SessionKey_LoginUser);
		}catch(Exception e) {
			Log.printStackTrace(e);
			return FAIL;
		}
		
		request.getSession().invalidate();
		
		return SUCCESS;
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
