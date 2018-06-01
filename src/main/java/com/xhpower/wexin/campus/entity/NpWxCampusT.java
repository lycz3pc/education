package com.xhpower.wexin.campus.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.xhpower.education.system.entity.Resources;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 校区表
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@TableName("np_wx_campus_t")
public class NpWxCampusT extends Model<NpWxCampusT> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 校区名称
     */
	@TableField("campus_name")
	private String campusName;
    /**
     * 联系电话
     */
	@TableField("tel_phone")
	private String telPhone;
    /**
     * 联系人
     */
	@TableField("user_name")
	private String userName;
    /**
     * 校区地址
     */
	@TableField("contact_addr")
	private String contactAddr;
    /**
     * 注册量
     */
	@TableField("register_num")
	private Integer registerNum;
    /**
     * 二维码  (文件资源表主键ID)
     */
	@TableField("qrcode_source_id")
	private Integer qrcodeSourceId;
    /**
     * 0正常 1删除
     */
	@TableField("is_delete")
	private Integer isDelete;
	@TableField("create_date")
	private Integer createDate;
	@TableField("update_date")
	private Integer updateDate;
	
	@TableField(exist = false)
	private Resources qrCodeResources;

	@TableField(exist = false)
	private String qrImgUrl;

	public String getQrImgUrl() {
		return qrImgUrl;
	}

	public void setQrImgUrl(String qrImgUrl) {
		this.qrImgUrl = qrImgUrl;
	}

	public Resources getQrCodeResources() {
		return qrCodeResources;
	}

	public void setQrCodeResources(Resources qrCodeResources) {
		this.qrCodeResources = qrCodeResources;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public Integer getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}

	public Integer getQrcodeSourceId() {
		return qrcodeSourceId;
	}

	public void setQrcodeSourceId(Integer qrcodeSourceId) {
		this.qrcodeSourceId = qrcodeSourceId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
