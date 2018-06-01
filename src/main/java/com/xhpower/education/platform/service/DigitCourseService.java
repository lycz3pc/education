package com.xhpower.education.platform.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.DigitCourse;

/**
 * X贝壳网数字课程表 服务类
 *
 * @author Lian YouJie
 * @since 2018-01-05
 */
public interface DigitCourseService extends IService<DigitCourse> {

    public Page<DigitCourse> list(Page<DigitCourse> page, DigitCourse digitCourse);

    public boolean edit(DigitCourse digitCourse);

}
