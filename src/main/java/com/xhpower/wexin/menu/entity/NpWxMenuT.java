package com.xhpower.wexin.menu.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 微信公众号导航栏记录表
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-30
 */
@TableName("np_wx_menu_t")
public class NpWxMenuT extends Model<NpWxMenuT> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 菜单名称
     */
	@TableField("name")
	private String name;
    /**
     * 跳转链接
     */
	private String url;
    /**
     * 上级ID
     */
	@TableField("parent_id")
	private Integer parentId;
    /**
     * view网页类型 click点击类型
     */
	private String type;
    /**
     * 0一级菜单 1二级菜单
     */
	private Integer level;
    /**
     * 菜单KEY值，用于消息接口推送
     */
	private String key;
	@TableField("create_date")
	private Integer createDate;
	@TableField("update_date")
	private Integer updateDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
