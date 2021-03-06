package com.xhpower.education.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

public class Region implements Serializable {
	
	
    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.no
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.parentno
     *
     * @mbggenerated
     */
    private String parentid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.shortname
     *
     * @mbggenerated
     */
    private String shortname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.leveltype
     *
     * @mbggenerated
     */
    private Byte leveltype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.citycode
     *
     * @mbggenerated
     */
    private String citycode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.zipcode
     *
     * @mbggenerated
     */
    private String zipcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.pinyin
     *
     * @mbggenerated
     */
    private String pinyin;
    
    @TableField(exist=false)
    private List<Region> children;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column region.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    private String noSubmitcount;
    private String noNoauditcount;
    
    @TableField(exist=false)
    private String parentname;

    
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

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

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Byte getLeveltype() {
		return leveltype;
	}

	public void setLeveltype(Byte leveltype) {
		this.leveltype = leveltype;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<Region> getChildren() {
		return children;
	}

	public void setChildren(List<Region> children) {
		this.children = children;
	}

	public String getNoSubmitcount() {
		return noSubmitcount;
	}

	public void setNoSubmitcount(String noSubmitcount) {
		this.noSubmitcount = noSubmitcount;
	}

	public String getNoNoauditcount() {
		return noNoauditcount;
	}

	public void setNoNoauditcount(String noNoauditcount) {
		this.noNoauditcount = noNoauditcount;
	}

}