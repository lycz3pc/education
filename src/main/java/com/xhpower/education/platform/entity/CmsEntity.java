package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.xhpower.education.system.entity.Resources;

/**
 * <p>
 * 内容发布
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-09-28
 */
@TableName("np_cms")
public class CmsEntity extends Model<CmsEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容类型
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subheading;

    /**
     * 封面图片resources
     */
    @TableField("cover_url")
    private Integer coverUrl;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件resources
     */
    private Integer accessory;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 来源
     */
    private String from;

    /**
     * 排序位置
     */
    private Integer position = 0;

    /**
     * 学术成果主题，或cms标签
     */
    private String theme;

    /**
     * 浏览量
     */
    private Integer browse;

    /**
     * 浏览量
     */
    private Integer news = 0;

    /**
     * 专家ID
     */
    private String expert;

    /**
     * 标签
     */
    private String tag;

    /**
     * 类型路径
     */
    @TableField("type_path")
    private String typePath;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private String tagId;

    /**
     * 封面资源
     */
    @TableField(exist = false)
    private Resources coverResources;

    /**
     * 附件资源
     */
    @TableField(exist = false)
    private Resources accessoryResources;

    public CmsEntity() {}

    public CmsEntity(Integer id, Integer position) {
        this.id = id;
        this.position = position;
        this.news = null;
    }

    public CmsEntity(Integer id, Integer news, String a) {
        this.id = id;
        this.news = news;
        this.position = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public Integer getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(Integer coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAccessory() {
        return accessory;
    }

    public void setAccessory(Integer accessory) {
        this.accessory = accessory;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Resources getCoverResources() {
        return coverResources;
    }

    public void setCoverResources(Resources coverResources) {
        this.coverResources = coverResources;
    }

    public Resources getAccessoryResources() {
        return accessoryResources;
    }

    public void setAccessoryResources(Resources accessoryResources) {
        this.accessoryResources = accessoryResources;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getBrowse() {
        return browse;
    }

    public void setBrowse(Integer browse) {
        this.browse = browse;
    }

    public Integer getNews() {
        return news;
    }

    public void setNews(Integer news) {
        this.news = news;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getTypePath() {
        return typePath;
    }

    public void setTypePath(String typePath) {
        this.typePath = typePath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
