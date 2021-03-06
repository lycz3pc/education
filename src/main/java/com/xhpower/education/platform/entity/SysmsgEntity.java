package com.xhpower.education.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("np_sysmsg")
public class SysmsgEntity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.userid
     *
     * @mbggenerated
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.msg
     *
     * @mbggenerated
     */
    private String msg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.from
     *
     * @mbggenerated
     */
    private Integer from;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.createtime
     *
     * @mbggenerated
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sysmsg.updatetime
     *
     * @mbggenerated
     */
    @TableField("update_time")
    private Date updateTime;
    
    /**对象id路径(逗号分隔，第一个为问题id，最后一个为发送消息的问题回复id)*/
    @TableField("object_paths")
	private String objectPaths;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.id
     *
     * @return the value of sysmsg.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.id
     *
     * @param id the value for sysmsg.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.userid
     *
     * @return the value of sysmsg.userid
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.userid
     *
     * @param userid the value for sysmsg.userid
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.msg
     *
     * @return the value of sysmsg.msg
     *
     * @mbggenerated
     */
    public String getMsg() {
        return msg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.msg
     *
     * @param msg the value for sysmsg.msg
     *
     * @mbggenerated
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.from
     *
     * @return the value of sysmsg.from
     *
     * @mbggenerated
     */
    public Integer getFrom() {
        return from;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.from
     *
     * @param from the value for sysmsg.from
     *
     * @mbggenerated
     */
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.status
     *
     * @return the value of sysmsg.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.status
     *
     * @param status the value for sysmsg.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.create_time
     *
     * @return the value of sysmsg.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.create_time
     *
     * @param createtime the value for sysmsg.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sysmsg.update_time
     *
     * @return the value of sysmsg.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sysmsg.update_time
     *
     * @param updatetime the value for sysmsg.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getObjectPaths() {
		return objectPaths;
	}

	public void setObjectPaths(String objectPaths) {
		this.objectPaths = objectPaths;
	}
	
}