package com.xhpower.education.system.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.system.manager.PermissionManager;
import com.xhpower.education.utils.R;

@RestController
@RequestMapping("/admin/sys/permission")
public class PermissionController {

	@Autowired
	private PermissionManager permissionManager;

	@RequestMapping("/list")
	public R list(Page<Permission> page) {
		
		return R.success().page(permissionManager.selectPage(page));
	}

	@RequestMapping("/listChild")
	public R listChild(Long parentId) {
		return R.success().list(permissionManager.list(parentId));
	}

	@RequestMapping("/listChildList")
	public R listChildList(String type) {
		EntityWrapper<Permission> wrapper = new EntityWrapper<>();
		wrapper.setEntity(new Permission());
		wrapper.notIn("permission","admin:root");
		if(type!=null && !"".equals(type)){
			wrapper.eq("type",type);
		}
		return R.success().put("permiss",permissionManager.selectList(wrapper));

	}

	@RequestMapping("/listChildListxh")
	public R listChildListxh(String type) {
		EntityWrapper<Permission> wrapper = new EntityWrapper<>();
		wrapper.setEntity(new Permission());
		if(type!=null && !"".equals(type)){
			wrapper.eq("type",type);
		}
		return R.success().put("permiss",permissionManager.selectList(wrapper));

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody Permission permission) {
		return permissionManager.insert(permission) ? R.success() : R.error();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public R update(@RequestBody Permission permission) {
		return permissionManager.updateById(permission) ? R.success() : R.error();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public R delete(Long[] ids) {
		for(Long id : ids){
			//存在子权限时不能删除
			EntityWrapper<Permission> deptWrapper = new EntityWrapper<Permission>();
			List<Permission> deptList = permissionManager.selectList(deptWrapper.eq("parentId", id));
			if(deptList.size() > 0){
				return R.error("存在子节点，不能删除");
			}
			
			permissionManager.deleteRolePermissionByPerId(id);
		}
		
		return permissionManager.deleteBatchIds(Arrays.asList(ids)) ? R.success() : R.error();
	}

}
