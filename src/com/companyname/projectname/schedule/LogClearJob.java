package com.companyname.projectname.schedule;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.opt.ThreadUtils;
import com.companyname.projectname.system.DirDefine;

/**
 * @author weinianjie
 * @date 2014年8月13日
 * @time 下午2:14:28
 */
public class LogClearJob extends JobImpl {
	
	@Override
	public String getJobName() {
		return "LogClear";
	}

	@Override
	public String getTriggerName() {
		return "LogClearTrigger";
	}

	@Override
	public String getCronExp() {
		return "0 10 0 * * ?";// 每天00:10:00执行
	}	
	
	@Override
	public Date getDateTime() {
		return null;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		String[] cmd = new String[] { "/bin/sh", "-c", " " + DirDefine.ShellDir + "/clearLog.sh " };
		
		try {
			Process ps = Runtime.getRuntime().exec(cmd);
			
			int psrt = ps.waitFor();
			if (psrt == 0) {
				Log.debug("----------- clear log files success");
			} else {
				Log.info("shell return " + psrt);
				Log.debug("----------- clear log files fail");
			}
			
		} catch (Exception e) {
			Log.printStackTrace(e);
		}finally{
			// 在quartz里面线程永远不会结束，需要手动关闭数据库连接
			ThreadUtils.closeAll();
		}				
		
	}
}
