package com.xhpower.wexin.regist.entity;

public class NpRegistCount {
	/**
	 * <p>
	 * 注册量返回统计量的实体
	 * </p>
	 *
	 * @author Song BoLin
	 * @since 2017-10-19
	 */
	//以天的时间列表
	private int days;
	//每天的注册量
	private int count;
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
