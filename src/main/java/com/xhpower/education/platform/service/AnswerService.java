package com.xhpower.education.platform.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.AnswerEntity;
import com.xhpower.education.utils.R;

public interface AnswerService extends IService<AnswerEntity> {
	
	/**
	 * 
	* @Title: selectAnswerList 
	* @Description: 获取评论列表
	* @param pages
	* @param answerEntity
	* @return 
	* @author xiong li
	 */
	public R selectAnswerList(Page<AnswerEntity> pages, AnswerEntity answerEntity);
	
	/**
	 * 
	* @Title: deleteAnswer 
	* @Description: 删除回答
	* @param id
	* @return 
	* @author xiong li
	 */
	public R deleteAnswer(Integer id);
	
	/**
	 * 
	* @Title: answerAuth 
	* @Description: 评论审核
	* @param answerEntity
	* @return 
	* @author xiong li
	 */
	public R answerAuth(AnswerEntity answerEntity);
	
	
}
