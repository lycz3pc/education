package com.xhpower.education.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.platform.entity.DigitCourse;
import com.xhpower.education.platform.entity.DigitCourseDto;
import com.xhpower.education.platform.service.DigitCourseService;
import com.xhpower.education.utils.R;

/**
 * X贝壳网数字课程表 前端控制器
 *
 * @author Lian YouJie
 * @since 2018-01-05
 */
@RestController
@RequestMapping("/platform/digitCourse")
public class DigitCourseController {

    @Autowired
    private DigitCourseService digitCourseService;

    @RequestMapping("/list")
    public R list(@RequestBody DigitCourseDto dto) {
    	Page<DigitCourse> page = new Page<>(dto.getCurrent(), dto.getSize());
    	DigitCourse digitCourse = new DigitCourse();
    	digitCourse.setDigitCourseType(dto.getDigitCourseType());
        return R.success().page(digitCourseService.list(page, digitCourse));
    }

    @RequestMapping("/edit")
    public R edit(@RequestBody DigitCourse digitCourse) {
        digitCourseService.edit(digitCourse);
        return R.success();
    }

}
