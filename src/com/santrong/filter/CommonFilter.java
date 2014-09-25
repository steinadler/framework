package com.santrong.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santrong.opt.ThreadUtil;
import com.santrong.system.Global;

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
		
		ThreadUtil.setHttpSession(request.getSession());
		
		//打个标记，表示该request曾经来过过滤器
		request.setAttribute("passFilter", 1);
		
		String url = request.getRequestURI();
		if (url != null) {
			boolean pass = false;
			
			// 永远不需要登录的界面
			if(url.endsWith("/login.action")){
				pass = true;
				
			}else{
				
				if(!Global.allLogin){// 全局定义为全部登录，则不用扫描了
					
					if (passUrls != null) {
						
						for (String suffix : passUrls) {
							if (url.endsWith(suffix)) {
								pass = true;
								break;
							}
						}
					}
				}
			}
			
			// 校验登录
			if (!pass) {
				//TODO 校验菜单权限
				if(passUrls == null) {
					response.sendRedirect("/login.do");
				}
			}
		}
		
		chain.doFilter(req, resp);
		
		/*
		 * ---------以下是后置执行-------------
		 */
		
		ThreadUtil.closeAll();
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
