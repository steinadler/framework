package com.companyname.projectname.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.companyname.projectname.log.Log;

/**
 * @author weinianjie
 * @date 2014年8月13日
 * @time 下午2:24:23
 */
public class ScheduleManager {
	
	SchedulerFactory factory = new StdSchedulerFactory();
	
	/**
	 * 开启一个调度
	 * @param job
	 */
	public boolean startCron(JobImpl job) {
		try{
			if(job != null) {
				Log.info("************start job : " + job.getJobName() + " by cron [" + job.getCronExp() + "]************");
				
				Scheduler scheduler = factory.getScheduler();
				JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(job.getJobName(), job.getGroupName()).build();
				CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getTriggerName(), job.getTriggerGroupName()).withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExp())).build();//00:00:00
				scheduler.scheduleJob(jobDetail, trigger);
				scheduler.start();
				return true;
			}
		}catch(Exception e) {
			Log.printStackTrace(e);
		}
		return false;
	}
	
	/**
	 * 移除一个调度
	 * @param job
	 * @return
	 */
	public boolean removeCron(JobImpl job) {
		try{
			if(job != null) {
				Log.info("************stop job : " + job.getJobName() + " by cron [" + job.getCronExp() + "]************");
				
				Scheduler scheduler = factory.getScheduler();
				TriggerKey tKey = new TriggerKey(job.getTriggerName(), job.getTriggerGroupName());
				JobKey jKey = new JobKey(job.getJobName(), job.getGroupName());
				
				scheduler.pauseTrigger(tKey);//停止触发器  
				scheduler.unscheduleJob(tKey);//移除触发器  
				scheduler.deleteJob(jKey);//删除任务
				return true;
			}
		}catch(Exception e) {
			Log.printStackTrace(e);
		}
		return false;
	}
	
	/**
	 * 判断调度存在
	 * @param job
	 * @return
	 */
	public boolean existsCron(JobImpl job) {
		try{
			if(job != null) {
				Scheduler scheduler = factory.getScheduler();
				TriggerKey tKey = new TriggerKey(job.getTriggerName(), job.getTriggerGroupName());
				JobKey jKey = new JobKey(job.getJobName(), job.getGroupName());
				
				return scheduler.checkExists(jKey) && scheduler.checkExists(tKey);
			}
		}catch(Exception e) {
			Log.printStackTrace(e);
		}
		return false;
	}
	
}
