package com.xhpower.wexin.activity.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.activity.manager.NpWxActivityTManager;
import com.xhpower.wexin.utils.PublicMethod;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/npWxActivityT")
public class NpWxActivityTController {
	
	@Autowired
	private NpWxActivityTManager npWxActivityTManager;
	
	
	 /**
     * 活动列表
     * @param jsonObject {page:页码,rows:行数,title:标题筛选}
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findNpWxActivityList")
	public R findNpWxActivityList(@RequestBody JSONObject jsonObject){
		try {
			//初始化page
			jsonObject = PublicMethod.initializationPageJson(jsonObject);
			//创建分页对象
			Page<NpWxActivityT> pages = new Page<NpWxActivityT>(jsonObject.getInteger("page"), jsonObject.getInteger("rows"));
			EntityWrapper<NpWxActivityT> wrapper = new EntityWrapper<NpWxActivityT>();
			wrapper.eq("is_delete", 0);
			if(StringUtils.isNotEmpty(jsonObject.getString("title"))){
				wrapper.like("h_title", jsonObject.getString("title"));
			}
			wrapper.orderBy("create_date", false);
			pages = this.npWxActivityTManager.selectPage(pages,wrapper);
			return R.success().page(pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	/**
     * 活动详情
     * @param hid 活动表主键ID
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findActivityObject")
	public R findActivityObject(Integer hid){
		try {
			if(hid==null){
				return R.error("参数异常");
			}
			NpWxActivityT npWxActivityT = this.npWxActivityTManager.selectNpWxActivityObject(hid);
			return npWxActivityT==null?R.error():R.success().put("npWxActivityT", npWxActivityT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	
	/**
     * 发布 修改 逻辑删除
     * @param npWxActivityT 
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/saveOrEditActivity")
	public R saveOrEditActivity(@RequestBody NpWxActivityT npWxActivityT){
		try {
			if(npWxActivityT==null){
				return R.error("参数异常");
			}
			return this.npWxActivityTManager.insertOrUpdateActivity(npWxActivityT)?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
}
