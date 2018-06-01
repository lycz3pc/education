package com.xhpower.wexin.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.system.entity.SysLogEntity;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.article.manager.NpWxArticleTManager;
import com.xhpower.wexin.utils.PublicMethod;

/**
 * <p>
 * 热文表 前端控制器
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-17
 */
@RestController
@RequestMapping("/article/npWxArticleT")
public class NpWxArticleTController {
	
	@Autowired
	private NpWxArticleTManager npWxArticleTManager;
	
	/**
     * 热文列表
     * @param jsonObject {page:页码,rows:行数,title:标题筛选}
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findarticleList")
	public R articleList(@RequestBody JSONObject jsonObject){
		try {
			//初始化page
			jsonObject = PublicMethod.initializationPageJson(jsonObject);
			//创建分页对象
			Page<NpWxArticleT> pages = new Page<NpWxArticleT>(jsonObject.getInteger("page"), jsonObject.getInteger("rows"));
			EntityWrapper<NpWxArticleT> wrapper = new EntityWrapper<NpWxArticleT>();
			wrapper.eq("is_delete", 0);
			if(StringUtils.isNotEmpty(jsonObject.getString("title"))){
				wrapper.like("r_title", jsonObject.getString("title"));
			}
			wrapper.orderBy("create_date", false);
			pages = this.npWxArticleTManager.selectPage(pages,wrapper);
			return R.success().page(pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	/**
     * 查询单条热文记录
     * @param rid 热文表主键ID
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findArticleObject")
	public R findArticleObject(Integer rid){
		try {
			if(rid==null){
				return R.error("参数异常");
			}
			NpWxArticleT npWxArticleT = this.npWxArticleTManager.selectNpWxArticleObject(rid);
			return npWxArticleT==null?R.error():R.success().put("npWxArticleT", npWxArticleT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	/**
     * 修改 新增 逻辑删除等操作
     * @param npWxArticleT 
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/saveOrEditArticle")
	public R saveOrEditArticle(@RequestBody NpWxArticleT npWxArticleT){
		try {
			if(npWxArticleT==null){
				return R.error("参数异常");
			}
			return this.npWxArticleTManager.insertOrUpdateArticle(npWxArticleT)?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
}
