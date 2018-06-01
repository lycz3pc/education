package com.xhpower.education.platform.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xhpower.education.platform.entity.DigitCourse;

/**
 * X贝壳网数字课程表 Mapper 接口
 *
 * @author Lian YouJie
 * @since 2018-01-05
 */
public interface DigitCourseMapper extends BaseMapper<DigitCourse> {

    public List<DigitCourse> list(JSONObject jsonObject);

    public Integer listCount(String digitCourseType);

    public String selectMaxPosition();

}
