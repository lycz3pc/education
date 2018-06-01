package com.xhpower.wexin.purchase.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.UserInfoEntityMapper;
import com.xhpower.education.platform.entity.UserInfoEntity;
import com.xhpower.wexin.purchase.dao.PayOrderRecordTMapper;
import com.xhpower.wexin.purchase.entity.Address;
import com.xhpower.wexin.purchase.entity.PayOrderRecordT;
import com.xhpower.wexin.purchase.entity.PurchaseVO;
import com.xhpower.wexin.purchase.manager.AddressManager;
import com.xhpower.wexin.purchase.manager.PayOrderRecordTManager;

/**
 * 支付记录表 服务实现类
 *
 * @author Lian YouJie
 * @since 2017-12-04
 */
@Service
public class PayOrderRecordTManagerImpl extends ServiceImpl<PayOrderRecordTMapper, PayOrderRecordT>
        implements PayOrderRecordTManager {

    @Autowired
    private AddressManager addressManager;

    @Autowired
    private PayOrderRecordTMapper recordMapper;

    @Autowired
    private UserInfoEntityMapper userInfoEntityMapper;

    @Override
    public Page<PurchaseVO> selectPurchasePage(Page<PayOrderRecordT> recordPage) {
        Page<PurchaseVO> page = new Page<>(recordPage.getCurrent(), recordPage.getSize());
        SqlHelper.fillWrapper(recordPage, ((Wrapper<?>) Condition.instance()));
        EntityWrapper<PayOrderRecordT> wrapper = new EntityWrapper<>();
        wrapper.eq("source", 3);
        wrapper.eq("status", 1);
        wrapper.orderBy(" create_date desc");
        List<PayOrderRecordT> records = recordMapper.selectPage(recordPage, wrapper);
        List<PurchaseVO> vos = new ArrayList<>();
        for (PayOrderRecordT record : records) {
            PurchaseVO vo = new PurchaseVO();
            BeanUtils.copyProperties(record, vo);
            Address address = addressManager.selectById(record.getAddressId());
            if (address != null) {
                vo.setAddress(address.getAddress());
                vo.setConsignee(address.getConsignee());
                vo.setPhone(address.getPhone());
            }
            UserInfoEntity userInfoEntity = userInfoEntityMapper.selectById(record.getUserId());
            if (userInfoEntity != null) {
                vo.setUserName(userInfoEntity.getReal_name());
            }
            vos.add(vo);
        }
        page.setTotal(recordPage.getTotal());
        page.setRecords(vos);
        return page;
    }

}
