
/** 
* @Title: Admin.java
* @Package: com.xhpower.waterutilities.system.entity
* @Description: TODO() 
* @Author: li shifeng
* @Date: 2016-12-21
*
*/
package com.xhpower.education.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
* @Databasetable: admin
* @Description: null
* @Author: li shifeng
* @Date: 2016-12-21
* @Version: V1.0  
*
*/ 
@TableName("np_admin")
public class Admin extends Model<Admin> implements Serializable{
    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -1195847222920707349L;

	/**
    * @DatabasetableColumnName: admin.id
    * @Description: null
     */
    private Long id;

    /**
    * @DatabasetableColumnName: admin.email
    * @Description: null
     */
    private String email;

    /**
    * @DatabasetableColumnName: admin.status
    * @Description: null
     */
    private Boolean status;

    /**
    * @DatabasetableColumnName: admin.password
    * @Description: null
     */
    private String password;

    /**
    * @DatabasetableColumnName: admin.phone
    * @Description: null
     */
    private String phone;

    /**
    * @DatabasetableColumnName: admin.username
    * @Description: null
     */
    private String username;

    /**
    * @DatabasetableColumnName: admin.name
    * @Description: null
     */
    private String name;

    /**
    * @DatabasetableColumnName: admin.start_time
    * @Description: null
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("start_time")
    private Date startTime;

    /**
    * @DatabasetableColumnName: admin.end_time
    * @Description: null
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("end_time")
    private Date endTime;

    @TableField("region_id")
    private String regionId;
    @TableField("user_type")
    private  String userType;
    @TableField("public_id")
    private String publicId;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	
}