package com.xhpower.wexin.jdclick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.system.entity.SysLogEntity;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.banner.entity.NpBanner;
import com.xhpower.wexin.banner.manager.NpBannerManager;
import com.xhpower.wexin.jdclick.entity.NpClickSum;
import com.xhpower.wexin.jdclick.entity.NpWxClickRecordT;
import com.xhpower.wexin.jdclick.manager.NpWxClickRecordTManager;

/**
 * <p>
 * 数据埋点记录表 前端控制器
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-19
 */
@RestController
@RequestMapping("/jdclick/npWxClickRecordT")
public class NpWxClickRecordTController {
	@Autowired
	private NpWxClickRecordTManager npWxClickRecordTManager;
	
	 /**
	 * 
	* @Title: 统计每一天京东点击量 
	* @Description: 统计图
	* @param start 开始时间
	* @param end 结束时间
	* @return 
	* @author song bolin
	 */
	 @RequestMapping("/sumjdclickList")
	 public R sumjdclickList(@RequestBody JSONObject jsonObject){
		 //最近一个月的点击量查询
           if(jsonObject.getInteger("start") ==null){
        	   jsonObject.put("start",(int) (System.currentTimeMillis()/1000-2591000));
           }
           if(jsonObject.getInteger("end") ==null){
        	   jsonObject.put("end",(int) (System.currentTimeMillis()/1000));
            }
           
		 
			 return  R.success().list(npWxClickRecordTManager.list(jsonObject.getInteger("start"),jsonObject.getInteger("end")));
		 
	 }
	 
	
	
}
