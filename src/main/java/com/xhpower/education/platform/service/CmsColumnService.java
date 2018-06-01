package com.xhpower.education.platform.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.CmsColumn;
import com.xhpower.education.platform.entity.CmsColumnVO;

/**
 * 
 * @ClassName: CmsColumnManager
 * @Description: 栏目管理服务接口
 * @author Lian YouJie
 * @date 2017年8月28日 上午10:55:30
 *
 */
public interface CmsColumnService extends IService<CmsColumn> {

    /**
     * 
     * @Title: list
     * @Description: 返回栏目管理树形结构
     * @param wrapper
     * @author Lian Youjie
     * @return List<CmsColumn> 返回类型
     */
    List<CmsColumnVO> list(CmsColumn cmsColumn);

}
