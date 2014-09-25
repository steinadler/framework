package com.companyname.projectname.webpage.developer.entry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weinianjie
 * @date 2014年8月25日
 * @time 下午5:00:59
 */
public class Dev_JobInfo {
	private String jobName;
	private String jobGroupName;
	private int status;
	private List<TriggerInfo> triggerList = new ArrayList<TriggerInfo>();
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<TriggerInfo> getTriggerList() {
		return triggerList;
	}

	public void setTriggerList(List<TriggerInfo> triggerList) {
		this.triggerList = triggerList;
	}

	public class TriggerInfo {
		private String triggerName;
		private String triggerGroupName;
		private String runtime;
		public String getTriggerName() {
			return triggerName;
		}
		public void setTriggerName(String triggerName) {
			this.triggerName = triggerName;
		}
		public String getTriggerGroupName() {
			return triggerGroupName;
		}
		public void setTriggerGroupName(String triggerGroupName) {
			this.triggerGroupName = triggerGroupName;
		}
		public String getRuntime() {
			return runtime;
		}
		public void setRuntime(String runtime) {
			this.runtime = runtime;
		}
	}
}
