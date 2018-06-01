package com.xhpower.education.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.system.entity.Region;

@Mapper
public interface RegionMapper extends BaseMapper<Region> {
	
	List<Region>  selectByParentId(@Param("id") String id);
    
	@Select("select DISTINCT t1.* from region t1 INNER  JOIN  region t2 on t1.id = t2.parentid where  t1.parentid>0 and (t2.name like '%${value}%' or  t1.name like '%${value}%')")
	List<Region> like(String str);
}