package com.xhpower.education.platform.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 评论回答
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-11-08
 */
@TableName("np_answer")
public class AnswerEntity extends Model<AnswerEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 评论项
     */
	@TableField("questions_id")
	private String questionsId;
    /**
     * 评论人
     */
	@TableField("create_user")
	private Integer createUser;
	
	@TableField(exist=false)
    private String userName;
    /**
     * 父评论
     */
	@TableField("parent_id")
	private String parentId;
    /**
     * 内容
     */
	private String content;
    /**
     * 点赞数
     */
	private Integer praise;
    /**
     * 踩
     */
	private Integer unpraise;
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
     * 评论数
     */
	private Integer comments;
    /**
     * 来源
     */
	private String from;
    /**
     * 是否为专家回答（1：专家回答 0：家长回答）
     */
	@TableField("is_expert")
	private Integer isExpert;
	
	/**审核状态 0：未审核 1：已审核 2:审核不通过*/
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionsId() {
		return questionsId;
	}

	public void setQuestionsId(String questionsId) {
		this.questionsId = questionsId;
	}
	
	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getUnpraise() {
		return unpraise;
	}

	public void setUnpraise(Integer unpraise) {
		this.unpraise = unpraise;
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

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Integer getIsExpert() {
		return isExpert;
	}

	public void setIsExpert(Integer isExpert) {
		this.isExpert = isExpert;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
