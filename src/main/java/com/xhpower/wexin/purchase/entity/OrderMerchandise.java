package com.xhpower.wexin.purchase.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 订单-商品详细信息表
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
@TableName("np_wx_order_merchandise")
public class OrderMerchandise extends Model<OrderMerchandise> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商品ID，暂无
     */
	@TableField("merchandise_id")
	private Integer merchandiseId;
    /**
     * 商品名称
     */
	@TableField("merchandise_name")
	private String merchandiseName;
    /**
     * 订单ID
     */
	@TableField("order_id")
	private Integer orderId;
    /**
     * 创建人ID
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchandiseId() {
		return merchandiseId;
	}

	public void setMerchandiseId(Integer merchandiseId) {
		this.merchandiseId = merchandiseId;
	}

	public String getMerchandiseName() {
		return merchandiseName;
	}

	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
