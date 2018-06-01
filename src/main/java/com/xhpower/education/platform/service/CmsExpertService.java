package com.xhpower.education.platform.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.CmsExpertEntity;
import com.xhpower.education.platform.entity.CmsExpertVO;

/**
 * <p>
 * 学术成果专家表 服务类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-09
 */
public interface CmsExpertService extends IService<CmsExpertEntity> {

    /**
     * 获取专家列表
     * 
     * @author Lian Youjie
     * @date 2017年10月9日 上午10:25:40
     * @param cmsExpertEntity
     * @return
     */
    Page<CmsExpertEntity> list(CmsExpertVO cmsExpertVO);

}
