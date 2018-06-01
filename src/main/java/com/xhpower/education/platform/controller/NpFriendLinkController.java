package com.xhpower.education.platform.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.platform.entity.NpFriendLink;
import com.xhpower.education.platform.service.NpFriendLinkService;
import com.xhpower.education.utils.R;

/**
 * <p>
 * 友情链接表 前端控制器
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-23
 */
@RestController
@RequestMapping("/platform/friendLink")
public class NpFriendLinkController {

    @Autowired
    private NpFriendLinkService npFriendLinkService;

    /**
     * 查询友情链接列表
     * 
     * @author Lian Youjie
     * @date 2017年10月23日 下午4:18:18
     * @param page
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody Page<NpFriendLink> page) {
        EntityWrapper<NpFriendLink> wrapper = new EntityWrapper<>();
        wrapper.orderBy("position");
        return R.success().page(npFriendLinkService.selectPage(page, wrapper));
    }

    /**
     * 保存友情链接
     * 
     * @author Lian Youjie
     * @date 2017年10月23日 下午4:18:40
     * @param npFriendLink
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody NpFriendLink npFriendLink) {
        npFriendLink.setCreateTime(npFriendLink.getId() > 0 ? null : new Date());
        npFriendLink.setUpdateTime(npFriendLink.getId() > 0 ? new Date() : null);
        return npFriendLinkService.insertOrUpdate(npFriendLink) ? R.success() : R.error();
    }

    /**
     * 删除友情链接
     * 
     * @author Lian Youjie
     * @date 2017年10月23日 下午4:18:43
     * @param npFriendLink
     * @return
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody NpFriendLink npFriendLink) {
        return npFriendLinkService.deleteById(npFriendLink.getId()) ? R.success() : R.error();
    }

    /**
     * 检查友情链接名称
     * 
     * @author Lian Youjie
     * @date 2017年10月23日 下午4:42:46
     * @param npFriendLink
     * @return
     */
    @RequestMapping("/check")
    public Boolean check(@RequestBody NpFriendLink npFriendLink) {
        EntityWrapper<NpFriendLink> wrapper = new EntityWrapper<>();
        wrapper.ne("id", npFriendLink.getId());
        wrapper.eq("name", npFriendLink.getName());
        return npFriendLinkService.selectCount(wrapper) == 0;
    }

}
