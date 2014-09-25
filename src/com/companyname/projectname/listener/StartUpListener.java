package com.companyname.projectname.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.opt.ThreadUtils;
import com.companyname.projectname.schedule.LogClearJob;
import com.companyname.projectname.schedule.ScheduleManager;

/**
 * @Author weinianjie
 * @Date 2014-7-6
 * @Time 下午6:37:09
 */
public class StartUpListener implements ServletContextListener {
	
	ScheduleManager scheManager = new ScheduleManager();
	
	// 启动执行
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try{
			// 把proxool配置载入环境
			try{
				Properties dbProps = new Properties();
				dbProps.load(StartUpListener.class.getResourceAsStream("/datasource.properties"));
				PropertyConfigurator.configure(dbProps);
			}catch(Exception e) {
				Log.printStackTrace(e);
			}
			
			// 给shell目录添加权限
//			try {
//				String[] cmd = new String[] { "/bin/sh", "-c", " chmod 777 " + DirDefine.ShellDir + "/* " };
//				Process ps = Runtime.getRuntime().exec(cmd);
//				ps.waitFor();
//			} catch (Exception e) {
//				Log.printStackTrace(e);
//			}			
			
			// 启动日志清理线程
			scheManager.startCron(new LogClearJob());
			
			// 启动TCP服务监听线程
	//		new Thread(new TcpServer(), "---TcpServer").start();
		}catch(Exception e) {
			Log.printStackTrace(e);
		}finally{
			ThreadUtils.closeAll();
		}
	}
	
	// 销毁执行
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			
			// 手工销毁quartz框架防止tomcat关不住
			Log.info("------:destroy quartz framework----");
			SchedulerFactory factory = new StdSchedulerFactory();
			Scheduler scheduler = factory.getScheduler();
            scheduler.shutdown(true);
            Thread.sleep(100);// Sleep for a bit so that we don't get any errors
        } catch (Exception e){
            Log.printStackTrace(e);
        }
	}

}
