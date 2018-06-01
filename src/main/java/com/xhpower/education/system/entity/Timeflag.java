package com.xhpower.education.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-06-25
 */
public class Timeflag extends Model<Timeflag> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    private Integer cid;

    /**
     * 最后登陆时间
     */
    private Date lasttime;

    /**
     * 类型，'1' 通知最后时间
     */
    private String type;

    public Timeflag() {}

    public Timeflag(Integer cid, String type) {
        this.cid = cid;
        this.lasttime = new Date();
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
