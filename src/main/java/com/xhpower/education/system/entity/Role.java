package com.xhpower.education.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
* @ClassName: Role 
* @Description: 角色实体
* @author lisf 
* @date 2017年4月28日 下午4:10:38 
*
 */
@TableName(value = "np_role")
public class Role  extends Model<Role> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3204371223050107573L;

	private Integer id;
	private String name;
	private String description;
	@TableField("create_time")
	private Date createTime;
	@TableField(exist=false)
	private Set<Permission> permissions;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	
	
	
}
