package com.xhpower.wexin.campus.manager.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.article.entity.NpWxArticleT;
import com.xhpower.wexin.campus.dao.NpWxCampusTMapper;
import com.xhpower.wexin.campus.entity.NpWxCampusT;
import com.xhpower.wexin.campus.manager.NpWxCampusTManager;
import com.xhpower.wexin.utils.PublicMethod;

/**
 * <p>
 * 校区表 服务实现类
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@Service
public class NpWxCampusTManagerImpl extends ServiceImpl<NpWxCampusTMapper, NpWxCampusT> implements NpWxCampusTManager {

	@Autowired
	private ResourcesManager resourcesManager;
	
	/**
	 * 查询单个校区详情
	 */
	@Override
	public NpWxCampusT selectCampusObject(Integer id) {
		NpWxCampusT npWxCampusT = selectById(id);
		if(npWxCampusT!=null){
			Resources resources = this.resourcesManager.selectById(npWxCampusT.getQrcodeSourceId());
			if(resources!=null){
				npWxCampusT.setQrCodeResources(resources);
			}
			return npWxCampusT;
		}
		return null;
	}

	/**
	 * 增删改以及二维码图片资源入库
	 */
	@Override
	public boolean insertOrUpdateArticle(NpWxCampusT npWxCampusT,HttpServletRequest request) {
		
		npWxCampusT.setCreateDate(npWxCampusT.getId() == null ? (int)(System.currentTimeMillis()/1000) : null);
		npWxCampusT.setUpdateDate(npWxCampusT.getId() == null ? null : (int)(System.currentTimeMillis()/1000));
		
		boolean insertResult = insertOrUpdate(npWxCampusT);
		
		//当执行新增操作后获取校区的唯一标识生成二维码
		if(npWxCampusT.getCreateDate()!=null && npWxCampusT.getCreateDate()>0){
			String path = PublicMethod.createQrCode("png","/upload/weixin/qrcode","http://www.baidu.com?cpsId="+npWxCampusT.getId(), 300,300, request);
			if(StringUtils.isNotEmpty(path)){
				Resources qrCodeResources = new Resources();
				qrCodeResources.setPath(path);
				qrCodeResources.setName("png");
				qrCodeResources.setCreateTime(new Date());
				npWxCampusT.setQrCodeResources(qrCodeResources);
				
				npWxCampusT.getQrCodeResources().setCreateTime(new Date());
				boolean r_reslut =  this.resourcesManager.insertResources(npWxCampusT.getQrCodeResources());
				if(r_reslut){
					npWxCampusT.setQrcodeSourceId(npWxCampusT.getQrCodeResources().getId());
					return insertOrUpdate(npWxCampusT);
				}
			}
		}
		return insertResult;
	}
	
}
