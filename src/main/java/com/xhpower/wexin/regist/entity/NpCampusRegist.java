package com.xhpower.wexin.regist.entity;

public class NpCampusRegist {
	/**
	 * <p>
	 * 注册量返回统计量的实体
	 * </p>
	 *
	 * @author Song BoLin
	 * @since 2017-10-19
	 */
	//校区列表
	private String campus;
	//每个校区的注册量
	private Integer count;
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
