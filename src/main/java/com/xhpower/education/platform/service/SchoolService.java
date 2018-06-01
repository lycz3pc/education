package com.xhpower.education.platform.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.SchoolEntity;
import com.xhpower.education.utils.R;

public interface SchoolService extends IService<SchoolEntity> {

	Boolean update(SchoolEntity school, MultipartFile[] files, HttpServletRequest request);

	Boolean save(SchoolEntity school, MultipartFile[] files, HttpServletRequest request);

	Page<SchoolEntity> list(Page<SchoolEntity> pages, SchoolEntity school);

	SchoolEntity isTop(Integer id);

	boolean saveJoinUnion(SchoolEntity school, MultipartFile[] files, HttpServletRequest request);

	R check(SchoolEntity school);

	String getQrCode(HttpServletRequest request,JSONObject jsonObject);
	
	/**
	 * 
	* @Title: insertRelation 
	* @Description: 创建关系
	* @param listId
	* @param bookIds
	* @return 
	* @author liuyoucheng
	 */
	//public int insertRelation(Integer listId, Long[] bookIds);
}
