package com.companyname.projectname.webpage.developer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.schedule.JobImpl;
import com.companyname.projectname.webpage.BaseAction;
import com.companyname.projectname.webpage.developer.entry.Dev_JobInfo;
import com.companyname.projectname.webpage.developer.entry.Dev_JobInfo.TriggerInfo;

/**
 * @author weinianjie
 * @date 2014年7月31日
 * @time 下午7:57:53
 */
@Controller
@RequestMapping("/dev")
public class DeveloperAction extends BaseAction{
	
	@RequestMapping("/index")
	public String index() {
		return "developer/devmain";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/job")
	public String job() {
		List<Dev_JobInfo> jobList = new ArrayList<Dev_JobInfo>();
		try{
			// 获取任务
			SchedulerFactory factory = new StdSchedulerFactory();
			Scheduler scheduler = factory.getScheduler();
			Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.groupEquals(JobImpl.BasicGroup));
			for(Iterator<JobKey> iter = jobs.iterator();iter.hasNext();) {
				JobKey jkey = iter.next();
				Dev_JobInfo item = new Dev_JobInfo();
				item.setJobName(jkey.getName());
				item.setJobGroupName(jkey.getGroup());
				
				// 获取任务里面的触发器
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jkey);
				for(int i=0;i<triggers.size();i++) {
					if(triggers.get(i) instanceof CronTrigger) {
						TriggerInfo tinfo = item.new TriggerInfo();
						CronTrigger trigger = (CronTrigger)triggers.get(i);
						tinfo.setTriggerName(trigger.getKey().getName());
						tinfo.setTriggerGroupName(trigger.getKey().getGroup());
						tinfo.setRuntime(trigger.getCronExpression());
						item.getTriggerList().add(tinfo);
					}
				}
				jobList.add(item);
			}
			
			// 判断运行状态
			for(JobExecutionContext jec :scheduler.getCurrentlyExecutingJobs()) {
				for(Dev_JobInfo jinfo : jobList) {
					if(jec.getJobDetail().getKey().getName().equals(jinfo.getJobName())) {
						jinfo.setStatus(1);
						break;
					}
				}
			}
		}catch(Exception e) {
			Log.printStackTrace(e);
		}
		getRequest().setAttribute("jobList", jobList);
		return "developer/job";
	}
	
	
	@RequestMapping("/html")
	public String html() {
		return "dev/html";
	}
}
