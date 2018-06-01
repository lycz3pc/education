package com.xhpower.wexin.purchase.manager;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xhpower.wexin.purchase.entity.PayOrderRecordT;
import com.xhpower.wexin.purchase.entity.PurchaseVO;

/**
 * 支付记录表 服务类
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
public interface PayOrderRecordTManager extends IService<PayOrderRecordT> {

    public Page<PurchaseVO> selectPurchasePage(Page<PayOrderRecordT> page);

}
