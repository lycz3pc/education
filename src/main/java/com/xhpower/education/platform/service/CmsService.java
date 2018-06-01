package com.xhpower.education.platform.service;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.CmsEntity;

/**
 * 
 * @ClassName: CmsService
 * @Description: 内容发布服务类
 * @author Lian Youjie
 * @date 2017年8月29日 上午11:13:04
 *
 */
public interface CmsService extends IService<CmsEntity> {

    /**
     * 
     * @Title: insertCms
     * @Description: 插入内容，同时插入封面和附件信息
     * @param cmsEntity
     * @author Lian Youjie
     * @return boolean 返回类型
     */
    boolean insertCms(CmsEntity cmsEntity);

    /**
     * 
     * @Title: deleteCms
     * @Description: 删除内容，同时删除封面和附件信息
     * @param cmsEntity
     * @author Lian Youjie
     * @return boolean 返回类型
     */
    boolean deleteCms(CmsEntity cmsEntity);

    /**
     * 
     * @Title: setTop
     * @Description: cmsEntity置顶
     * @param cmsEntity
     * @author Lian Youjie
     * @return boolean 返回类型
     */
    boolean setTop(CmsEntity cmsEntity);

}
