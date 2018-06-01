package com.xhpower.wexin.activity.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.wexin.activity.dao.NpWxActivityTMapper;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.activity.manager.NpWxActivityTManager;
import com.xhpower.wexin.article.entity.NpWxArticleT;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@Service
public class NpWxActivityTManagerImpl extends ServiceImpl<NpWxActivityTMapper, NpWxActivityT> implements NpWxActivityTManager {

	@Autowired
	private ResourcesManager resourcesManager;
	
	/**
	 * 修改及添加文件资源记录
	 */
	@Override
	public boolean insertOrUpdateActivity(NpWxActivityT npWxActivityT) {
		npWxActivityT.setCreateDate(npWxActivityT.getHId() == null ? (int)(System.currentTimeMillis()/1000) : null);
		npWxActivityT.setUpdateDate(npWxActivityT.getHId() == null ? null : (int)(System.currentTimeMillis()/1000));
		
		if(npWxActivityT.getActivityResources()!=null){
			npWxActivityT.getActivityResources().setCreateTime(new Date());
			this.resourcesManager.insertResources(npWxActivityT.getActivityResources());
			npWxActivityT.setActivitySourceId(npWxActivityT.getActivityResources().getId());
		}
       
		return insertOrUpdate(npWxActivityT);
	}

	/**
	 * 查询单条活动详情记录
	 */
	@Override
	public NpWxActivityT selectNpWxActivityObject(Integer hid) {
		NpWxActivityT npWxActivityT = selectById(hid);
		if(npWxActivityT!=null){
			Resources resources = this.resourcesManager.selectById(npWxActivityT.getActivitySourceId());
			if(resources!=null){
				npWxActivityT.setActivityResources(resources);
			}
			return npWxActivityT;
		}
		return null;
	}
	
}
