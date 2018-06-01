
/** 
* @Title: NpConstantItemMapper.java
* @Package: com.xhpower.education.platform.dao
* @Description: 新父母学校常量项管理DAO层
* @Author: xiong li
* @Date: 2017-09-30
*
*/
package com.xhpower.education.platform.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.platform.entity.NpConstantItem;

@Mapper
public interface NpConstantItemMapper extends BaseMapper<NpConstantItem> {
   
	/**
	 * 
	* @Title: insertSelective 
	* @Description: 新增
	* @param npConstantItem
	* @return 
	* @author xiong li
	 */
	int insertSelective(NpConstantItem npConstantItem);
}