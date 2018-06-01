package com.xhpower.education.platform.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.platform.entity.CmsColumn;
import com.xhpower.education.platform.entity.CmsColumnVO;
import com.xhpower.education.platform.service.CmsColumnService;
import com.xhpower.education.utils.R;

/**
 * 
 * @ClassName: CmsColumnController
 * @Description: 栏目管理控制器
 * @author Lian YouJie
 * @date 2017年8月28日 上午10:58:41
 *
 */
@RestController
@RequestMapping("/platform/cmsColumn")
public class CmsColumnController {

    @Autowired
    private CmsColumnService cmsColumnService;

    /**
     * 
     * @Title: list
     * @Description: 查询栏目列表，传入cmsColumn的id属性则返回该父栏目下的子栏目
     * @param cmsColumn
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/list")
    public R list(@RequestBody CmsColumn cmsColumn) {
        try {
            List<CmsColumnVO> list = new ArrayList<>();
            // TODO: 根据帐号控制是否显示顶级节点
            if ("xhpower".equals("xhpow")) {
                list.add(new CmsColumnVO("-1", "顶级节点", cmsColumnService.list(cmsColumn)));
            } else {
                list = cmsColumnService.list(cmsColumn);
            }
            return R.success().put("columns", list);
        } catch (Exception e) {
            return R.error(606, "网络连接超时！");
        }
    }

    /**
     * 
     * @Title: save
     * @Description: 保存栏目
     * @param cmsColumn
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/save")
    @SysLog("栏目保存")
    public R save(@RequestBody CmsColumn cmsColumn) {
        try {
            // 设置创建时间，已有id则是修改，不需再设置创建时间
            cmsColumn.setCreateTime("-1".equals(cmsColumn.getId()) ? new Date() : cmsColumn.getCreateTime());
            // 设置更新时间，没有id则是新增，不需设置更新时间
            cmsColumn.setUpdateTime("-1".equals(cmsColumn.getId()) ? null : new Date());
            cmsColumn.setId("-1".equals(cmsColumn.getId()) ? UUID.randomUUID().toString().replace("-", "") : cmsColumn.getId());
            return cmsColumnService.insertOrUpdate(cmsColumn) ? R.success() : R.error();
        } catch (Exception e) {
            return R.error(606, "网络连接超时！");
        }
    }

    /**
     * 
     * @Title: delete
     * @Description: 删除栏目:根据id删除单个栏目
     * @param cmsColumn
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/delete")
    @SysLog("栏目删除")
    public R delete(@RequestBody CmsColumn cmsColumn) {
        try {
            return cmsColumnService.deleteById(cmsColumn.getId()) ? R.success() : R.error();
        } catch (Exception e) {
            return R.error(606, "网络连接超时！");
        }
    }

    /**
     * 
     * @Title: check
     * @Description: 检查是否已存在栏目名称
     * @param cmsColumn
     * @author Lian Youjie
     * @return Boolean 返回类型
     */
    @RequestMapping("/check")
    public Boolean check(@RequestBody CmsColumn cmsColumn) {
        EntityWrapper<CmsColumn> wrapper = new EntityWrapper<>();
        wrapper.ne("id", cmsColumn.getId());
        wrapper.eq("cloumn_name", cmsColumn.getCloumnName());
        return cmsColumnService.selectCount(wrapper) == 0;
    }

}
