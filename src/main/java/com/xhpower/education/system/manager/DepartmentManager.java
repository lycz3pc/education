package com.xhpower.education.system.manager;



import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Department;


/** 
 * @ClassName: DepartmentManager 
 * @Description: 系统用户管理接口
 * @author lisf 
 * @date 2016年12月10日 下午4:56:58 
 *  
 */
public interface DepartmentManager extends IService<Department>{
	 
	int updateByPrimaryKey(Department department);
	
	public List<Department> list(Long parentId);
}
