package com.xhpower.education.system.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.system.entity.Role;
@Mapper
public interface RoleMapper extends BaseMapper<Role>  {
	
	@Delete(" delete from np_role_permission where role_id = #{roleId}")
	int deleteRolePermissionByRoleId(Integer roleId);
	
	@Delete(" delete from np_admin_role where role_id = #{roleId}")
	int deleteRoleAdminByRoleId(Integer roleId);
	
	Set<Role> selectByUsername(String userName);
	
	public int insertRolePermission(Role role);
	
	@Select(" select * from np_role ")
	public List<Role> list();

}
