package com.xhpower.education.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.api.core.APIException;
import com.xhpower.education.system.dao.SequenceMapper;
import com.xhpower.education.system.entity.Sequence;
import com.xhpower.education.system.manager.SequenceManager;

/**
 * <p>
 * 序列表 服务实现类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-06-25
 */
@Service
public class SequenceManagerImpl extends ServiceImpl<SequenceMapper, Sequence> implements SequenceManager {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    public Long nextVal(String seqName) {
        Sequence temp = sequenceMapper.selectById(seqName);
        Long vaLong = 0L;
        if (temp == null) {
            new APIException(5, "序列不存在");
        }
        vaLong = temp.getCurrentVal() + temp.getIncrementVal();
        temp.setCurrentVal(vaLong);
        sequenceMapper.updateById(temp);
        return vaLong;
    }

}
