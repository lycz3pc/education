package com.xhpower.wexin.purchase.manager.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.wexin.purchase.dao.AddressMapper;
import com.xhpower.wexin.purchase.entity.Address;
import com.xhpower.wexin.purchase.manager.AddressManager;

/**
 * 地址信息表 服务实现类
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
@Service
public class AddressManagerImpl extends ServiceImpl<AddressMapper, Address> implements AddressManager {

}
