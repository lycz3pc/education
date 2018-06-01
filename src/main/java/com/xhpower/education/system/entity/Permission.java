package com.xhpower.education.system.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @Databasetable: permission
 * @Description: 权限管理
 * @author: tang xiaobin
 * @Date: 2017-4-19
 * @Version: V1.0
 *
 */
@TableName("np_permission")
public class Permission extends Model<Permission> implements Serializable{

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -6897409821086847521L;

	public enum Type{
		/**
		 * @Fields dir : TODO 目录
		 */
		dir,
		/**
		 * @Fields menu : TODO 菜单
		 */
    	menu,
    	/**
		 * @Fields url : TODO （功能，按钮。。）
		 */
    	button
	}
	
	/**
	 * @DatabasetableColumnName: permission.id
	 * @Description: id
	 */
	private Long id;
	
	/**
	 * @DatabasetableColumnName: permission.name
	 * @Description: 权限名称 （/user/*）
	 */
	private String name;
	
	/**
	 * @DatabasetableColumnName: permission.text
	 * @Description:权限显示文本
	 */
	
	private String text;
	
	/**
	 * @DatabasetableColumnName: permission.url
	 * @Description: 资源路径（/user/save）
	 */
	private String url;
	
	/**
	 * @DatabasetableColumnName: permission.parentId
	 * @Description: 父ID
	 */
	@TableField("parent_id")
	private Long parentId;
	
	/**
	 * @DatabasetableColumnName: permission.priority
	 * @Description: 位置
	 */
	private Long priority;
	
	/**
	 * @DatabasetableColumnName: permission.type
	 * @Description: 权限类型
	 */
	private Type type;
	
	/**
	 * @DatabasetableColumnName: permission.permission
	 * @Description: 权限标示（user:add）
	 */
	private String permission;
	
	@TableField(exist=false)
	private List<Permission> children;
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text == null ? null : text.trim();
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}



	public Long getParentId() {
		return parentId;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	public Long getPriority() {
		return priority;
	}



	public void setPriority(Long priority) {
		this.priority = priority;
	}



	public Type getType() {
		return type;
	}



	public void setType(Type type) {
		this.type = type;
	}


	
	public String getPermission() {
		return permission;
	}



	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}

	public List<Permission> getChildren() {
		return children;
	}



	public void setChildren(List<Permission> children) {
		this.children = children;
	}



	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
