package com.xhpower.education.system.manager.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.DepartmentMapper;
import com.xhpower.education.system.entity.Department;
import com.xhpower.education.system.manager.DepartmentManager;

@Service
@Transactional
public class DepartmentImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentManager {
	@Autowired
	private  DepartmentMapper  departmentMapper;
	
	@Override
	public int updateByPrimaryKey(Department department) {
		return departmentMapper.updateByPrimaryKey(department);
	}

	@Override
	public List<Department> list(Long parentId) {
		
		return departmentMapper.list(parentId);
	}

	

}
