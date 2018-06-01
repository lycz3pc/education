package com.xhpower.education.platform.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.platform.entity.CmsEntity;

/**
 * 
 * @ClassName: CmsEntityMapper
 * @Description: 内容发布实体类
 * @author Lian Youjie
 * @date 2017年8月29日 上午11:10:20
 *
 */
public interface CmsEntityMapper extends BaseMapper<CmsEntity> {

    /**
     * 
     * @Title: selectMaxPosition
     * @Description: 查询cmsEntity所在栏目下，最大的排序只并+1返回
     * @param cmsEntity
     * @author Lian Youjie
     * @return Integer 返回类型
     */
    Integer selectMaxPosition(CmsEntity cmsEntity);

}
