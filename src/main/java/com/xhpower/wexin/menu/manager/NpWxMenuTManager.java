package com.xhpower.wexin.menu.manager;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.menu.entity.NpWxMenuT;

/**
 * <p>
 * 
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
public interface NpWxMenuTManager extends IService<NpWxMenuT> {
	
	JSONObject findMenuList(HttpServletRequest request);
	
	boolean genderWxMenu(HttpServletRequest request);
	
}
