package com.xhpower.education.system.manager;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Sequence;

/**
 * <p>
 * 序列表 服务类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-06-25
 */
public interface SequenceManager extends IService<Sequence> {

    Long nextVal(String seqName);

}
