package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * QQ客服
 *
 * @author Lian YouJie
 * @since 2017-11-03
 */
@TableName("np_customer_service")
public class NpCustomerService extends Model<NpCustomerService> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * QQ客服号码
     */
    private String qq;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态
     */
    @TableField("status_value")
    private String statusValue;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime = new Date();

    public NpCustomerService() {}

    public NpCustomerService(Integer status, String statusValue) {
        this.status = status;
        this.statusValue = statusValue;
    }

    public NpCustomerService(Integer id, Integer status, String statusValue) {
        this.id = id;
        this.status = status;
        this.statusValue = statusValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
