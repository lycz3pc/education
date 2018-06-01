package com.xhpower.education.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.system.entity.AuditLog;

@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLog> {

	
	@Select("SELECT c1.customertruename as userid,c2.customertruename as audituser,"+""
			+ "a.auditresult,a.createtime from auditlog a,customer_info c1,customer_info c2"+
           "where a.userid=c1.customerId"+
          "and  a.audituser=c2.customerId")
	List<AuditLog> selectAuditList();
}
