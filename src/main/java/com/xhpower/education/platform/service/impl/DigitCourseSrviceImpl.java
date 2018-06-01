package com.xhpower.education.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.DigitCourseMapper;
import com.xhpower.education.platform.entity.DigitCourse;
import com.xhpower.education.platform.service.DigitCourseService;

/**
 * X贝壳网数字课程表 服务实现类
 *
 * @author Lian YouJie
 * @since 2018-01-05
 */
@Service
public class DigitCourseSrviceImpl extends ServiceImpl<DigitCourseMapper, DigitCourse> implements DigitCourseService {

    @Autowired
    private DigitCourseMapper digitCourseMapper;

    @Override
    public Page<DigitCourse> list(Page<DigitCourse> page, DigitCourse digitCourse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("digitCourseType", digitCourse.getDigitCourseType());
        jsonObject.put("offset", page.getOffsetCurrent());
        jsonObject.put("limit", page.getSize());
        page.setTotal(digitCourseMapper.listCount(digitCourse.getDigitCourseType()));
        page.setRecords(digitCourseMapper.list(jsonObject));
        return page;
    }

    @Override
    public boolean edit(DigitCourse digitCourse) {
        if (!"0".equals(digitCourse.getStatus())) digitCourse.setStatus(digitCourseMapper.selectMaxPosition());
        return insertOrUpdate(digitCourse);
    }

}
