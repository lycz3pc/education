package com.xhpower.wexin.purchase.manager.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.wexin.purchase.dao.OrderMerchandiseMapper;
import com.xhpower.wexin.purchase.entity.OrderMerchandise;
import com.xhpower.wexin.purchase.manager.OrderMerchandiseManager;

/**
 * 订单-商品详细信息表 服务实现类
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
@Service
public class OrderMerchandiseManagerImpl extends ServiceImpl<OrderMerchandiseMapper, OrderMerchandise>
        implements OrderMerchandiseManager {

}
