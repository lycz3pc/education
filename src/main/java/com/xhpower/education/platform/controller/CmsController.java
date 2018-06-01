package com.xhpower.education.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.common.cons.EducationConstants;
import com.xhpower.education.platform.entity.CmsEntity;
import com.xhpower.education.platform.entity.CmsQueryVO;
import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.platform.service.CmsService;
import com.xhpower.education.platform.service.NpConstantItemService;
import com.xhpower.education.utils.R;

/**
 * 
 * @ClassName: CmsController
 * @Description: 内容发布控制器
 * @author Lian Youjie
 * @date 2017年8月29日 上午10:45:10
 *
 */
@RestController
@RequestMapping("/platform/cms")
public class CmsController {

    @Autowired
    private CmsService cmsService;
    
    @Autowired
    private NpConstantItemService npConstantItemService;

    /**
     * 
     * @Title: list
     * @Description: 根据查询条件查询内容列表
     * @param cmsQueryVO
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/list")
    public R list(@RequestBody CmsQueryVO cmsQueryVO) {
        Page<CmsEntity> page = new Page<>(cmsQueryVO.getCurrent(), cmsQueryVO.getSize());
        EntityWrapper<CmsEntity> wrapper = new EntityWrapper<>();
        // 判断内容所属栏目，用于第一次打开内容列表也，查询所有内容
        if (!"-1".equals(cmsQueryVO.getType())) {
            wrapper.like("type_path", cmsQueryVO.getType());
        }
        wrapper.like("title", cmsQueryVO.getTitle());
        wrapper.orderBy("news", false);
        wrapper.orderBy("position", false);
        wrapper.orderBy("create_time", false);
        page = cmsService.selectPage(page, wrapper);
        return R.success().page(page);
    }

    /**
     * 
     * @Title: save
     * @Description: 保存内容
     * @param cmsEntity
     * @author Lian Youjie
     * @return R 返回类型
     */
    @SysLog("内容发布")
    @RequestMapping("/save")
    public R save(@RequestBody CmsEntity cmsEntity) {
        // TODO:设置内容创建人id
        cmsEntity.setCreateUser(5797);
        return cmsService.insertCms(cmsEntity) ? R.success() : R.error();
    }

    /**
     * 
     * @Title: delete
     * @Description: 根据id删除内容
     * @param cmsEntity
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/delete")
    @SysLog("内容删除")
    public R delete(@RequestBody CmsEntity cmsEntity) {
        return cmsService.deleteCms(cmsEntity) ? R.success() : R.error();
    }

    /**
     * 
     * @Title: setTop
     * @Description: 将内容在栏目下置顶
     * @param cmsEntity
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/setTop")
    @SysLog("内容置顶")
    public R setTop(@RequestBody CmsEntity cmsEntity) {
        return cmsService.setTop(cmsEntity) ? R.success() : R.error();
    }
    
    /**
     * 获取标签和主题列表
     * 
     * @author Lian Youjie
     * @date 2017年10月9日 下午3:41:48
     * @param cmsExpertVO
     * @return
     */
    @RequestMapping("/getTagAndThemeList")
    public R getTagAndThemelist(@RequestBody CmsEntity cmsEntity) {
        List<NpConstantItem> themeList = npConstantItemService.getConstantByCategoryId(EducationConstants.CATEGORY_TOPIC_101);
        List<NpConstantItem> tagList = npConstantItemService.getConstantByCategoryId(EducationConstants.CATEGORY_FLAG_102);
        return R.success().put("themes", themeList).put("tags", tagList);
    }

}
