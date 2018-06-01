package com.xhpower.education.system.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.PermissionMapper;
import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.system.manager.PermissionManager;

/** 
 * @ClassName: PermissionManagerImpl 
 * @Description: 系统权限管理实现
 * @author tangxiaobin 
 * @date 2017.4.19 
 *  
 */
@Service
@Transactional
public class PermissionManagerImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionManager{
	
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public Permission selectByPId(Long id) {
		return permissionMapper.selectByPId(id);
	}

	@Override
	public List<Permission> list(Long id) {
		
		return permissionMapper.list(id);
	}

	@Override
	public int deleteRolePermissionByPerId(Long permissionId) {
		
		return permissionMapper.deleteRolePermissionByPerId(permissionId);
	}

	@Override
	public List<Permission> selectByRoleIds(List<Integer> roleIds) {
		if(null == roleIds || roleIds.size() == 0){
			return new ArrayList<Permission>();
		}
		
		return permissionMapper.selectByRoleIds(roleIds);
	}

	@Override
	public List<Permission> selectChildren(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return permissionMapper.selectChildren (map );
	}

	@Override
	public Boolean deletePerAndChildren(Integer id) {
		//存在子权限时不能删除
		EntityWrapper<Permission> deptWrapper = new EntityWrapper<Permission>();
		List<Permission> deptList = this.selectList(deptWrapper.eq("parent_id", id));
		if(deptList.size() > 0){
			for(Permission p : deptList){
				//先删除关系表
				deleteRolePermissionByPerId(p.getId());
				
				this.deleteById(p.getId());
			}
		}
		//先删除关系表
		deleteRolePermissionByPerId(id.longValue());
		this.deleteRolePermissionByPerId(id.longValue());
		return this.deleteById(id);
	}

	
}
