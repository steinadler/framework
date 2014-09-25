package com.companyname.projectname.opt;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午3:04:18
 */
public class PageQuery {
	private int pageNum;
	private int count;
	private int pageSize = 16;
	private int prevInsert = 0; //前置插入混淆数据量
	
	private String orderBy = "cts";
	private String orderRule = "desc";	
	
	/*
	 * 获取limit的开头
	 */
	public int getLimitBegin() {
		if(this.pageNum != 0) {// 非第一页
			return pageNum * pageSize - prevInsert;
		}
		return pageNum * pageSize;
	}
	/*
	 * 获取limit的结尾
	 */
	public int getLimitEnd() {
		if(this.pageNum == 0) {// 第一页
			return pageSize - prevInsert;
		}
		return pageSize;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(String orderRule) {
		this.orderRule = orderRule;
	}

	public int getPrevInsert() {
		return prevInsert;
	}

	public void setPrevInsert(int prevInsert) {
		this.prevInsert = prevInsert;
	}
}
