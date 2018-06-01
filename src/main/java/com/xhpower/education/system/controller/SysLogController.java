package com.xhpower.education.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.system.entity.SysLogEntity;
import com.xhpower.education.system.manager.SysLogService;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: SysLogController 
* @Description: 系统日志控制类 
* @author xiong li 
* @date 2017年8月31日 上午10:34:43 
*
 */
@RestController
@RequestMapping("/admin/sys/sysLog")
public class SysLogController {
	
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 
	* @Title: list 
	* @Description: 系统日志列表
	* @param page 当前页
	* @param rows 每页显示数据
	* @param start 开始时间
	* @param end 结束时间
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/list")
	public R list(int page , int rows, String start , String end){
		//创建分页对象
		Page<SysLogEntity> pages = new Page<SysLogEntity>(page, rows);
		//查询条件封装对象
		EntityWrapper<SysLogEntity> wrapper = new EntityWrapper<SysLogEntity>();
		//查询条件开始时间和结束时间
		if(!StringUtils.isEmpty(start)){
			wrapper.ge("create_date", start);
		}
		
		if(!StringUtils.isEmpty(end)){
			wrapper.le("create_date", end);
		}
		//以创建时间排序
		wrapper.orderBy("create_date", false);
		//分页带条件查询
		pages = sysLogService.selectPage(pages,wrapper);
		//创建easyui数据格式对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pages.getTotal());
		map.put("rows", pages.getRecords());
		
		return R.success(map);
	}

}
