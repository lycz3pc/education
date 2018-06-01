package com.xhpower.education.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 
 * @ClassName: Role
 * @Description: 角色关联权限
 * @author lisf
 * @date 2017年4月28日 下午4:10:38
 *
 */
@TableName(value = "np_role_permission")
public class RolePermission  extends Model<RolePermission> implements java.io.Serializable{
    private static final long serialVersionUID = -3204371223050107573L;

    public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	@TableField("role_id")
    private Integer roleId;

    @TableField("permission_id")
    private Integer permissionId;

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
