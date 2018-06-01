package com.xhpower.education.system.manager;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.system.entity.Role;


/** 
 * @ClassName: AdminManager 
 * @Description: 系统用户管理接口
 * @author lisf 
 * @date 2016年12月10日 下午4:56:58 
 *  
 */
public interface AdminManager extends IService<Admin>{
	 
int updateByPrimaryKey(Admin admin);
	
	
	Page<Admin> list(Page<Admin> page);
	
    /**
     * 
    * @Title: getAuthorizationInfo 
    * @Description: 获取用户权限
    * @param username
    * @return    设定文件
    * @author lisf 
    * @return SimpleAuthorizationInfo    返回类型 
    * @throws
     */
	public SimpleAuthorizationInfo getAuthorizationInfo(String username);
	
	/**
	 * 
	* @Title: getMenuList 
	* @Description: 获取用户菜单 
	* @param username
	* @return    设定文件
	* @author lisf 
	* @return List<Permission>    返回类型 
	* @throws
	 */
	public List<Permission> getMenuList(String username);
	
	/**
	 * 
	* @Title: delectAdminRoleByAdminId 
	* @Description: 根据用户id删除用户角色表
	* @param id
	* @return 
	* @author xiong li
	 */
	public int delectAdminRoleByAdminId(Long id);

	/**
	 *
	 * @Title: delectAdminRoleByAdminId
	 * @Description: 根据用户id删除用户及用去权限表
	 * @param id
	 * @return
	 * @author xiong li
	 */
	public boolean delectAdmin(Long id);

	
	/**
	 * 
	* @Title: updateAdmin 
	* @Description: 更新关系
	* @param admin
	* @param roles
	* @return 
	* @author xiong li
	 */
	public int updateAdmin(Admin admin, Set<Role> roles);
	
	/**   
	 * @Title: getMenusByPid
	 * @date 2017-8-31 下午5:30:10
	 * @author Administrator
	 * @Description: 根据root权限差子集菜单
	 * @param map
	 * @return
	 * 
	 */
	public List<Permission> getMenusByPid(String username,String pid);
}
