package com.xhpower.wexin.banner.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.xhpower.education.system.entity.Resources;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * banner
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-17
 */
@TableName("np_banner")
public class NpBanner extends Model<NpBanner> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 链接
     */
	private String url;
    /**
     * 标题
     */
	private String title;
    /**
     * 位置
     */
	private String location;
    /**
     * 图片
     */
	private String img;
    /**
     * (1:上架、0:下架)
     */
	@TableField("up_down")
	private String upDown;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 类型
     */
	private Integer type;
    /**
     * 来源文本
     */
	@TableField("location_text")
	private String locationtext;
    /**
     * banner类别
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 链接方式
     */
	@TableField("url_type")
	private String urlType;
    /**
     * banner类型名称
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 0PC 1公众号
     */
	private Integer source;
	
	/**
	 * 外部表主键ID
	 */
	private String source_id;
	
	/**
	 * 1跳转链接 2跳转课程详情 3跳转活动详情 4跳转热文详情
	 */
	private String click_type;
	
	


	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	public String getClick_type() {
		return click_type;
	}

	public void setClick_type(String click_type) {
		this.click_type = click_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLocationtext() {
		return locationtext;
	}

	public void setLocationtext(String locationtext) {
		this.locationtext = locationtext;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Object createTime) {
		this.createTime = (Date) createTime;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Object updateTime) {
		this.updateTime = (Date) updateTime;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
