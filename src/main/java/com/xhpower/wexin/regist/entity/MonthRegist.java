package com.xhpower.wexin.regist.entity;

public class MonthRegist {
	/**
	 * <p>
	 * 注册量返回统计量的实体
	 * </p>
	 *
	 * @author Song BoLin
	 * @since 2017-10-19
	 */
	
	//月份列表
 private Integer months;
 //每月的注册量
 private Integer count;
public Integer getMonths() {
	return months;
}
public void setMonths(Integer months) {
	this.months = months;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
 
}
