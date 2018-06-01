package com.xhpower.education.common.page;

/**
 * 分页参数,可以作为输入参数
 *
 * @Author lisf
 */
public class Page {

	protected int pageNo = 1;
	protected int pageSize = 10;
	
	/**
	 * 
	 */
	public Page() {
		super();
	}

	/**
	 * @param pageNo
	 * @param pageSize
	 */
	public Page(int pageNo,int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}
}
