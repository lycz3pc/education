package com.xhpower.wexin.purchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.purchase.entity.OrderMerchandise;
import com.xhpower.wexin.purchase.entity.PayOrderRecordT;
import com.xhpower.wexin.purchase.manager.OrderMerchandiseManager;
import com.xhpower.wexin.purchase.manager.PayOrderRecordTManager;

/**
 * 支付记录表 前端控制器
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
@RestController
@RequestMapping("/weixin/purchase")
public class PurchaseController {

    @Autowired
    private PayOrderRecordTManager recordTManager;
    
    @Autowired
    private OrderMerchandiseManager merchandiseManager;

    /**
     * 订单列表
     * 
     * @author Lian Youjie
     * @date 2017年12月4日 上午11:32:40
     * @param record
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody Page<PayOrderRecordT> page) {
        return R.success().page(recordTManager.selectPurchasePage(page));
    }

    /**
     * 发货
     * 
     * @author Lian Youjie
     * @date 2017年12月4日 上午11:32:40
     * @param record
     * @return
     */
    @RequestMapping("/delivery")
    public R delivery(@RequestBody PayOrderRecordT record) {
        PayOrderRecordT recordT = new PayOrderRecordT();
        recordT.setId(record.getId());
        recordT.setShipStatus(1);
        recordT.setWaybillNo(record.getWaybillNo());
        return recordTManager.updateById(recordT) ? R.success() : R.error();
    }
    
    @RequestMapping("/detail")
    public R detail(@RequestBody PayOrderRecordT record) {
        EntityWrapper<OrderMerchandise> wrapper = new EntityWrapper<>();
        wrapper.eq("order_id", record.getId());
        return R.success().put("merchandises", merchandiseManager.selectList(wrapper));
    }

}
