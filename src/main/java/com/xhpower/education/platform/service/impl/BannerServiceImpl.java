package com.xhpower.education.platform.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.BannerEntityMapper;
import com.xhpower.education.platform.entity.BannerConstants;
import com.xhpower.education.platform.entity.BannerEntity;
import com.xhpower.education.platform.entity.CmsColumn;
import com.xhpower.education.platform.entity.CmsEntity;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.platform.entity.SchoolEntity;
import com.xhpower.education.platform.entity.TextbookEntity;
import com.xhpower.education.platform.service.BannerService;
import com.xhpower.education.platform.service.CmsColumnService;
import com.xhpower.education.platform.service.CmsService;
import com.xhpower.education.platform.service.ExpertService;
import com.xhpower.education.platform.service.SchoolService;
import com.xhpower.education.platform.service.TextbookService;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;

/**
 * 
* @ClassName: BannerServiceImpl 
* @Description: 轮播图服务接口实现类
* @author xiong li 
* @date 2017年8月26日 下午3:26:10 
*
 */
@Service
@Transactional
public class BannerServiceImpl extends ServiceImpl<BannerEntityMapper, BannerEntity> implements BannerService {

	/**
	 * 图文管理服务类
	 */
	@Autowired
	private CmsService cmsService;
	/**
	 * 图文栏目管理服务类
	 */
	@Autowired
	private CmsColumnService cmsColumnService;
	/**
	 * 教材管理服务类
	 */
	@Autowired
	private TextbookService textbookService;
	/**
	 * 联盟学校管理服务类
	 */
	@Autowired
	private SchoolService schoolService;
	/**
	 * 专家管理服务类
	 */
	@Autowired
	private ExpertService expertService;
	
	@Override
	public Boolean insertOrUpdateBanner(HttpServletRequest request, BannerEntity banner, MultipartFile file) {
		//当前时间
		Date date = new Date();
		
		String img = "";
		if(!file.isEmpty()){
			//当上传文件不为空时
			img = UploadUtil.uploadFile("/upload/banner", request, file);
			
			banner.setImg(img);
		}
		
		//当图片文件为空时，判断是否为内部链接
		if(banner.getUrlType().equals("inner")){
			//为内部链接时，查询链接的图文，把图文管理记录的图片、标题等设置给banner
			CmsColumn cln = cmsColumnService.selectById(banner.getType());
			CmsEntity cms = cmsService.selectById(banner.getUrl());
			banner.setUrl(cln.getDetailUrl()+"?id="+banner.getUrl());
			banner.setTitle(cms.getTitle());	
		}
		
		//新增默认上架，修改不改变上下架
		if(banner.getId() == null){
			banner.setUpDown(BannerConstants.UP.toString());
			banner.setCreateTime(date);
		}else{
			banner.setUpdateTime(date);
		}
		
		//验证删除
		this.validateSort(banner);
		
		//新增或更新
		return this.insertOrUpdate(banner);
	}
	
