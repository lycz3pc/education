package com.xhpower.education.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xhpower.education.system.manager.AdminManager;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.ShiroUtils;

@RestController
@RequestMapping("/sys/menu")
public class MenuController {
	
	@Autowired
	private  AdminManager adminManager;
   
	@RequestMapping("/list")
	@ResponseBody
	public R list(){		
		return R.success().put("menus",adminManager.getMenuList(ShiroUtils.getUsername()));
	}
	
	@RequestMapping("/bypid")
	@ResponseBody
	public R byrole(String parentid){		
		return R.success().put("menus",adminManager.getMenusByPid(ShiroUtils.getUsername(),parentid));
	}
}
