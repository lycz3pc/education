package com.xhpower.wexin.campus.manager;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.wexin.campus.entity.NpWxCampusT;

/**
 * <p>
 * 校区表 服务类
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
public interface NpWxCampusTManager extends IService<NpWxCampusT> {
	
	NpWxCampusT selectCampusObject(Integer id);
	
	boolean insertOrUpdateArticle(NpWxCampusT npWxCampusT,HttpServletRequest request);
}
