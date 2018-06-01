package com.xhpower.education.platform.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.CmsColumnMapper;
import com.xhpower.education.platform.entity.CmsColumn;
import com.xhpower.education.platform.entity.CmsColumnVO;
import com.xhpower.education.platform.service.CmsColumnService;

/**
 * 
 * @ClassName: CmsColumnManagerImpl
 * @Description: 栏目管理服务实现类
 * @author Lian YouJie
 * @date 2017年8月28日 上午10:57:36
 *
 */
@Service
@Transactional
public class CmsColumnManagerImpl extends ServiceImpl<CmsColumnMapper, CmsColumn> implements CmsColumnService {

    @Autowired
    private CmsColumnMapper cmsColumnMapper;

    /**
     * 获取栏目信息树形结构
     * 
     * @see com.xhpower.education.platform.service.CmsColumnService#list(CmsColumn)
     */
    @Override
    public List<CmsColumnVO> list(CmsColumn column) {
        List<CmsColumnVO> list = cmsColumnMapper.getAllChildren(column);
        
//        Map<Integer, CmsColumnVO> cMap = new LinkedHashMap<Integer, CmsColumnVO>();
//        EntityWrapper<CmsColumn> wrapper = new EntityWrapper<>();
//        if ("-1".equals(column.getId())) {
//            wrapper.eq("parent_id", column.getId());
//        }
//        wrapper.orderBy("id");
//        List<CmsColumn> list = cmsColumnMapper.selectList(wrapper);
//        for (Iterator<CmsColumn> iterator = list.iterator(); iterator.hasNext();) {
//            CmsColumn cmsColumn = iterator.next();
//            if (column.getId() != null && column.getId() > 0) {
//                CmsColumnVO vo = new CmsColumnVO();
//                BeanUtils.copyProperties(cmsColumn, vo);
//                vo.setText(cmsColumn.getCloumnName());
//                cMap.put(cmsColumn.getId(), vo);
//            } else {
//                if (Integer.valueOf("-1").equals(cmsColumn.getParentId())) {// 一级分类
//                    CmsColumnVO vo = new CmsColumnVO();
//                    BeanUtils.copyProperties(cmsColumn, vo);
//                    vo.setText(cmsColumn.getCloumnName());
//                    cMap.put(cmsColumn.getId(), vo);
//                } else {// 二级分类
//                    if (null != cMap.get(cmsColumn.getParentId())) {
//                        CmsColumnVO vo = new CmsColumnVO();
//                        BeanUtils.copyProperties(cmsColumn, vo);
//                        vo.setText(cmsColumn.getCloumnName());
//                        cMap.get(cmsColumn.getParentId()).getChildren().add(vo);// 添加到子分类中
//                    }
//                }
//            }
//        }
        return list;
    }

}
