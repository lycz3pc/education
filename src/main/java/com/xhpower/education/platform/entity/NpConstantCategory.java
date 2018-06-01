
/** 
* @Title: NpConstantCategory.java
* @Package: com.xhpower.education.platform.entity
* @Description: 新父母学校常量类型 
* @Author: xiong li
* @Date: 2017-09-30
*
*/
package com.xhpower.education.platform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
* @Databasetable: np_constant_category
* @Description: 新父母学校常量类型 
* @Author: xiong li
* @Date: 2017-09-30
* @Version: V1.0  
*
*/ 
@TableName("np_constant_category")
public class NpConstantCategory  implements Serializable {
    /** 
	* @Fields serialVersionUID : serialVersionUID
	*/ 
	private static final long serialVersionUID = 1440252958583360369L;

	/**
    * @DatabasetableColumnName: np_constant_category.category_id
    * @Description: 类型id
     */
	@TableId("category_id")
    private Long categoryId;

    /**
    * @DatabasetableColumnName: np_constant_category.category_name
    * @Description: 类型名称
     */
	@TableField("category_name")
    private String categoryName;

    /**
    * @DatabasetableColumnName: np_constant_category.category_key
    * @Description: 类型键名
     */
	@TableField("category_key")
    private String categoryKey;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey == null ? null : categoryKey.trim();
    }
}