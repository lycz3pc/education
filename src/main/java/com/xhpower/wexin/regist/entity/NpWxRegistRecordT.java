package com.xhpower.wexin.regist.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 通过校区二维码注册记录
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-23
 */
@TableName("np_wx_regist_record_t")
public class NpWxRegistRecordT extends Model<NpWxRegistRecordT> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 校区记录表主键ID
     */
	@TableField("campus_id")
	private Integer campusId;
	@TableField("campus_name")
	private String campusName;
	@TableField("regist_user_id")
	private Integer registUserId;
    /**
     * 0公众号自行注册 1通过校区推广二维码注册 2通过个人分享连接注册
     */
	private Integer source;
	@TableField("create_date")
	private Integer createDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public Integer getRegistUserId() {
		return registUserId;
	}

	public void setRegistUserId(Integer registUserId) {
		this.registUserId = registUserId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
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
