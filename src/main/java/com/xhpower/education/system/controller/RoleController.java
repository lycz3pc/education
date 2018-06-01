package com.xhpower.education.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.common.BaseController;
import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.system.entity.Role;
import com.xhpower.education.system.entity.RolePermission;
import com.xhpower.education.system.entity.RoleVo;
import com.xhpower.education.system.manager.PermissionManager;
import com.xhpower.education.system.manager.RoleManager;
import com.xhpower.education.system.manager.RolePermissionManager;
import com.xhpower.education.utils.R;

@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
     @Autowired
	 private RoleManager  roleManager;
     @Autowired
     private PermissionManager permissionManager;
	@Autowired
	private RolePermissionManager rolePermissionManager;

	/**
	 * @param roleVo(参数对象)
	 * @return page
	 * @Title: index
	 * @Description: 角色列表
	 * @author lixiong
	 */
	 @RequestMapping("/list")
     public R list(@RequestBody RoleVo roleVo){
		 Page<Role> page = new Page<>(roleVo.getCurrent(), roleVo.getSize());
		 EntityWrapper<Role> wrapper = new EntityWrapper<>();
		 wrapper.like("name", roleVo.getName().replace(" ", ""));
		 wrapper.notIn("name","root");
		 page = roleManager.selectPage(page, wrapper);
		 return R.success().page(page);
     }


	/**
	 * @param roleVo(参数对象)
	 * @return page
	 * @Title: index
	 * @Description: 角色列表
	 * @author lixiong
	 */
	@RequestMapping("/listxh")
	public R listxh(@RequestBody RoleVo roleVo){
		Page<Role> page = new Page<>(roleVo.getCurrent(), roleVo.getSize());
		EntityWrapper<Role> wrapper = new EntityWrapper<>();
		wrapper.like("name", roleVo.getName().replace(" ", ""));
		page = roleManager.selectPage(page, wrapper);
		return R.success().page(page);
	}

	/**
	 * @param roleVo(参数对象)
	 * @return success/error
	 * @Title: index
	 * @Description: 保存角色
	 * @author lixiong
	 */
	 @RequestMapping(value="/save",method=RequestMethod.POST)
	 @SysLog("权限角色新增")
     public R save(@RequestBody RoleVo roleVo){
		 EntityWrapper<Role> wrapper = new EntityWrapper<>();
		 wrapper.eq("name", roleVo.getName().replace(" ", ""));
		 Role  bol = roleManager.selectOne(wrapper);
		 if(bol!=null && bol.getId()!=roleVo.getId()){
			 return  R.error("名称已经存在");
		 }
		roleManager.addRole(roleVo);
		 return  R.success();
     }


	/**
	 * @param roleVo(参数对象)
	 * @return success/error
	 * @Title: index
	 * @Description: 修改角色
	 * @author lixiong
	 */
	 @RequestMapping(value="/update",method=RequestMethod.POST)
	 @SysLog("权限角色修改")
     public R update(@RequestBody RoleVo roleVo){
		 EntityWrapper<Role> wrapper = new EntityWrapper<>();
		 wrapper.eq("name", roleVo.getName().replace(" ", ""));
		 Role  bol = roleManager.selectOne(wrapper);
		 if(bol!=null && bol.getId()!=roleVo.getId()){
			 return  R.error("名称已经存在");
		 }
		 roleManager.updRole(roleVo);
		 return  R.success();
     }


	/**
	 * @param id(角色id)
	 * @return success/error
	 * @Title: index
	 * @Description: 删除角色
	 * @author lixiong
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@SysLog("权限角色删除")
	public R del(Integer id) throws Exception {
		if(roleManager.delRole(id)){
			return R.success();
		}else{
			return R.error("服务器异常");
		}
	}

	/**
	 * @param id(角色id)
	 * @return rolePermissions(角色权限)/role（角色对象）
	 * @Title: index
	 * @Description: 获取权限对象
	 * @author lixiong
	 */
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public R role(Integer id) throws Exception {
		EntityWrapper<RolePermission> wrapper = new EntityWrapper<RolePermission>();
		wrapper.setEntity(new RolePermission());
		wrapper.eq("role_id",id);
		Role role = roleManager.selectById(id);
		List<RolePermission > rolePermissions = rolePermissionManager.selectList(wrapper);
		return R.success().put("rolePermissions",rolePermissions).put("role",role);
	}

	/**
	 * @param roleVo
	 * @return permissionList(权限列表)）
	 * @Title: index
	 * @Description: 对应权限列表
	 * @author lixiong
	 */
	@RequestMapping(value = "/permission", method = RequestMethod.POST)
	public R permission(@RequestBody RoleVo roleVo) throws Exception {
		Page<Permission> page = new Page<>(roleVo.getCurrent(), roleVo.getSize());
		EntityWrapper<Permission> wrapper = new EntityWrapper<Permission>();
		wrapper.setEntity(new Permission());
		wrapper.eq("parent_id",roleVo.getId());
		page = permissionManager.selectPage(page, wrapper);
		return R.success().page(page);
	}


	/**
	 * @param permission(菜单对象)
	 * @return success/error
	 * @Title: index
	 * @Description: 菜单新增
	 * @author lixiong
	 */
	@RequestMapping(value = "/permissionadd", method = RequestMethod.POST)
	@SysLog("权限菜单新增")
	public R permissionadd(Permission permission) throws Exception {
		 permissionManager.insert(permission);
		   return R.success();

	}

	/**
	 * @param permission(菜单对象)
	 * @return success/error
	 * @Title: index
	 * @Description: 菜单修改
	 * @author lixiong
	 */
	@RequestMapping(value = "/permissionaupd", method = RequestMethod.POST)
	@SysLog("权限菜单修改")
	public R permissionaupd(Permission permission) throws Exception {
		 permissionManager.updateById(permission);
			return R.success();
	}


	/**
	 * @param id(菜单id)
	 * @return success/error
	 * @Title: index
	 * @Description: 菜单修改
	 * @author lixiong
	 */
	@RequestMapping(value = "/permissionaudel", method = RequestMethod.POST)
	@SysLog("权限菜单删除")
	public R permissionaudel(Integer id) throws Exception {
		boolean bol = permissionManager.deletePerAndChildren(id);
		
		return bol ?  R.success() :  R.error("服务器异常");

	}

	/**
	 * @param id(菜单id)
	 * @return success/error
	 * @Title: index
	 * @Description: 菜单修改
	 * @author lixiong
	 */
	@RequestMapping(value = "/mission", method = RequestMethod.POST)
	public R mission(Integer id) throws Exception {
		Permission permission = permissionManager.selectById(id);
		if(permission!=null){
			return R.success().put("permission",permission);
		} else {
			return R.error("服务器异常");
		}

	}


}
