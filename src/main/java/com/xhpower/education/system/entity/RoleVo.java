package com.xhpower.education.system.entity;

import java.util.Date;
import java.util.List;

public class RoleVo {
	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private Integer current;
	private Integer size;
	private Integer id;
	private String name;
	private String description;
	private Date createTime;

	private List<Integer> chkvalue;

	public List<Integer> getChkvalue() {
		return chkvalue;
	}

	public void setChkvalue(List<Integer> chkvalue) {
		this.chkvalue = chkvalue;
	}
}
