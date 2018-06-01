
/** 
* @Title: DepartmentMapper.java
* @Package: com.xhpower.waterutilities.system.dao
* @Description: TODO() 
* @Author: li shifeng
* @Date: 2016-12-10
*
*/
package com.xhpower.education.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.system.entity.Department;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
  
   @Update("update department  set name = #{name,jdbcType=VARCHAR},"
   		+ "descript = #{descript,jdbcType=VARCHAR}, parentId = #{parentId,jdbcType=BIGINT}"
   		+ " where departmentid = #{departmentId,jdbcType=BIGINT}")
   int updateByPrimaryKey(Department department);
   
   public List<Department> list(Long parentId);
   
}