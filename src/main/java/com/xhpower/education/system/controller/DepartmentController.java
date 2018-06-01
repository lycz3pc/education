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
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.system.entity.Department;
import com.xhpower.education.system.manager.AdminManager;
import com.xhpower.education.system.manager.DepartmentManager;
import com.xhpower.education.utils.R;


@RestController
@RequestMapping("/admin/sys/department")
public class DepartmentController {
	@Autowired
	private DepartmentManager departmentManager;
	@Autowired
	private AdminManager adminManager;

	@RequestMapping("/list")
	public R list(Page<Department> page) {
	  
		return R.success().page(departmentManager.selectPage(page));
	}

	@RequestMapping("/listChild")
	public R listChild(Long parentId) {
		return R.success().list(departmentManager.list(parentId));
	}

	@RequestMapping("/listChildList")
	public List<Department> listChildList(Long parentId) {
		return departmentManager.list(parentId);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody Department department) {
		return departmentManager.insert(department) ? R.success() : R.error();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public R update(@RequestBody Department department) {
		return departmentManager.updateByPrimaryKey(department) > 0? R.success() : R.error();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public R delete(Long[] ids) {
		for(Long id : ids){
			
			//存在子部门时不能删除
			EntityWrapper<Department> deptWrapper = new EntityWrapper<Department>();
			List<Department> deptList = departmentManager.selectList(deptWrapper.eq("parentId", id));
			if(deptList.size() > 0){
				return R.error("存在子节点，不能删除");
			}
			
			//部门不为空时不能删除
			EntityWrapper<Admin> wrapper = new EntityWrapper<Admin>();
			List<Admin> list = adminManager.selectList(wrapper.eq("departmentId", id));
			if(list.size() > 0){
				return R.error("部门中有人，不能删除");
			}
		}
		return departmentManager.deleteBatchIds(Arrays.asList(ids)) ? R.success() : R.error();
	}
	

}
