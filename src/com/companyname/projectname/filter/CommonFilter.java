package com.companyname.projectname.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.opt.ThreadUtils;
import com.companyname.projectname.system.Global;
import com.companyname.projectname.webpage.user.entry.UserItem;

/**
 * @Author weinianjie
 * @Date 2014-7-9
 * @Time 下午12:08:20
 */
public class CommonFilter implements Filter{

	private String[] passUrls;
	
	@Override
	public void destroy() {
		
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		ThreadUtils.setHttpRequest(request);
		ThreadUtils.setHttpResponse(response);
		
		String url = request.getRequestURI();
		if (url != null) {
			
			// 记录用户访问(过滤掉接口的请求)
			if(!url.startsWith("/http/basic.action")) {
				Log.logRequest(request);
			}
			
			boolean pass = false;
			
			// 是否是开发目录
			if(url.startsWith("/dev/")) {
				pass = true;
			}
			
			// 是否是配置的免登陆页面
			if (passUrls != null) {
				
				for (String suffix : passUrls) {
					if (url.endsWith(suffix)) {
						pass = true;
						break;
					}
				}
			}
			
			// 直接访问的jsp页面，除非配置在web.xml里，其余全部拦截
			// spring把视图移交给jsp页面时候经过servlet拦截，但是不经过filter拦截，所以不会导致无法移交视图
			if(!pass && url.endsWith(".jsp")) {
				response.sendRedirect("/login.action");
			}
//			Log.debug("-----------------:" + url);
			
			// 不是免登录的页面，开始校验登录状态
			if (!pass) {
				UserItem loginUser = (UserItem)request.getSession().getAttribute(Global.SessionKey_LoginUser);
				if(loginUser == null) {
					response.sendRedirect("/login.action");
				}
			}
		}
		
		chain.doFilter(req, resp);
		
		/*
		 * ---------以下是后置执行-------------
		 */
		
		ThreadUtils.closeAll();
	}


	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		String configPassUrls = fConfig.getInitParameter("passUrls");
		if (configPassUrls != null) {
			configPassUrls = configPassUrls.replaceAll("[\\s]", "");
			passUrls = configPassUrls.split(",");
		}
	}

}
