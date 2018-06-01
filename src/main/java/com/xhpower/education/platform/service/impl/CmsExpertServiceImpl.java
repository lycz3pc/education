package com.xhpower.education.platform.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.CmsExpertMapper;
import com.xhpower.education.platform.dao.ExpertEntityMapper;
import com.xhpower.education.platform.entity.CmsExpertEntity;
import com.xhpower.education.platform.entity.CmsExpertVO;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.platform.service.CmsExpertService;

/**
 * <p>
 * 学术成果专家表 服务实现类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-09
 */
@Service
public class CmsExpertServiceImpl extends ServiceImpl<CmsExpertMapper, CmsExpertEntity> implements CmsExpertService {

    @Autowired
    private CmsExpertMapper cmsExpertMapper;

    @Autowired
    private ExpertEntityMapper expertEntityMapper;

    /**
     * 获取专家列表
     * 
     * @see com.xhpower.education.platform.service.CmsExpertService#list(CmsExpertEntity)
     */
    @Override
    public Page<CmsExpertEntity> list(CmsExpertVO cmsExpertVO) {
        Page<CmsExpertEntity> page = new Page<>(cmsExpertVO.getCurrent(), cmsExpertVO.getSize());
        EntityWrapper<CmsExpertEntity> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotEmpty(cmsExpertVO.getTag())) {
            wrapper.like("tag", cmsExpertVO.getTag());
        }
        if (StringUtils.isNotEmpty(cmsExpertVO.getName())) {
            wrapper.like("name", cmsExpertVO.getName());
        }
        SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(cmsExpertMapper.selectPage(page, wrapper));
        if (cmsExpertVO.getSize() > 2147483646) {
            EntityWrapper<ExpertEntity> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("status", "1");
            entityWrapper.eq("logic_status", "1");
            if (StringUtils.isNotEmpty(cmsExpertVO.getTag())) {
                entityWrapper.like("tag", cmsExpertVO.getTag());
            }
            List<ExpertEntity> list = expertEntityMapper.selectList(entityWrapper);
            for (ExpertEntity expertEntity : list) {
                CmsExpertEntity entity = new CmsExpertEntity();
                BeanUtils.copyProperties(expertEntity, entity);
                entity.setId(expertEntity.getId().toString());
                page.getRecords().add(entity);
            }
        }
        return page;
    }

}
