package com.xhpower.education.system.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xhpower.education.system.manager.RegionManager;
import com.xhpower.education.utils.R;


/**
 * 
* @ClassName: RegionController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lisf 
* @date 2017年5月13日 下午5:41:16 
*
 */
@RestController
@RequestMapping("/api/region")
public class RegionController {
	@Autowired
	private RegionManager  regionManager;
	
	@RequestMapping("/list")
	public R list(String id){
		return R.success().put("regions",regionManager.selectByParentId(id));
	}
	@RequestMapping("/getRegion")
	public R getRegion(String[] ids){
		return R.success().put("regions",regionManager.selectBatchIds(Arrays.asList(ids)));
	}
}
