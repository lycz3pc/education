package com.xhpower.wexin.banner.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.platform.entity.BannerEntity;
import com.xhpower.education.platform.service.BannerService;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.article.manager.NpWxArticleTManager;
import com.xhpower.wexin.banner.entity.NpBanner;
import com.xhpower.wexin.banner.manager.NpBannerManager;

/**
 * <p>
 * banner 前端控制器
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-17
 */
@RestController
@RequestMapping("/banner/npBanner")
public class NpBannerController {
	@Autowired
	private NpBannerManager npBannerManager;
	
	/**
	 * 
	* @Title: list 
	* @Description: banner列表
	* @return 
	* @author song bolin
	 */
	@RequestMapping("/findbannerList")
	public R bannerList(@RequestBody JSONObject jsonObject){
		try {
			if(jsonObject.getInteger("page")==null){
				jsonObject.put("page", 1);
			}
			if(jsonObject.getInteger("rows")==null){
				jsonObject.put("rows", 10);
			}
			//创建分页对象
			Page<NpBanner> pages = new Page<NpBanner>(jsonObject.getInteger("page"), jsonObject.getInteger("rows"));
			EntityWrapper<NpBanner> wrapper = new EntityWrapper<NpBanner>();
			if(StringUtils.isNotEmpty(jsonObject.getString("title"))){
				wrapper.like("title", jsonObject.getString("title"));
			}
			wrapper.orderBy("id", false);
			wrapper.where("source = 1", "source");
			pages = npBannerManager.selectPage(pages,wrapper);
			return R.success().page(pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	/**
	 * 
	* @Title: update 
	* @Description: 修改
	* @param id 选中行id
	* @return 
	* @author song bolin
	 */
	@RequestMapping("/findbannerObject")
	public R findBannerObject(Integer id){
		try {
			if(id==null){
				return R.error("参数异常");
			}
			NpBanner npBanner = npBannerManager.selectNpBannerObject(id);
			return npBanner==null?R.error():R.success().put("npBanner", npBanner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	/**
	 * 
	* @Title: save 
	* @Description: 保存
	* @param npBannner 保存的npBanner对象
	* @return 
	* @author song bolin
	 */
	@RequestMapping("/saveOrEditBanner")
	public R saveOrEditBanner(@RequestBody NpBanner npBanner){
		try {
			if(npBanner==null){
				return R.error("参数异常");
			}
			npBanner.setUpDown("1");
			return npBannerManager.insertOrUpdateBanner(npBanner)?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	/**
	 * 
	* @Title: delete
	* @Description: 删除
	* @param id 选中行id
	* @return 
	* @author songbolin
	 */
	@RequestMapping("/deleteBanner")
	public R deleteBanner(@RequestBody JSONObject jsonObject){
		try {
			if(jsonObject.getInteger("id")==null){
				return R.error("参数异常");
			}
			return npBannerManager.deleteById(jsonObject.getInteger("id"))?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
}
