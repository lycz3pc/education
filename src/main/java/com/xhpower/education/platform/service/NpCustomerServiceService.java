package com.xhpower.education.platform.service;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.NpCustomerService;

/**
 * QQ客服 服务类
 *
 * @author Lian YouJie
 * @since 2017-11-03
 */
public interface NpCustomerServiceService extends IService<NpCustomerService> {
    
    public boolean start(Integer id);

}
