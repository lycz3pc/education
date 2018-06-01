package com.xhpower.wexin.jdclick.manager;

import com.xhpower.wexin.jdclick.entity.NpClickSum;
import com.xhpower.wexin.jdclick.entity.NpWxClickRecordT;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 数据埋点记录表 服务类
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-19
 */
public interface NpWxClickRecordTManager extends IService<NpWxClickRecordT> {
	public List<NpClickSum> list(Integer start,Integer end);
}
