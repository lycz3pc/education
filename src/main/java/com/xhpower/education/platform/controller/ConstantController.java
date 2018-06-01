package com.xhpower.education.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.platform.entity.ConstantEntity;
import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.platform.service.ConstantService;
import com.xhpower.education.platform.service.NpConstantItemService;
import com.xhpower.education.utils.R;

/**
 * 
 * @ClassName: ConstantController
 * @Description: 学段常量控制类
 * @author liuyoucheng
 * @date 2017年9月30日 下午3:28:04
 *
 */
@RestController
@RequestMapping("/platform/constant")
public class ConstantController {

    @Autowired
    private ConstantService constantService;

    // public static Map<String, List<ConstantEntity>> dataList;

    @Autowired
    private NpConstantItemService npConstantItemService;

    @RequestMapping("/getSectionData")
    public R getSectionData(String category_key, String parent_id) {
        List<ConstantEntity> sectionList = constantService.getSectionData(category_key, parent_id);
        return R.success().put("section", sectionList);
    }

    @RequestMapping("/getTopicData")
    public R getTopicData(String categoryId) {
        List<NpConstantItem> topicList = npConstantItemService.getConstantByCategoryId(categoryId);
        return R.success().put("topic", topicList);
    }

    @RequestMapping("/getProvinceData")
    public R getProvinceData(String categoryId) {
        List<ConstantEntity> dataList = constantService.getConstantByCategory(categoryId);
        return R.success().put("address", dataList);
    }

    @RequestMapping("/getTextBookType")
    public R getTextBookType(String categoryId) {
        List<NpConstantItem> typeList = npConstantItemService.getTextBookType(categoryId);
        return R.success().put("type", typeList);
    }

    @RequestMapping("/check")
    public boolean check(@RequestBody NpConstantItem constant) {
        EntityWrapper<NpConstantItem> wrapper = new EntityWrapper<>();
        wrapper.ne("constant_id", constant.getConstantId());
        wrapper.eq("category_id", constant.getCategoryId());
        wrapper.eq("constant_value", constant.getConstantValue());
        return npConstantItemService.selectCount(wrapper) == 0;
    }
}
