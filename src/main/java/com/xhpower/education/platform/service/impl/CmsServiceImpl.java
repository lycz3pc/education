package com.xhpower.education.platform.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.CmsEntityMapper;
import com.xhpower.education.platform.entity.CmsEntity;
import com.xhpower.education.platform.service.CmsService;
import com.xhpower.education.system.manager.ResourcesManager;

/**
 * 
 * @ClassName: CmsServiceImpl
 * @Description: 内容发布服务实现类
 * @author Lian Youjie
 * @date 2017年8月29日 上午11:13:45
 *
 */
@Service
@Transactional
public class CmsServiceImpl extends ServiceImpl<CmsEntityMapper, CmsEntity> implements CmsService {

    @Autowired
    private CmsEntityMapper cmsEntityMapper;

    @Autowired
    private ResourcesManager resourcesManager;

    /**
     * 插入内容，同时插入封面和附件信息
     * 
     * @see com.xhpower.education.platform.service.CmsService#insert(CmsEntity)
     */
    @Override
    public boolean insertCms(CmsEntity cmsEntity) {
        cmsEntity.setCreateTime(cmsEntity.getId() == null ? new Date() : null);
        cmsEntity.setUpdateTime(cmsEntity.getId() == null ? null : new Date());
        resourcesManager.insertResources(cmsEntity.getAccessoryResources());
        cmsEntity.setAccessory(cmsEntity.getAccessoryResources().getId());
        resourcesManager.insertResources(cmsEntity.getCoverResources());
        cmsEntity.setCoverUrl(cmsEntity.getCoverResources().getId());
        cmsEntity.setPosition(cmsEntity.getId() == null ? 0 : null);
        cmsEntity.setNews(cmsEntity.getId() == null ? 0 : null);
        return insertOrUpdate(cmsEntity);
    }

    @Override
    public boolean deleteCms(CmsEntity cmsEntity) {
        cmsEntity = selectById(cmsEntity.getId());
        resourcesManager.deleteById(cmsEntity.getAccessory());
        resourcesManager.deleteById(cmsEntity.getCoverUrl());
        return deleteById(cmsEntity.getId());
    }

    @Override
    public boolean setTop(CmsEntity cmsEntity) {
        Integer maxPosition = 0;
        if (Integer.valueOf(1).equals(cmsEntity.getPosition())) {
            maxPosition = cmsEntityMapper.selectMaxPosition(cmsEntity);
            cmsEntity = new CmsEntity(cmsEntity.getId(), maxPosition);
        } else if (Integer.valueOf(2).equals(cmsEntity.getPosition())) {
            cmsEntity = new CmsEntity(cmsEntity.getId(), 0, "");
        } else if (Integer.valueOf(3).equals(cmsEntity.getPosition())) {
            EntityWrapper<CmsEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("type", cmsEntity.getType());
            wrapper.eq("news", "1");
            if (cmsEntityMapper.selectCount(wrapper) < 3) {
                cmsEntity = new CmsEntity(cmsEntity.getId(), 1, "");
            }else {
                cmsEntity = new CmsEntity(cmsEntity.getId(), 0, "");
            }
        } else {
            cmsEntity = new CmsEntity(cmsEntity.getId(), maxPosition);
        }
        return updateById(cmsEntity);
    }

}
