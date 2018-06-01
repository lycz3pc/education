package com.xhpower.education.platform.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.utils.R;

/**
 * 专家
 * 
 * @author liuyoucheng
 */
public interface ExpertService extends IService<ExpertEntity>{

	Page<ExpertEntity> list(Page<ExpertEntity> pages, ExpertEntity school);

	ExpertEntity isTop(Integer id);

	boolean save(ExpertEntity expert,MultipartFile file,HttpServletRequest request);

	/*boolean validateSort(ExpertEntity expert);*/
	
	/**
	 * 
	* @Title: updateImg 
	* @Description: 更新专家头像
	* @param expert
	* @param file
	* @param request
	* @return 
	* @author xiong li
	 */
	public R updateImg(ExpertEntity expert,MultipartFile file,HttpServletRequest request);
	
	/**
	 * 
	* @Title: check 
	* @Description: 审核专家
	* @param expert
	* @return 
	* @author xiong li
	 */
	public R check(ExpertEntity expert);
	
}
