package com.xhpower.education.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.system.entity.Permission;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission>{
    
	@Select("select id, name, text, url, parent_id as parentId, priority, type, permission"
			+ " from np_permission where id = #{id,jdbcType=BIGINT}")
	Permission selectByPId(Long id);
	
	List<Permission> list(Long parentId);
	
	@Delete(" delete from np_role_permission where permission_id = #{permissionId}")
	int deleteRolePermissionByPerId(Long permissionId);
	
	List<Permission> selectByRoleIds(@Param("roleIds") List<Integer> roleIds);
	
	public List<Permission> selectChildren(Map<String,Object> map);
}
