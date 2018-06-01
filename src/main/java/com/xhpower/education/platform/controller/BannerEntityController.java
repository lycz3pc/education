package com.xhpower.education.platform.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.platform.entity.BannerEntity;
import com.xhpower.education.platform.service.BannerService;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: BannerEntityController 
* @Description: 轮播图控制类 
* @author xiong li 
* @date 2017年8月26日 下午3:28:04 
*
 */
@RestController
@RequestMapping("/platform/banner")
public class BannerEntityController {
	
	@Autowired
	private BannerService bannerService;
	
	/**
	 * 
	* @Title: list 
	* @Description: banner列表
	* @param page 当前页
	* @param rows 每页显示条数
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/list")
	public R list(int page , int rows, BannerEntity banner){
		
		return bannerService.bannerList(page, rows, banner);
	}
	
	/**
	 * 
	* @Title: add 
	* @Description: 新增 
	* @param banner 新增对象
	* @param file 上传文件对象
	* @return 
	* @author xiong li
	 */
	@SysLog("banner新增与更新")
	@RequestMapping("/add")
	public R add(HttpServletRequest request, BannerEntity banner, MultipartFile file){
		
		//新增或更新
		return bannerService.insertOrUpdateBanner(request, banner, file)?R.success():R.error();

	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除
	* @param ids 需删除的数据id
	* @return 
	* @author xiong li
	 */
	@SysLog("banner删除")
	@RequestMapping("/delete")
	public R delete(HttpServletRequest request, Integer[] ids){
		
		return bannerService.deleteBanner(request, ids)?R.success():R.error();
	}
	
	/**
	 * 
	* @Title: upOrDown 
	* @Description: banner管理上架或下架
	* @param id 需下架的记录id
	* @param location 位置
	* @return 
	* @author xiong li
	 */
	@SysLog("banner管理上架或下架功能")
	@RequestMapping("/upOrDown")
	public R upOrDown(Integer id, String location){
		
		return bannerService.upDown(id, location)?R.success():R.error();	
	}
	
	/**
	 * 
	* @Title: selectCms 
	* @Description: 根据类型找cms
	* @param type 栏目类型
	* @param page 当前页
	* @param rows 每页显示数据
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/selectCms")
	public R selectCms(String type, int page , int rows){
		
		return bannerService.selectCms(type, page, rows);
	}
	
	/**
	 * 
	* @Title: cmsColumns 
	* @Description: 选择栏目
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/cmsColumns")
	public Object cmsColumns(){
		
		//返回所有栏目
		return bannerService.cmsColumns();
	}
	
	/**
	 * 
	* @Title: validateSort 
	* @Description: 验证排序 
	* @param banner banner对象：id、sort、location
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/validateSort")
	public R validateSort(BannerEntity banner){
		
		return bannerService.validate(banner);
	}

}
