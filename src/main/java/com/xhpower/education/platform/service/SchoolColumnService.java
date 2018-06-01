package com.xhpower.education.platform.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.SchoolColumnEntity;

/**
 * 
 * @ClassName: SchoolColumnService
 * @Description: 学校-栏目对应表服务接口类
 * @author Lian Youjie
 * @date 2017年9月9日 下午3:50:16
 *
 */
public interface SchoolColumnService extends IService<SchoolColumnEntity> {

    /**
     * 
     * @Title: createStation
     * @Description: 创建子站，删除原有的子站栏目信息
     * @param schoolColumnEntities
     * @author Lian Youjie
     * @return boolean 返回类型
     */
    boolean createStation(List<SchoolColumnEntity> schoolColumnEntities);

}
