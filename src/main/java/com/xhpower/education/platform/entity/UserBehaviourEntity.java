package com.xhpower.education.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;


/**
* 用户关注、点赞、收藏等行为表
* @author xiong li
*/
@TableName("np_user_behaviour")
public class UserBehaviourEntity {
	
	/**用户行为id*/
	private Integer id;

	/**操作对象id*/
	@TableField("object_id")
	private String objectId;

	/**创建人id*/
	@TableField("user_id")
	private String userId;

	/**记录类型（1、点赞  2、关注 3、收藏））*/
	private String type;

	/**来源*/
	private String source;

	/**创建时间*/
	@TableField("create_time")
	private Date createTime;

	/**更新时间*/
	@TableField("update_time")
	private Date updateTime;


	/**
	* 取得select,insert,update,references
	* @return Integer
	*/
	public Integer getId() {
		return id;
	}

	/**
	* 设置select,insert,update,references
	* @param id
	*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* 设置select,insert,update,references
	* @param object_id
	*/
	public void setObjectId(String objectId){
		this.objectId=objectId;
	}

	/**
	* 取得select,insert,update,references
	* @return String
	*/
	public String getObjectId(){
		return objectId;
	}

	/**
	* 设置select,insert,update,references
	* @param user_id
	*/
	public void setUserId(String userId){
		this.userId=userId;
	}

	/**
	* 取得select,insert,update,references
	* @return String
	*/
	public String getUserId(){
		return userId;
	}

	/**
	* 设置select,insert,update,references
	* @param type
	*/
	public void setType(String type){
		this.type=type;
	}

	/**
	* 取得select,insert,update,references
	* @return String
	*/
	public String getType(){
		return type;
	}

	/**
	* 设置select,insert,update,references
	* @param source
	*/
	public void setSource(String source){
		this.source=source;
	}

	/**
	* 取得select,insert,update,references
	* @return String
	*/
	public String getSource(){
		return source;
	}

	/**
	* 设置select,insert,update,references
	* @param create_time
	*/
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	/**
	* 取得select,insert,update,references
	* @return Date
	*/
	public Date getCreateTime(){
		return createTime;
	}

	/**
	* 设置select,insert,update,references
	* @param update_time
	*/
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}

	/**
	* 取得select,insert,update,references
	* @return Date
	*/
	public Date getUpdateTime(){
		return updateTime;
	}
}
