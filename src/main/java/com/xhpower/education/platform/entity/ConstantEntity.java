package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @ClassName: ConstantEntity
 * @Description: 学龄实体类
 * @author Liuyoucheng
 * @date 2017年9月29日 上午11:11:55
 *
 */
@TableName("sys_constant_item")
public class ConstantEntity extends Model<ConstantEntity> {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * id
     */
    @TableId(value = "constant_id", type = IdType.AUTO)
	private String id;
	
	private String category_id;
	
	private String constant_name;
	
	private String constant_value;
	
	private String parent_ids;
	
	private Boolean is_read_only;
	
	@TableField(exist=false)
	private List<ConstantEntity> chirdList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getConstant_name() {
		return constant_name;
	}

	public void setConstant_name(String constant_name) {
		this.constant_name = constant_name;
	}

	public String getConstant_value() {
		return constant_value;
	}

	public void setConstant_value(String constant_value) {
		this.constant_value = constant_value;
	}

	public String getParent_ids() {
		return parent_ids;
	}

	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}

	public Boolean getIs_read_only() {
		return is_read_only;
	}

	public void setIs_read_only(Boolean is_read_only) {
		this.is_read_only = is_read_only;
	}

	public List<ConstantEntity> getChirdList() {
		return chirdList;
	}

	public void setChirdList(List<ConstantEntity> chirdList) {
		this.chirdList = chirdList;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
}
