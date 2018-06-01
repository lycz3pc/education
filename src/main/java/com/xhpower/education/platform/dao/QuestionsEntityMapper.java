package com.xhpower.education.platform.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.platform.entity.BkUserInfoVO;
import com.xhpower.education.platform.entity.QuestionsEntity;

public interface QuestionsEntityMapper extends BaseMapper<QuestionsEntity> {
	
	/**
	 * 
	* @Title: getUserInfo 
	* @Description: 查询贝壳网用户
	* @param userId
	* @return 
	* @author xiong li
	 */
	public BkUserInfoVO getUserInfo(Integer userId);

}