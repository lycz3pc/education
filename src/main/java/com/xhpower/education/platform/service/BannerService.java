package com.xhpower.education.platform.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.BannerEntity;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: BannerService 
* @Description: 轮播图服务接口
* @author xiong li 
* @date 2017年8月26日 下午3:25:14 
*
 */
public interface BannerService extends IService<BannerEntity>{
	
	/**
	 * 
	* @Title: bannerList 
	* @Description: 查询banner列表
	* @param page 当前页
	* @param rows 每页显示条数
	* @param banner 查询条件
	* @return 
	* @author xiong li
	 */
	public R bannerList(int page , int rows, BannerEntity banner);
	
	/**
	 * 
	* @Title: deleteBanner 
	* @Description: 删除banner
	* @param request 请求对象
	* @param ids 需删除的banner编号
	* @return 
	* @author xiong li
	 */
	public Boolean deleteBanner(HttpServletRequest request, Integer[] ids);
	
	/**
	 * 
	* @Title: insertOrUpdateBanner 
	* @Description: banner新增或更新
	* @param request 请求
	* @param banner banner对象
	* @param file 上传的文件
	* @return 
	* @author xiong li
	 */
	public Boolean insertOrUpdateBanner(HttpServletRequest request, BannerEntity banner, MultipartFile file);
	
	/**
	 * 
	* @Title: upDown 
	* @Description: 下架除轮播图位,推荐位以外的位置的记录 
	* @param id 需下架记录编号
	* @param location 位置
	* @return 
	* @author xiong li
	 */
	public Boolean upDown(Integer id, String location);
	
	/**
	 * 
	* @Title: selectCms 
	* @Description: 查找内容管理列表 
	* @param type 内容类型
	* @param page 当前页
	* @param rows 每页显示条数
	* @return 
	* @author xiong li
	 */
	public R selectCms(String type, int page , int rows);
	
	/**
	 * 
	* @Title: cmsColumns 
	* @Description: 查询内容管理的栏目列表 
	* @return 
	* @author xiong li
	 */
	public Object cmsColumns();
	
	/**
	 * 
	* @Title: validateSort 
	* @Description: 验证banner同位置排序是否重复
	* @param banner 存在条件
	* @return 
	* @author xiong li
	 */
	public R validate(BannerEntity banner);
	
	
}
