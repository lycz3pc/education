package com.xhpower.education.api.core;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;


/**
 * 
* @ClassName: BasePageResponse 
* @Description: API 分页返回结果封装
* @author lisf 
* @date 2016年9月23日 下午3:56:29 
*
 */
public abstract class BasePageResponse extends APIBaseResponse{

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -6653946666189946791L;
	/**
	 * 总条数	 */
	protected int totalCount = 0;
	/**
	 * 集合长度
	 */
	protected int size = 0;
	/**
	 * 下一条
	 */
	protected int nextPage = 0;
	
	
	public BasePageResponse(Page<?> page) {
		setPage(page);
	}
	
	/**
	 * 设置分页对象
	 * 
	 * @param pagination
	 */
	public void setPage(Page<?> page) {
		totalCount = page.getTotal();
		size = page.getRecords().size();
		nextPage = page.hasNext()? 0 : page.getCurrent()+1;
		setList(page.getRecords());
	}
	
	/**
	 * 设置集合
	 * 
	 * @param list
	 */
	public abstract void setList(List<?> list);
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
