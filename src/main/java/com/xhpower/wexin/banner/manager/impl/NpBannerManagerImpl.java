package com.xhpower.wexin.banner.manager.impl;

import com.xhpower.wexin.banner.entity.NpBanner;
import com.xhpower.education.platform.entity.BannerConstants;
import com.xhpower.education.platform.entity.BannerEntity;
import com.xhpower.education.platform.entity.CmsColumn;
import com.xhpower.education.platform.entity.CmsEntity;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.platform.entity.SchoolEntity;
import com.xhpower.education.platform.entity.TextbookEntity;
import com.xhpower.education.platform.service.CmsColumnService;
import com.xhpower.education.platform.service.CmsService;
import com.xhpower.education.platform.service.ExpertService;
import com.xhpower.education.platform.service.SchoolService;
import com.xhpower.education.platform.service.TextbookService;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.banner.dao.NpBannerMapper;
import com.xhpower.wexin.banner.manager.NpBannerManager;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * banner 服务实现类
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-17
 */
@Service
public class NpBannerManagerImpl extends ServiceImpl<NpBannerMapper, NpBanner> implements NpBannerManager {

	/**
	 * 
	* @Title: save 
	* @Description: 保存
	* @param npBannner 保存的npBanner对象
	* @return 
	* @author song bolin
	 */
	@Override
	public boolean insertOrUpdateBanner(NpBanner npBanner) {
		
		npBanner.setCreateTime(npBanner.getId() == null ? (new Date()) : null);
		npBanner.setUpdateTime(npBanner.getId() == null ? null :(new Date()));
		
		return insertOrUpdate(npBanner);
	}

	/**
	 * 
	* @Title: update 
	* @Description: 修改
	* @param id 选中行id
	* @return 
	* @author song bolin
	 */
	@Override
	public NpBanner selectNpBannerObject(Integer id) {
		NpBanner npBanner = selectById(id);
		if(npBanner!=null){
			
			return npBanner;
		}
		return null;
	}



	
}
