package com.companyname.projectname.webpage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.companyname.projectname.opt.ThreadUtils;
import com.companyname.projectname.system.Global;
import com.companyname.projectname.webpage.user.entry.UserItem;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午4:38:21
 */
public abstract class BaseAction {
	protected final String SUCCESS = "success";
	protected final String FAIL = "fail";
	protected final String ERROR_PARAM = "error_param";
	
	
	public final UserItem currentUser() {
		UserItem user = (UserItem)ThreadUtils.currentHttpRequest().getSession().getAttribute(Global.SessionKey_LoginUser);
		return user;
	}
	
	public final HttpServletRequest getRequest() {
		return ThreadUtils.currentHttpRequest();
	}
	
	public final HttpServletResponse getResponse() {
		return ThreadUtils.currentHttpResponse();
	}
}
