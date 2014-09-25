package com.santrong.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.santrong.log.Log;

/**
 * @Author weinianjie
 * @Date 2014-7-6
 * @Time 下午6:37:09
 */
public class CommonContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("xxxxxxxxxxxx");
		Log.info("------------Listener:" + Thread.currentThread());

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("yyyyyyyyyyyyy");

	}

}
