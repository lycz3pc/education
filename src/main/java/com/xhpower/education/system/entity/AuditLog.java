package com.xhpower.education.system.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("auditlog")
public class AuditLog {

	private int id;
	private String userid;//审核人
	private String audituser;//审核对象
	private String auditresult;//审核结果
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAudituser() {
		return audituser;
	}
	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}
	public String getAuditresult() {
		return auditresult;
	}
	public void setAuditresult(String auditresult) {
		this.auditresult = auditresult;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
	
	
}
