package com.xhpower.wexin.activityPartake.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.activityPartake.entity.NpWxActivityPartakeT;
import com.xhpower.wexin.activityPartake.manager.NpWxActivityPartakeTManager;
import com.xhpower.wexin.utils.PublicMethod;

/**
 * <p>
 * 活动参与记录表 前端控制器
 * </p>
 *
 * @author Song BoLin
 * @since 2017-11-09
 */
@RestController
@RequestMapping("/activityPartake/npWxActivityPartakeT")
public class NpWxActivityPartakeTController {
	@Autowired
	private NpWxActivityPartakeTManager npWxActivityPartakeTManager;
	 /**
     * 活动参与人数列表
     * @param jsonObject {page:页码,rows:行数,title:标题筛选}
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findNpWxActivityPartakeList")
	public R findNpWxActivityPartakeList(@RequestBody JSONObject jsonObject){
		try {
			//初始化page
			jsonObject = PublicMethod.initializationPageJson(jsonObject);
			//创建分页对象
			Page<NpWxActivityPartakeT> pages = new Page<NpWxActivityPartakeT>(jsonObject.getInteger("page"), jsonObject.getInteger("rows"));
			EntityWrapper<NpWxActivityPartakeT> wrapper = new EntityWrapper<NpWxActivityPartakeT>();
			wrapper.orderBy("create_date", false);
			wrapper.where("activity_id={0}", jsonObject.getInteger("hid"));
			pages = this.npWxActivityPartakeTManager.selectPage(pages,wrapper);
			return R.success().page(pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
}
