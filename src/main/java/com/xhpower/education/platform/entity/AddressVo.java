package com.xhpower.education.platform.entity;

import java.util.List;

public class AddressVo {

	private String name;
	private int id;
	private List<AddressVo> childList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<AddressVo> getChildList() {
		return childList;
	}
	public void setChildList(List<AddressVo> childList) {
		this.childList = childList;
	}
	
	
	
	
}
