package com.xhpower.education.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.SchoolColumnMapper;
import com.xhpower.education.platform.entity.SchoolColumnEntity;
import com.xhpower.education.platform.entity.SchoolEntity;
import com.xhpower.education.platform.service.SchoolColumnService;
import com.xhpower.education.platform.service.SchoolService;

/**
 * 
 * @ClassName: SchoolColumnServiceImpl
 * @Description: 学校-栏目对应表服务实现类
 * @author Lian Youjie
 * @date 2017年9月9日 下午3:51:37
 *
 */
@Service
public class SchoolColumnServiceImpl extends ServiceImpl<SchoolColumnMapper, SchoolColumnEntity>
        implements SchoolColumnService {
    
    @Autowired
    private SchoolService schoolService;

    @Override
    public boolean createStation(List<SchoolColumnEntity> schoolColumnEntities) {
        if (schoolColumnEntities.size() > 0) {
            Integer id = schoolColumnEntities.get(0).getSchoolId();
            EntityWrapper<SchoolColumnEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("school_id", id);
            schoolService.updateById(new SchoolEntity(id, "/" + id));
            delete(wrapper);
            return insertBatch(schoolColumnEntities);
        } else {
            return false;
        }
    }

}
