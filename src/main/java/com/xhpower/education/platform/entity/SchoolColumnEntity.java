package com.xhpower.education.platform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * @ClassName: SchoolColumn
 * @Description: 学校-栏目对应表实体类
 * @author Lian Youjie
 * @date 2017年9月9日 下午3:36:09
 *
 */
@TableName("np_school_column")
public class SchoolColumnEntity extends Model<SchoolColumnEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 栏目名称
     */
    @TableField("school_id")
    private Integer schoolId;

    /**
     * 地址
     */
    @TableField("column_id")
    private Integer columnId;

    /**
     * 冗余字段，栏目名称
     */
    @TableField("column_text")
    private String columnText;

    /**
     * 冗余字段，栏目地址
     */
    @TableField("column_url")
    private String columnUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getColumnText() {
        return columnText;
    }

    public void setColumnText(String columnText) {
        this.columnText = columnText;
    }

    public String getColumnUrl() {
        return columnUrl;
    }

    public void setColumnUrl(String columnUrl) {
        this.columnUrl = columnUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
