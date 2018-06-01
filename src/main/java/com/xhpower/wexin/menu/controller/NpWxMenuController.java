package com.xhpower.wexin.menu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.common.BaseController;
import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.menu.entity.NpWxMenuT;
import com.xhpower.wexin.menu.manager.NpWxMenuTManager;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/npWxMenuController")
public class NpWxMenuController{
	
	@Autowired
	private NpWxMenuTManager npWxMenuManager;
	
	@RequestMapping(value = "/findMenuList", method = RequestMethod.POST)
	public R findMenuList(Integer pid){
		try {
			EntityWrapper<NpWxMenuT> wrapper = new EntityWrapper<>();
			if(pid!=null && pid>0){
				wrapper.eq("parent_id", pid);
			}else{
				wrapper.eq("level",1);
			}
			List<NpWxMenuT> jsonObject = this.npWxMenuManager.selectList(wrapper);
			if(jsonObject==null){
				return R.error("未匹配到数据");
			}
			return R.success().put("permiss", jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	@RequestMapping("/findMenuObject")
	public R findMenuObject(Integer id){
		try {
			if(id==null){
				return R.error("参数异常");
			}
			NpWxMenuT npWxMenuT = this.npWxMenuManager.selectById(id);
			return npWxMenuT==null?R.error():R.success().put("npWxMenuT", npWxMenuT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	@RequestMapping("/updateMenu")
	public R updateMenuObject(HttpServletRequest request,NpWxMenuT npWxMenuT){
		try {
			boolean reslut = this.npWxMenuManager.genderWxMenu(request);
			if(!reslut){
				return R.error("操作异常！");
			}
			return this.npWxMenuManager.insertOrUpdate(npWxMenuT)?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	@RequestMapping("/deleteMenu")
	public R deleteMenuObject(HttpServletRequest request,Integer id){
		try {
			if(id==null){
				return R.error("参数异常");
			}
			boolean reslut = this.npWxMenuManager.genderWxMenu(request);
			if(!reslut){
				return R.error("操作异常！");
			}
			return this.npWxMenuManager.deleteById(id)?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
}
