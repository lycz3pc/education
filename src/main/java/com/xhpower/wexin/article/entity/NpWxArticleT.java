package com.xhpower.wexin.article.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.xhpower.education.system.entity.Resources;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 热文表
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-17
 */
@TableName("np_wx_article_t")
public class NpWxArticleT extends Model<NpWxArticleT> {

    private static final long serialVersionUID = 1L;

	@TableId(value="r_id", type= IdType.AUTO)
	private Integer rId;
    /**
     * 热文标题
     */
	@TableField("r_title")
	private String rTitle;
    /**
     * 热文内容
     */
	@TableField("r_conten")
	private String rConten;
	
	 /**
     * 活动详情
     */
	@TableField("detail")
	private String detail;
	

	/**
     * 热文类型ID
     */
	@TableField("rewen_type_id")
	private Integer rewenTypeId;
	 /**
     * 文件资源表主键ID
     */
	@TableField("img_rsource_id")
	private Integer imgRsourceId;
    /**
     * 发布人
     */
	@TableField("batch_user_id")
	private Integer batchUserId;
    /**
     * 热文点击数
     */
	@TableField("browse_num")
	private Integer browseNum;
	
	 /**
     * 来源
     */
	@TableField("source")
	private String source;
	

	/**
     * 收藏数
     */
	@TableField("collection_num")
	private Integer collectionNum;
    /**
     * 0正常 1已删除
     */
	@TableField("is_delete")
	private Integer isDelete;
	@TableField("create_date")
	private Integer createDate;
	
	/**
     * 图片资源
     */
    @TableField(exist = false)
    private Resources coverResources;
	
	public Resources getCoverResources() {
		return coverResources;
	}

	public void setCoverResources(Resources coverResources) {
		this.coverResources = coverResources;
	}

	@TableField("update_date")
	private Integer updateDate;

	

	public Integer getRId() {
		return rId;
	}

	public void setRId(Integer rId) {
		this.rId = rId;
	}

	public String getRTitle() {
		return rTitle;
	}

	public void setRTitle(String rTitle) {
		this.rTitle = rTitle;
	}

	public String getRConten() {
		return rConten;
	}

	public void setRConten(String rConten) {
		this.rConten = rConten;
	}

	 public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}
	
	public Integer getRewenTypeId() {
		return rewenTypeId;
	}

	public void setRewenTypeId(Integer rewenTypeId) {
		this.rewenTypeId = rewenTypeId;
	}

	public Integer getBatchUserId() {
		return batchUserId;
	}

	public Integer getImgRsourceId() {
		return imgRsourceId;
	}

	public void setImgRsourceId(Integer imgRsourceId) {
		this.imgRsourceId = imgRsourceId;
	}

	public void setBatchUserId(Integer batchUserId) {
		this.batchUserId = batchUserId;
	}

	public Integer getBrowseNum() {
		return browseNum;
	}

	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
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
		return this.rId;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
