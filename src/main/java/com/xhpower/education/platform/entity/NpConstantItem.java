
/** 
* @Title: NpConstantItem.java
* @Package: com.xhpower.education.platform.entity
* @Description: 新父母学校常量项 
* @Author: xiong li
* @Date: 2017-09-30
*
*/
package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
* @Databasetable: np_constant_item
* @Description: 新父母学校常量项
* @Author: xiong li
* @Date: 2017-09-30
* @Version: V1.0  
*
*/ 
@TableName("np_constant_item")
public class NpConstantItem  implements Serializable {
    /** 
	* @Fields serialVersionUID : serialVersionUID
	*/ 
	private static final long serialVersionUID = 7814417552909241413L;

	/**
    * @DatabasetableColumnName: np_constant_item.constant_id
    * @Description: 常量项id
     */
	@TableId("constant_id")
    private Long constantId;

    /**
    * @DatabasetableColumnName: np_constant_item.category_id
    * @Description: 类型id
     */
	@TableField("category_id")
    private String categoryId;

    /**
    * @DatabasetableColumnName: np_constant_item.constant_name
    * @Description: 常量名称
     */
	@TableField("category_name")
    private String categoryName;

    /**
    * @DatabasetableColumnName: np_constant_item.constant_value
    * @Description: 常量值
     */
	@TableField("constant_value")
    private String constantValue;

    /**
    * @DatabasetableColumnName: np_constant_item.creator_id
    * @Description: 创建人ID
     */
	@TableField("creator_id")
    private String creatorId;

    /**
    * @DatabasetableColumnName: np_constant_item.create_time
    * @Description: 创建时间
     */
	@TableField("create_time")
    private Date createTime;

    /**
    * @DatabasetableColumnName: np_constant_item.last_modify_by
    * @Description: 最后修改人
     */
	@TableField("last_modify_by")
    private String lastModifyBy;

    /**
    * @DatabasetableColumnName: np_constant_item.last_modify_time
    * @Description: 最后修改时间
     */
	@TableField("last_modify_time")
    private Date lastModifyTime;

    /**
    * @DatabasetableColumnName: np_constant_item.sort_no
    * @Description: 排序
     */
	@TableField("sort_no")
    private Integer sortNo;

    public Long getConstantId() {
        return constantId;
    }

    public void setConstantId(Long constantId) {
        this.constantId = constantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(String constantValue) {
        this.constantValue = constantValue == null ? null : constantValue.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy == null ? null : lastModifyBy.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}