package com.xhpower.education.platform.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.platform.entity.CustomerServiceQueryVO;
import com.xhpower.education.platform.entity.NpCustomerService;
import com.xhpower.education.platform.service.NpCustomerServiceService;
import com.xhpower.education.utils.R;

/**
 * QQ客服 前端控制器
 *
 * @author Lian YouJie
 * @since 2017-11-03
 */
@RestController
@RequestMapping("/platform/npCustomerService")
public class NpCustomerServiceController {

    @Autowired
    private NpCustomerServiceService customerService;

    /**
     * 查询QQ客服列表
     * @author Lian Youjie
     * @date 2017年11月3日 上午5:00:06
     * @param vo
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody CustomerServiceQueryVO vo) {
        Page<NpCustomerService> page = new Page<>(vo.getCurrent(), vo.getSize());
        EntityWrapper<NpCustomerService> wrapper = new EntityWrapper<>();
        if (vo.getQq() != null) {
            wrapper.like("qq", vo.getQq().toString());
        }
        wrapper.orderBy("status", false);
        wrapper.orderBy("create_time", false);
        return R.success().page(customerService.selectPage(page, wrapper));
    }

    /**
     * 保存QQ客服
     * @author Lian Youjie
     * @date 2017年11月3日 上午5:00:20
     * @param vo
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody NpCustomerService vo) {
        vo.setCreateTime(vo.getId() == null ? new Date() : null);
        return customerService.insertOrUpdate(vo) ? R.success() : R.error();
    }

    /**
     * 删除
     * @author Lian Youjie
     * @date 2017年11月3日 上午5:00:31
     * @param vo
     * @return
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody NpCustomerService vo) {
        return customerService.deleteById(vo.getId()) ? R.success() : R.error();
    }
    
    /**
     * 删除
     * @author Lian Youjie
     * @date 2017年11月3日 上午5:00:31
     * @param vo
     * @return
     */
    @RequestMapping("/start")
    public R start(@RequestBody NpCustomerService vo) {
        return customerService.start(vo.getId()) ? R.success() : R.error();
    }

}
