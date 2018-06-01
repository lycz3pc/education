package com.xhpower.wexin.article.manager;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.wexin.article.entity.NpWxArticleT;

/**
 * <p>
 * 热文表 服务类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-17
 */
public interface NpWxArticleTManager extends IService<NpWxArticleT> {

	boolean insertOrUpdateArticle(NpWxArticleT npWxArticleT);
	
	NpWxArticleT selectNpWxArticleObject(Integer rid);
	
}
