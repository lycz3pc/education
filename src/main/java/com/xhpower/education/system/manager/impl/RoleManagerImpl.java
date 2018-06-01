package com.xhpower.education.system.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.RoleMapper;
import com.xhpower.education.system.entity.Role;
import com.xhpower.education.system.entity.RolePermission;
import com.xhpower.education.system.entity.RoleVo;
import com.xhpower.education.system.manager.RoleManager;
import com.xhpower.education.system.manager.RolePermissionManager;

@Service("roleManager")
@Transactional
public class RoleManagerImpl extends ServiceImpl<RoleMapper,Role> implements RoleManager {
	
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleManager  roleManager;

	@Autowired
	private RolePermissionManager rolePermissionManager;
	
	@Override
	public int deleteRolePermissionByRoleId(Integer roleId) {

		return roleMapper.deleteRolePermissionByRoleId(roleId);
	}

	@Override
	public Set<Role> selectByUsername(String userName) {

		return roleMapper.selectByUsername(userName);
	}

	@Override
	public int insertRolePermission(Role role) {

		return roleMapper.insertRolePermission(role);
	}

	@Override
	public boolean delRole(Integer id) {
		roleManager.deleteRoleAdminByRoleId(id);
		roleManager.deleteRolePermissionByRoleId(id);
	return 	roleManager.deleteById(id);
	}


	@Override
	public void addRole(RoleVo roleVo) {
		Role role =new Role();
		role.setName(roleVo.getName());
		role.setCreateTime(new Date());
		roleManager.insert(role);
		for (int i = 0; i < roleVo.getChkvalue().size(); i++) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(role.getId());
			rolePermission.setPermissionId(roleVo.getChkvalue().get(i));
			rolePermissionManager.insert(rolePermission);
		}
	}

	@Override
	public void updRole(RoleVo roleVo) {
		Role role =new Role();
		role.setId(roleVo.getId());
		role.setName(roleVo.getName());
		roleManager.updateById(role);
		roleManager.deleteRolePermissionByRoleId(role.getId());
		for (int i = 0; i < roleVo.getChkvalue().size(); i++) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(role.getId());
			rolePermission.setPermissionId(roleVo.getChkvalue().get(i));
			rolePermissionManager.insert(rolePermission);
		}
	}

	@Override
	public int deleteRoleAdminByRoleId(Integer roleId) {

		return roleMapper.deleteRoleAdminByRoleId(roleId);
	}

	@Override
	public List<Role> list() {

		return roleMapper.list();
	}


}