	/**
	 * 
	* @Title: validateSort 
	* @Description: 验证上架的banner是否存在相同位置排序的数据，本身以外删除
	* @param banner banner对象：id location sort 
	* @return
	* @author xiong li
	 */
	private Boolean validateSort(BannerEntity banner){
		//根据位置和排序查询，存在，将原来的数据替换
		EntityWrapper<BannerEntity> wrapper = new EntityWrapper<BannerEntity>();
		wrapper.eq("location", banner.getLocation());
		wrapper.eq("sort", banner.getSort());
		wrapper.eq("source", "0");
		wrapper.eq("up_down", BannerConstants.UP);
		//判断是否存在
		BannerEntity enti = this.selectOne(wrapper);
		if(null != enti){
			//当修改记录和存在记录不一样时，删除存在记录
			if(null == banner.getId()){
				enti.setUpDown(BannerConstants.DOWN.toString());
				this.update(enti, wrapper);
				return false;
			}else{
				BannerEntity vali = this.selectById(banner.getId());
				//当存在数据不是其本身，并且其为上架状态时，则不改变
				if(!banner.getId().equals(enti.getId()) && vali.getUpDown().equals(BannerConstants.UP)){
					enti.setUpDown(BannerConstants.DOWN.toString());
					this.update(enti, wrapper);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Boolean upDown(Integer id, String location) {
		//更新时间
		Date date = new Date();
		//下架标识转换
		int down = BannerConstants.DOWN;
		//上架标识转换
		int up = BannerConstants.UP;
		//返回参数
		Boolean bool = false;
		
		switch (location) {
		//联盟学校
		case BannerConstants.X1:
			SchoolEntity school = schoolService.selectById(id);
			//以前为置顶时改为否，不是置顶时改为置顶
			if(school.getIsTop().equals(up)){
				school.setIsTop(down);
			}else{
				school.setIsTop(up);
			}
			school.setUpdateTime(date);
			bool = schoolService.updateById(school);
			break;
		//专家位
		case BannerConstants.ZJ1:
			ExpertEntity expert = expertService.selectById(id);
			//以前为置顶时改为否，不是置顶时改为置顶
			if(expert.getIsTop().equals(up)){
				expert.setIsTop(down);
			}else{
				expert.setIsTop(up);
			}
			expert.setUpdatetime(date);
			bool = expertService.updateById(expert);
			break;
		//教材位
		case BannerConstants.J1:
			TextbookEntity book = textbookService.selectById(id);
			//以前为置顶时改为否，不是置顶时改为置顶
			if(book.getIsTop().equals(up)){
				book.setIsTop(down);
			}else{
				book.setIsTop(up);
			}
			book.setUpdatetime(date);
			bool = textbookService.updateById(book);
			break;

		default:
			//查询需处理的记录
			BannerEntity banner = this.selectById(id);
			if(banner.getUpDown().equals(BannerConstants.UP)){
				//当banner为上架时，就处理成下架
				banner.setUpDown(BannerConstants.DOWN.toString());
			}else{
				//当上架时，需验证相同位置排序是否存在是否存在
				EntityWrapper<BannerEntity> wrapper = new EntityWrapper<BannerEntity>();
				wrapper.eq("location", banner.getLocation());
				wrapper.eq("source", "0");
				wrapper.eq("sort", banner.getSort());
				wrapper.eq("up_down", BannerConstants.UP);
				BannerEntity enti = this.selectOne(wrapper);
				if(null != enti){
					//将已存在的下架
					enti.setUpDown(BannerConstants.DOWN.toString());
					this.update(enti, wrapper);
				}
				//当banner为下架时，就处理成上架
				banner.setUpDown(BannerConstants.UP.toString());
			}
			banner.setUpdateTime(new Date());
			bool = this.updateById(banner);
			break;
		}
		return bool;
	}

	@Override
	public R bannerList(int page, int rows, BannerEntity banner) {
		
		//创建分页对象
		Page<BannerEntity> pages = new Page<BannerEntity>(page, rows);
		//条件查询的条件封装对象
		EntityWrapper<BannerEntity> wrapper = new EntityWrapper<BannerEntity>();
		
		wrapper.eq("location", banner.getLocation());
		wrapper.eq("source", "0");
		//列表按位置和排序升序排列
		wrapper.orderBy("up_down",false);
		wrapper.orderBy("sort",true);
		//分页查询banner记录
		pages = this.selectPage(pages, wrapper);
		//创建easyui允许格式的数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pages.getTotal());
		map.put("rows", pages.getRecords());
		
		return R.success(map);
	}

	@Override
	public Boolean deleteBanner(HttpServletRequest request, Integer[] ids) {
		
		return this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public R selectCms(String type, int page, int rows) {
		
		Page<CmsEntity> pages = new Page<CmsEntity>(page, rows);
		EntityWrapper<CmsEntity> wrapper = new EntityWrapper<CmsEntity>();
		//根据栏目和图片不为空查询图文记录
		wrapper.eq("type", type);
		//wrapper.isNotNull("accessory");
		pages = cmsService.selectPage(pages,wrapper);
		//创建easyui列表要求格式的对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pages.getTotal());
		map.put("rows", pages.getRecords());
		
		return R.success(map);
	}

	@Override
	public Object cmsColumns() {
		return cmsColumnService.list(new CmsColumn());
	}

	@Override
	public R validate(BannerEntity banner) {
		//根据位置和排序查询，存在，将原来的数据替换
		EntityWrapper<BannerEntity> wrapper = new EntityWrapper<BannerEntity>();
		wrapper.eq("location", banner.getLocation());
		wrapper.eq("sort", banner.getSort());
		wrapper.eq("up_down", BannerConstants.UP);
		wrapper.eq("source", "0");
		//判断是否存在
		BannerEntity enti = this.selectOne(wrapper);
		if(null != enti){
			if(null == banner.getId()){
				return R.error();
			}else{
				BannerEntity vali = this.selectById(banner.getId());
				//当存在数据不是其本身，并且其为上架状态时
				if(!banner.getId().equals(enti.getId()) && vali.getUpDown().equals(BannerConstants.UP)){
					return R.error();
				}
			}
		}
		return R.success();	
	}
}
