package com.companyname.projectname.schedule;

import java.util.Date;

import org.quartz.Job;

/**
 * @author weinianjie
 * @date 2014年8月13日
 * @time 下午2:31:11
 */
public abstract class JobImpl implements Job {
	
	public static String BasicGroup = "BasicGroup";
	public static String BasicTriggerGroup = "BasicTriggerGroup";
	
	abstract String getJobName();
	
	public String getGroupName() {
		return BasicGroup;
	}
	
	abstract String getTriggerName();
	
	public String getTriggerGroupName() {
		return BasicTriggerGroup;
	}
	
	abstract String getCronExp();
	
	abstract Date getDateTime();
}
