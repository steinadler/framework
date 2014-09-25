package com.companyname.projectname.webpage.demo.dao;

import java.sql.Connection;
import java.sql.Statement;

import com.companyname.projectname.log.Log;
import com.companyname.projectname.opt.ThreadUtils;
import com.companyname.projectname.webpage.BaseDao;
import com.companyname.projectname.webpage.demo.entry.DemoForm;
import com.companyname.projectname.webpage.demo.entry.DemoView;
import com.companyname.projectname.webpage.demo.mapper.DemoMapper;

/**
 * @Author weinianjie
 * @Date 2014-7-4
 * @Time 下午10:30:58
 */

// 所有方法都不需要关闭数据库连接，HTTP请求线程结束后自动关闭
public class DemoDao extends BaseDao{
	
	/**
	 * 纯jdbc方式
	 */
	public void jdbc(){
		
		String sql = "insert into demo values('jdbc', '3')";
		
		try{
			
			Connection conn = ThreadUtils.currentConnection();
			Statement stm = conn.createStatement();
			stm.executeUpdate(sql);
//			stm.execute(arg0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用sql辅助工具，主要用于查询
	 * @return
	 */
	public String criteria() {
		
		String rt = null;
		
		try{
//			Connection conn = ThreadUtil.currentConnection();
//			Statement stm = conn.prepareStatement(sql);
//			stm.execute(arg0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rt;
	}
	
	/**
	 * jdbcTemplate方式
	 */
	public void template(){
		
		this.getJdbcTemplate().update("insert into demo values(?, ?)", new Object[]{"template", "1"});
		
	}

	
	/**
	 * 使用ibatis
	 */
	public DemoView ibatis() {
		
		DemoMapper mapper = this.getMapper(DemoMapper.class);
		DemoView view = mapper.getDemoById("123");
		if(view != null) {
			Log.debug("---view:field1" + view.getField1());
		}else{
			Log.debug("---view is null");
		}
		return view;
		
	}
	
	/**
	 * 使用ibatis
	 */
	public void ibatis2() {
		
		DemoMapper mapper = this.getMapper(DemoMapper.class);
		
		DemoForm form = new DemoForm();
		form.setId("ibatis");
		form.setField1("2");
		
		mapper.insert(form);
	}
	
}
