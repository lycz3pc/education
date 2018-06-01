package com.xhpower.wexin.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 支付记录表
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
@TableName("np_wx_pay_order_record_t")
public class PayOrderRecordT extends Model<PayOrderRecordT> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 支付金额
     */
    private BigDecimal price;

    /**
     * 0未支付 1已支付
     */
    private Integer status;

    /**
     * 1购买课程 2参与活动 3购买书籍
     */
    private Integer source;

    /**
     * 1微信支付 2支付宝 3贝壳支付
     */
    @TableField("pay_type")
    private Integer payType;

    /**
     * 第三方支付平台返回的流水号
     */
    @TableField("serial_number")
    private String serialNumber;

    /**
     * 支付时间
     */
    @TableField("pay_date")
    private Integer payDate;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Integer createDate;

    /**
     * 修改时间
     */
    @TableField("update_date")
    private Integer updateDate;

    /**
     * 订单收货地址ID
     */
    @TableField("address_id")
    private Integer addressId;

    /**
     * 发货状态，1已发，0未发
     */
    @TableField("ship_status")
    private Integer shipStatus;

    @TableField("waybill_no")
    private String waybillNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getPayDate() {
        return payDate;
    }

    public void setPayDate(Integer payDate) {
        this.payDate = payDate;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Integer updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(Integer shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
