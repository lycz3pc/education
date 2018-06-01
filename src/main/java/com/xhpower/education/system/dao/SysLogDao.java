package com.xhpower.education.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.system.entity.SysLogEntity;

/**
 * 系统日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
void save(SysLogEntity t);
	
	void save(Map<String, Object> map);
	
	void saveBatch(List<SysLogEntity> list);
	
	int update(SysLogEntity t);
	
	int update(Map<String, Object> map);
	
	int delete(Object id);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] id);

	SysLogEntity queryObject(Object id);
	
	List<SysLogEntity> queryList(Map<String, Object> map);
	
	List<SysLogEntity> queryList(Object id);
	
	int queryTotal(Map<String, Object> map);

	int queryTotal();
}
