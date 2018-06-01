package com.xhpower.education.platform.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.BkUserInfoVO;
import com.xhpower.education.platform.entity.QuestionsEntity;
import com.xhpower.education.utils.R;

public interface QuestionsService extends IService<QuestionsEntity> {
	
	/**
	 * 
	* @Title: selectQueList 
	* @Description: 获得问题列表
	* @param pages
	* @param questionsEntity
	* @return 
	* @author xiong li
	 */
	public R selectQueList(Page<QuestionsEntity> pages, QuestionsEntity questionsEntity);
	
	/**
	 * 
	* @Title: deleteQue 
	* @Description: 删除问题
	* @param id
	* @return 
	* @author xiong li
	 */
	public R deleteQue(Integer id);
	
	
	/**
	 * 
	* @Title: getUserInfo 
	* @Description: 查询贝壳网用户
	* @param userId
	* @return 
	* @author xiong li
	 */
	public BkUserInfoVO getUserInfo(Integer userId);
	
	/**
	 * 
	* @Title: questionAuth 
	* @Description: 问题审核
	* @param ques
	* @return 
	* @author xiong li
	 */
	public R questionAuth(QuestionsEntity ques);
	
}
