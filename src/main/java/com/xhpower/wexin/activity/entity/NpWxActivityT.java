package com.xhpower.wexin.activity.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.xhpower.education.system.entity.Resources;

import java.io.Serializable;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@TableName("np_wx_activity_t")
public class NpWxActivityT extends Model<NpWxActivityT> {

    private static final long serialVersionUID = 1L;

	@TableId(value="h_id", type= IdType.AUTO)
	private Integer hId;
    /**
     * 活动标题
     */
	@TableField("h_title")
	private String hTitle;
    /**
     * 活动内容
     */
	@TableField("h_content")
	private String hContent;
    /**
     * 活动开始时间
     */
	@TableField("start_date")
	private Integer startDate;
    /**
     * 活动详情
     */
	@TableField("detail")
	private String detail;
	
	/**
     * 活动结束时间
     */
	@TableField("end_date")
	private Integer endDate;
    /**
     * 活动类型(配置表主键ID)
     */
	@TableField("activity_type")
	private String activityType;
    /**
     * 文件资源表主键ID
     */
	@TableField("activity_source_id")
	private Integer activitySourceId;
    /**
     * 参与价格
     */
	@TableField("activity_price")
	private BigDecimal activityPrice;
    /**
     * 用户收藏数
     */
	@TableField("collection_num")
	private Integer collectionNum;
    /**
     * 参与人数
     */
	@TableField("partake_num")
	private Integer partakeNum;
    /**
     * 发布人用户ID
     */
	@TableField("batch_user_id")
	private Integer batchUserId;
    /**
     * 0正常 1已删除
     */
	@TableField("is_delete")
	private Integer isDelete;
	@TableField("create_date")
	private Integer createDate;
	@TableField("update_date")
	private Integer updateDate;
	
	/**
     * 活动状态
     */
	@TableField("activity_status")
	private Integer activityStatus;
	
	/**
	 * 文件资源表
	 */
	@TableField(exist = false)
	private Resources activityResources;


	public Integer getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	
	public Resources getActivityResources() {
		return activityResources;
	}

	public void setActivityResources(Resources activityResources) {
		this.activityResources = activityResources;
	}

	public Integer getHId() {
		return hId;
	}

	public void setHId(Integer hId) {
		this.hId = hId;
	}

	public String getHTitle() {
		return hTitle;
	}

	public void setHTitle(String hTitle) {
		this.hTitle = hTitle;
	}

	public String getHContent() {
		return hContent;
	}

	public void setHContent(String hContent) {
		this.hContent = hContent;
	}

	public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	 public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}
	
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getActivitySourceId() {
		return activitySourceId;
	}

	public void setActivitySourceId(Integer activitySourceId) {
		this.activitySourceId = activitySourceId;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public Integer getPartakeNum() {
		return partakeNum;
	}

	public void setPartakeNum(Integer partakeNum) {
		this.partakeNum = partakeNum;
	}

	public Integer getBatchUserId() {
		return batchUserId;
	}

	public void setBatchUserId(Integer batchUserId) {
		this.batchUserId = batchUserId;
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
		return this.hId;
	}

}
