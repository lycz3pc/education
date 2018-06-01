package com.xhpower.education.system.manager;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Role;
import com.xhpower.education.system.entity.RoleVo;

public interface RoleManager extends IService<Role>{
	
	public int deleteRolePermissionByRoleId(Integer roleId);

	public int deleteRoleAdminByRoleId(Integer roleId);

	public Set<Role> selectByUsername(String userName);

	public int insertRolePermission(Role role);

	public boolean delRole(Integer id);

	public void addRole(RoleVo roleVo);

	public void updRole(RoleVo roleVo);

	public List<Role> list();

}
