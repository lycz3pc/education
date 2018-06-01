package com.xhpower.education.platform.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.common.cons.EducationConstants;
import com.xhpower.education.platform.entity.CmsExpertEntity;
import com.xhpower.education.platform.entity.CmsExpertVO;
import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.platform.service.CmsExpertService;
import com.xhpower.education.platform.service.NpConstantItemService;
import com.xhpower.education.utils.R;

/**
 * <p>
 * 学术成果专家表 前端控制器
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-09
 */
@RestController
@RequestMapping("/platform/cmsExpert")
public class CmsExpertController {

    @Autowired
    private CmsExpertService cmsExpertService;

    @Autowired
    private NpConstantItemService npConstantItemService;

    /**
     * 查询专家列表，并返回主题列表
     * 
     * @author Lian Youjie
     * @date 2017年10月9日 下午3:41:48
     * @param cmsExpertVO
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody CmsExpertVO cmsExpertVO) {
        Page<CmsExpertEntity> page = cmsExpertService.list(cmsExpertVO);
        List<NpConstantItem> list = npConstantItemService.getConstantByCategoryId(EducationConstants.CATEGORY_TOPIC_101);
        return R.success().page(page).put("themes", list);
    }

    /**
     * 专家保存
     * 
     * @author Lian Youjie
     * @date 2017年10月9日 下午3:41:22
     * @param entity
     * @return
     */
    @SysLog("专家保存")
    @RequestMapping("/save")
    public R save(@RequestBody CmsExpertEntity entity) {
        entity.setId(StringUtils.isNotBlank(entity.getId()) ? entity.getId() : UUID.randomUUID().toString().replace("-", ""));
        cmsExpertService.insertOrUpdate(entity);
        return R.success().put("expertId", entity.getId());
    }

    /**
     * 专家删除
     * 
     * @author Lian Youjie
     * @date 2017年10月9日 下午3:41:22
     * @param entity
     * @return
     */
    @SysLog("专家删除")
    @RequestMapping("/delete")
    public R delete(@RequestBody CmsExpertEntity entity) {
        cmsExpertService.deleteById(entity.getId());
        return R.success();
    }

    /**
     * 
     * @Title: check
     * @Description: 检查是否已存在专家名称
     * @param entity
     * @author Lian Youjie
     * @return Boolean 返回类型
     */
    @RequestMapping("/check")
    public Boolean check(@RequestBody CmsExpertEntity entity) {
        EntityWrapper<CmsExpertEntity> wrapper = new EntityWrapper<>();
        wrapper.ne("id", entity.getId());
        wrapper.eq("name", entity.getName());
        return cmsExpertService.selectCount(wrapper) == 0;
    }

}
