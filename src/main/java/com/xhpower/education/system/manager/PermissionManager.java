package com.xhpower.education.system.manager;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Permission;

/**
 * @ClassName: PermissionManager
 * @Description: 系统权限管理接口
 * @author tangxiaobin
 * @date 2017.4.19
 */
public interface PermissionManager extends IService<Permission>{
	
	Permission selectByPId(Long id);
	
	List<Permission> list(Long id);
	
	int deleteRolePermissionByPerId(Long permissionId);
	
	public List<Permission> selectByRoleIds(List<Integer> roleIds);
	
	public List<Permission> selectChildren(Map<String,Object> map);
	
	/**
	 * 
	* @Title: deletePerAndChildren 
	* @Description: 删除记录以及其子记录
	* @param id
	* @return 
	* @author xiong li
	 */
	public Boolean deletePerAndChildren(Integer id);
	
}
