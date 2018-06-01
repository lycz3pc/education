package com.xhpower.education.platform.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.platform.entity.CmsColumn;
import com.xhpower.education.platform.entity.CmsColumnVO;

/**
 * 
 * @ClassName: CmsColumnMapper
 * @Description: 栏目管理Dao接口
 * @author Lian Youjie
 * @date 2017年8月28日 上午10:53:47
 *
 */
public interface CmsColumnMapper extends BaseMapper<CmsColumn> {
    
    List<CmsColumnVO> getAllChildren(CmsColumn cmsColumn);

}
