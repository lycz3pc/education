package com.xhpower.wexin.activityPartake.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 活动参与记录表
 * </p>
 *
 * @author Song BoLin
 * @since 2017-11-09
 */
@TableName("np_wx_activity_partake_t")
public class NpWxActivityPartakeT extends Model<NpWxActivityPartakeT> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动表主键ID
     */
	@TableField("activity_id")
	private Integer activityId;
    /**
     * 0待支付 1参与成功
     */
	private Integer status;
    /**
     * 支付记录表主键ID
     */
	@TableField("order_id")
	private Integer orderId;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 参与人
     */
	@TableField("user_name")
	private String userName;
    /**
     * 联系电话
     */
	@TableField("user_phone")
	private String userPhone;
    /**
     * 参与时间
     */
	@TableField("create_date")
	private Integer createDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Integer createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
