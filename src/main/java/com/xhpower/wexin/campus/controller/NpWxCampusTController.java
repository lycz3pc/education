package com.xhpower.wexin.campus.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;
import com.xhpower.wexin.campus.entity.NpWxCampusT;
import com.xhpower.wexin.campus.manager.NpWxCampusTManager;
import com.xhpower.wexin.utils.PublicMethod;

/**
 * <p>
 * 校区表 前端控制器
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/npWxCampusT")
public class NpWxCampusTController {
	
	@Autowired
	private NpWxCampusTManager npWxCampusTManager;
	@Autowired
	private ResourcesManager resourcesManager;
	
	
	 /**
     * 校区列表
     * @param jsonObject {page:页码,rows:行数,title:标题筛选}
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findCampusList")
	public R findCampusList(@RequestBody JSONObject jsonObject){
		try {
			//初始化page
			jsonObject = PublicMethod.initializationPageJson(jsonObject);
			//创建分页对象
			Page<NpWxCampusT> pages = new Page<NpWxCampusT>(jsonObject.getInteger("page"), jsonObject.getInteger("rows"));
			EntityWrapper<NpWxCampusT> wrapper = new EntityWrapper<NpWxCampusT>();
			wrapper.eq("is_delete", 0);
			if(StringUtils.isNotEmpty(jsonObject.getString("title"))){
				wrapper.like("campus_name", jsonObject.getString("title"));
			}
			wrapper.orderBy("create_date", false);
			pages = this.npWxCampusTManager.selectPage(pages,wrapper);
			if(pages!=null && pages.getRecords().size()>0){
				NpWxCampusT npWxCampusT = null;
				for (int i = 0; i < pages.getRecords().size(); i++) {
					npWxCampusT = pages.getRecords().get(i);
					if(npWxCampusT!=null && npWxCampusT.getQrcodeSourceId()!=null){
						Resources resources  =this.resourcesManager.selectById(npWxCampusT.getQrcodeSourceId());
						if(resources!=null){
							npWxCampusT.setQrImgUrl(resources.getPath());
						}
					}
				}
			}
			return R.success().page(pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	/**
     * 校区详情
     * @param id 校区表主键ID
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/findCampusObject")
	public R findCampusObject(Integer id){
		try {
			if(id==null){
				return R.error("参数异常");
			}
			NpWxCampusT npWxCampusT = this.npWxCampusTManager.selectCampusObject(id);
			return npWxCampusT==null?R.error():R.success().put("npWxCampusT", npWxCampusT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
	
	/**
     * 修改 新增 逻辑删除以及生成推广二维码
     * @param npWxCampusT
     * @author Deng BinBin
     * @return R 返回类型
     */
	@RequestMapping("/saveCampus")
	public R saveCampus(@RequestBody NpWxCampusT npWxCampusT,HttpServletRequest request){
		try {
			if(npWxCampusT==null){
				return R.error("参数异常");
			}
			
			return this.npWxCampusTManager.insertOrUpdateArticle(npWxCampusT,request)?R.success():R.error();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("请求异常");
	}
}
