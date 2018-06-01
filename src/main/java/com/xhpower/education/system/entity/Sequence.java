package com.xhpower.education.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 * <p>
 * 序列表
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-06-25
 */
public class Sequence extends Model<Sequence> {

    private static final long serialVersionUID = 1L;

    /**
     * 序列名称
     */
    @TableId("seq_name")
    private String seqName;

    /**
     * 当前值
     */
    @TableField("current_val")
    private Long currentVal;

    /**
     * 步长(跨度)
     */
    @TableField("increment_val")
    private Integer incrementVal;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public Long getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(Long currentVal) {
        this.currentVal = currentVal;
    }

    public Integer getIncrementVal() {
        return incrementVal;
    }

    public void setIncrementVal(Integer incrementVal) {
        this.incrementVal = incrementVal;
    }

    @Override
    protected Serializable pkVal() {
        return this.seqName;
    }

}
