package com.xhpower.education.system.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.AdminMapper;
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.system.entity.Role;
import com.xhpower.education.system.manager.AdminManager;
import com.xhpower.education.system.manager.PermissionManager;
import com.xhpower.education.system.manager.RoleManager;
import com.xhpower.plugins.common.utils.StringHelper;

/**
 * @ClassName: AdminManagerImpl
 * @Description: 系统用户管理实现
 * @author root
 * @date 2016年12月10日 下午5:05:26
 * 
 */
@Service
@Transactional
public class AdminManagerImpl extends ServiceImpl<AdminMapper, Admin> implements AdminManager {
	@Autowired
	private AdminMapper			adminMapper;
	@Autowired
	private RoleManager			roleManager;
	@Autowired
	private PermissionManager	permissionManager;

	@Autowired
	private AdminManager adminManager;

	@Override
	public int updateByPrimaryKey(Admin admin) {
		return adminMapper.updateByPrimaryKey( admin );
	}

	@Override
	public Page<Admin> list(Page<Admin> page) {
		page.setRecords( adminMapper.list( page ) );
		return page;
	}

	@Override
	public SimpleAuthorizationInfo getAuthorizationInfo(String username) {
		Set<Role> roleSet = roleManager.selectByUsername( username );
		// 角色名的集合
		Set<String> roles = new HashSet<String>();
		// 权限名的集合
		Set<String> permissions = new HashSet<String>();
		Iterator<Role> it = roleSet.iterator();
		while (it.hasNext()) {
			Role role = it.next();
			roles.add( role.getName() );

			List<Permission> permissionSet = permissionManager.selectByRoleIds( Arrays.asList( role.getId() ) );

			for (Permission per : permissionSet) {
				if (!StringHelper.isStringEmpty( per.getPermission() )) {
					permissions.add( per.getPermission() );
				}
			}
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRoles( roles );
		authorizationInfo.addStringPermissions( permissions );
		return authorizationInfo;
	}

	@Override
	public List<Permission> getMenuList(String username) {
		Set<Role> roleSet = roleManager.selectByUsername( username );

		// 角色名的集合
		Set<String> roles = new HashSet<String>();
		// 权限名的集合

		Iterator<Role> it = roleSet.iterator();
		List<Integer> roleIds = new ArrayList<>();
		while (it.hasNext()) {
			Role role = it.next();
			roles.add( role.getName() );
			roleIds.add( role.getId() );
		}
		// 获取一级类目
		List<Permission> permissionSet = permissionManager.selectByRoleIds( roleIds );
		/*
		 * //获取二级类目 List<Permission>
		 * children=permissionManager.selectChildren(roleIds); //组合 for
		 * (Permission permission : permissionSet) { List<Permission>
		 * permissions = new ArrayList<Permission>(); for (Permission ch :
		 * children) { if(ch.getParentId().equals( permission.getId() )){
		 * permissions.add( ch ); } } permission.setChildren( permissions ); }
		 */
		return permissionSet;
	}

	@Override
	public int delectAdminRoleByAdminId(Long id) {

		return adminMapper.delectAdminRoleByAdminId( id );
	}

	@Override
	public boolean delectAdmin(Long id) {
		adminManager.delectAdminRoleByAdminId(id);
		return 	adminManager.deleteById(id);
	}

	@Override
	public int updateAdmin(Admin admin, Set<Role> roles) {

		// 更新关系表
		adminMapper.delectAdminRoleByAdminId( admin.getId() );
		if (roles.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put( "adminId", admin.getId() );
			map.put( "roles", roles );
			adminMapper.insertAdminRole( map );
		}
		return adminMapper.updateByPrimaryKey( admin );
	}

	@Override
	public List<Permission> getMenusByPid(String username, String pid) {
		// TODO Auto-generated method stub
		Set<Role> roleSet = roleManager.selectByUsername( username );
		// 角色名的集合
		Set<String> roles = new HashSet<String>();
		// 权限名的集合
		Iterator<Role> it = roleSet.iterator();
		List<Integer> roleIds = new ArrayList<>();
		while (it.hasNext()) {
			Role role = it.next();
			roles.add( role.getName() );
			roleIds.add( role.getId() );
		}
		// 获取一级类目
		Map<String, Object> map = new HashMap<>();
		map.put( "parentid", pid );
		map.put( "roleids", StringUtils.join( roleIds, "," ) );
		List<Permission> permissionSet = permissionManager.selectChildren( map );
		// 组合

		for (Permission permission : permissionSet) {
		//	List<Permission> permissions = new ArrayList<Permission>();
			map.put( "parentid", permission.getId() );
			map.put( "roleids", StringUtils.join( roleIds, "," ) );
			// 获取二级类目
			List<Permission> children = permissionManager.selectChildren( map );
			permission.setChildren( children );
		}
		return permissionSet;
	}

}
