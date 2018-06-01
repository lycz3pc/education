package com.xhpower.education.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.platform.entity.SchoolColumnEntity;
import com.xhpower.education.platform.service.SchoolColumnService;
import com.xhpower.education.utils.R;

/**
 * 
 * @ClassName: SchoolColumnController
 * @Description: 学校-栏目表 前端控制器
 * @author Lian Youjie
 * @date 2017年9月9日 下午3:55:54
 *
 */
@RestController
@RequestMapping("/platform/schoolColumn")
public class SchoolColumnController {

    @Autowired
    private SchoolColumnService schoolColumnService;

    /**
     * 获取校级子站栏目列表
     * 
     * @author Lian Youjie
     * @date 2017年11月23日 下午2:09:43
     * @param columnEntity
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody SchoolColumnEntity columnEntity) {
        EntityWrapper<SchoolColumnEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("school_id", columnEntity.getSchoolId());
        return R.success().put("schoolColumns", schoolColumnService.selectList(wrapper));
    }

}
