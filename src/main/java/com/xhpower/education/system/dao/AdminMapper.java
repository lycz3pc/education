
/** 
* @Title: AdminMapper.java
* @Package: com.xhpower.waterutilities.system.dao
* @Description: TODO() 
* @Author: li shifeng
* @Date: 2016-12-10
*
*/
package com.xhpower.education.system.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xhpower.education.system.entity.Admin;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
  
	@Update("update np_admin  set email = #{email,jdbcType=VARCHAR},"
	   		+ "status = #{status,jdbcType=CHAR}, password = #{password,jdbcType=VARCHAR},"
	   		+ " phone = #{phone,jdbcType=VARCHAR},username = #{username,jdbcType=VARCHAR},"
	   		+ "name = #{name,jdbcType=VARCHAR},start_time = #{startTime,jdbcType=TIMESTAMP},"
	   		+ "end_time = #{endTime,jdbcType=TIMESTAMP} where id = #{id,jdbcType=BIGINT}")
	   int updateByPrimaryKey(Admin admin);
	   
	   @Select(" select * from np_admin ")
	   List<Admin> list(Pagination page);
	   
	   @Delete(" delete from np_admin_role where admin_id = #{id,jdbcType=BIGINT} ")
	   int delectAdminRoleByAdminId(Long id);
	   
	   int insertAdminRole(Map<String,Object> map);
   
}