package com.xhpower.wexin.article.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.entity.CmsEntity;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.wexin.article.dao.NpWxArticleTMapper;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.article.manager.NpWxArticleTManager;

/**
 * <p>
 * 热文表 服务实现类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-17
 */
@Service
public class NpWxArticleTManagerImpl extends ServiceImpl<NpWxArticleTMapper, NpWxArticleT> implements NpWxArticleTManager {
	
	@Autowired
	private ResourcesManager resourcesManager;
	
	
	/**
	 * 修改及新增文件资源操作
	 */
	@Override
    public boolean insertOrUpdateArticle(NpWxArticleT npWxArticleT) {
		npWxArticleT.setCreateDate(npWxArticleT.getRId() == null ? (int)(System.currentTimeMillis()/1000) : null);
		npWxArticleT.setUpdateDate(npWxArticleT.getRId() == null ? null : (int)(System.currentTimeMillis()/1000));
		
		if(npWxArticleT.getCoverResources()!=null){
			npWxArticleT.getCoverResources().setCreateTime(new Date());
			this.resourcesManager.insertResources(npWxArticleT.getCoverResources());
			npWxArticleT.setImgRsourceId(npWxArticleT.getCoverResources().getId());
		}
       
		return insertOrUpdate(npWxArticleT);
    }

	/**
	 * 查询单条热文记录及图片资源
	 */
	@Override
	public NpWxArticleT selectNpWxArticleObject(Integer rid) {
		NpWxArticleT npWxArticleT = selectById(rid);
		if(npWxArticleT!=null){
			Resources resources = this.resourcesManager.selectById(npWxArticleT.getImgRsourceId());
			if(resources!=null){
				npWxArticleT.setCoverResources(resources);
			}
			return npWxArticleT;
		}
		return null;
	}
}
