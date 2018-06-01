package com.xhpower.education.platform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 学术成果专家表
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-09
 */
@TableName("np_cms_expert")
public class CmsExpertEntity extends Model<CmsExpertEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 专业领域
     */
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
