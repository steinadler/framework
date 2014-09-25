package com.santrong.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-9
 * @Time 下午5:20:01
 */
@SuppressWarnings("serial")
public class UserAction extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log.debug("----xxx123");
		response.sendRedirect("http://www.baidu.com");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}	
}
