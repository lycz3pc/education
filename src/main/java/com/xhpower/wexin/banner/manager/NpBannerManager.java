package com.xhpower.wexin.banner.manager;


import com.xhpower.education.system.entity.Role;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.banner.entity.NpBanner;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * banner 服务类
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-17
 */
public interface NpBannerManager extends IService<NpBanner> {
	/**
	 * 
	* @Title: save 
	* @Description: 保存
	* @param npBannner 保存的npBanner对象
	* @return 
	* @author song bolin
	 */
	boolean insertOrUpdateBanner(NpBanner npBanner);

	/**
	 * 
	* @Title: update 
	* @Description: 修改
	* @param id 选中行id
	* @return 
	* @author song bolin
	 */
	NpBanner selectNpBannerObject(Integer id);
	
	

}
