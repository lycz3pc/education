package com.xhpower.education.platform.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.NpCustomerServiceMapper;
import com.xhpower.education.platform.entity.NpCustomerService;
import com.xhpower.education.platform.service.NpCustomerServiceService;

/**
 * QQ客服 服务实现类
 *
 * @author Lian YouJie
 * @since 2017-11-03
 */
@Service
@Transactional
public class NpCustomerServiceServiceImpl extends ServiceImpl<NpCustomerServiceMapper, NpCustomerService>
        implements NpCustomerServiceService {

    @Override
    public boolean start(Integer id) {
        NpCustomerService vo = new NpCustomerService(0, "未启用");
        EntityWrapper<NpCustomerService> wrapper = new EntityWrapper<>();
        update(vo, wrapper);
        vo = new NpCustomerService(id, 1, "启用");
        return updateById(vo);
    }

}
